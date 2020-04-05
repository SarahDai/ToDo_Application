import java.util.*;
import java.util.stream.Collectors;
import utils.InvalidArgumentException;
import utils.ListFormatter;

public abstract class OptionGroup {
  private String name;
  private List<Option> requiredOptions;
  private List<Option> options;
  private boolean isRequired;

  /**
   * Instantiates a new Option group.
   *
   * @param name the name
   */
  public OptionGroup(String name, boolean isRequired) {
    this.name = name;
    this.options = new ArrayList<>();
    this.requiredOptions = new ArrayList<>();
    this.isRequired = isRequired;
  }

  public void checkOptions(List<Option> options) throws InvalidArgumentException {
    List<String> missingOptions = new ArrayList<>();
    for (Option option : options) {
      if (!this.options.contains(option)) missingOptions.add(option.getName());
    }
    if (!missingOptions.isEmpty()) {
      throw new InvalidArgumentException(String.format(
          "Missing %s in this group.",
          ListFormatter.format(missingOptions)));
    }
  }
  /**
   * Gets name of the OptionGroup.
   *
   * @return the name of the OptionGroup.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Add option into the option group, set the group name of the Option meanwhile.
   *
   * @param option the option to be added into the option group.
   */
  public void addOption(Option option) {
    this.options.add(option);
    option.setGroup(this.name);
    if (option.isRequired()) requiredOptions.add(option);
  }

  public boolean isRequired() {
    return isRequired;
  }

  /**
   * Gets the list of options that belongs to the option group.
   *
   * @return the options belonging to the option group.
   */
  public List<Option> getOptions() {
    return this.options;
  }

  protected void basicCheck(List<Option> options) throws InvalidArgumentException {
    Map<String, Integer> optionMap = new HashMap<>();
    for (Option option : options) {
      String name = option.getName();
      optionMap.put(name, optionMap.getOrDefault(name, 0) + 1);
      if (!checkNum(optionMap, option))
        throw new InvalidArgumentException(String.format(
            "Option(%s) should only appears: %s times, but it appears %s times",
            name, option.getNumOfAppearing(), optionMap.get(name)));
    }
    List<String> leftRequiredOptions = checkRequired(optionMap);
    if (!leftRequiredOptions.isEmpty()) {
      throw new InvalidArgumentException(String.format(
          "%s required but missing.",
          ListFormatter.format(leftRequiredOptions)));
    }
  }

  private boolean checkNum(Map<String, Integer> map, Option option) {
    return map.get(option.getName()) <= option.getNumOfAppearing();
  }

  private List<String> checkRequired(Map<String, Integer> map) {
    List<String> left = flattenOptions(this.requiredOptions);
    for (Option option: this.requiredOptions) {
      if (map.containsKey(option.getName())) left.remove(option.getName());
    }
    return left;
  }

  public abstract void checkValid(List<Option> options) throws InvalidArgumentException;

  protected List<String> flattenOptions(List<Option> options) {
    return options.stream().map(option -> option.getName()).collect(Collectors.toList());
  }
}
