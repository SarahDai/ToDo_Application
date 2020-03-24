package communicationautomation;

import java.util.*;

/**
 * The type ValidArgs, represents arguments parsed against an Options descriptor.
 */
public class ValidArgs {

  private static final String TEMPLATE = "template";
  private Map<String, Option> options;
  private Map<String, List<Option>> optionTypes;

  /**
   * Instantiates a new ValidArgs object.
   */
  public ValidArgs() {
    this.options = new HashMap<>();
    this.optionTypes = new HashMap<>();
  }

  /**
   * Add the processed Option to the ValidArgs.
   *
   * @param opt the opt to be added
   */
  public void addOption(Option opt) {
    this.options.put(opt.getName(), opt);
    if (opt.getName().contains(TEMPLATE)) {
      List<Option> list = optionTypes.getOrDefault(TEMPLATE, new ArrayList<>());
      list.add(opt);
      this.optionTypes.put(TEMPLATE, list);
    }
  }

  /**
   * Gets option based on the option name.
   *
   * @param opt the name of the Option to search for
   * @return the option with the name
   */
  public Option getOption(String opt) {
    return this.options.get(opt);
  }

  /**
   * Gets list of options of certain type, i.e. template here.
   *
   * @param groupType the group type to search for
   * @return the list of options of the type
   */
  public List<Option> getGroupOptions(String groupType) {
    return this.optionTypes.get(groupType);
  }

  /**
   * Gets all options parsed against an Options descriptor.
   *
   * @return the options
   */
  public Collection<Option> getOptions() {
    return this.options.values();
  }

  /**
   * Checks whether the ValidArgs has the Option with the given name.
   *
   * @param option the name of the Option to search for
   * @return true if the ValidArgs has the Option, false otherwise
   */
  public boolean hasOption(String option) {
    return this.options.containsKey(option);
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
    return Objects.equals(options, validArgs.options);
  }

  @Override
  public int hashCode() {
    return Objects.hash(options);
  }

  @Override
  public String toString() {
    return "ValidArgs{" +
        "options=" + options +
        ", optionTypes=" + optionTypes +
        '}';
  }
}

