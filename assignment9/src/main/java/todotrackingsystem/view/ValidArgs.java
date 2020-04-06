package todotrackingsystem.view;

import java.util.*;
import todotrackingsystem.utils.OptionRequestComparator;

/**
 * The type ValidArgs, represents arguments parsed against an Options descriptor.
 */
public class ValidArgs {
  private Map<String, Option> individualOptions;
  private Map<String, List<Option>> optionTypes; //groupName-options

  /**
   * Instantiates a new ValidArgs object.
   */
  public ValidArgs() {
    this.individualOptions = new HashMap<>();
    this.optionTypes = new TreeMap<>(new OptionRequestComparator());
  }

  /**
   * Add the processed Option to the ValidArgs.
   *
   * @param opt the opt to be added
   */
  public void addOption(Option opt) {
    if (opt.getGroup() != null) {
      List<Option> opts = this.optionTypes.getOrDefault(opt.getGroup(), new ArrayList<>());
      opts.add(opt);
      this.optionTypes.put(opt.getGroup(), opts);
    } else {
      this.individualOptions.put(opt.getName(), opt);
    }
  }

  /**
   * Gets option types.
   *
   * @return the option types
   */
  public Map<String, List<Option>> getOptionTypes() {
    return optionTypes;
  }

  /**
   * Gets individual option.
   *
   * @param optName the opt name
   * @return the individual option
   */
  public Option getIndividualOption(String optName) {
    return individualOptions.get(optName);
  }

  /**
   * Gets option group.
   *
   * @param groupName the group name
   * @return the option group
   */
  public List<Option> getOptionGroup(String groupName) {
    return optionTypes.get(groupName);
  }

  /**
   * Check if the individual option is inside the validArgs by option name.
   *
   * @param optName the opt name
   * @return true if the individual option is inside the validArgs by option name
   */
  public boolean hasIndividualOption(String optName) {
    return this.individualOptions.containsKey(optName);
  }

  /**
   * Check if the option group is inside the validArgs by group name.
   *
   * @param groupName the group name
   * @return true if the option group is inside the validArgs.
   */
  public boolean hasGroup(String groupName) {
    return this.optionTypes.containsKey(groupName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValidArgs validArgs = (ValidArgs) o;
    return Objects.equals(individualOptions, validArgs.individualOptions) &&
        Objects.equals(optionTypes, validArgs.optionTypes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(individualOptions, optionTypes);
  }

  @Override
  public String toString() {
    return "ValidArgs{" +
        "individualOptions=" + individualOptions +
        ", optionTypes=" + optionTypes +
        '}';
  }
}

