package communicationautomation;

import java.util.*;


/**
 * The type Options, represents a collection of Option objects, which
 * describe the possible options for a command-line.
 */
public class Options {

  /**
   * Contains all options, with Option name as key, Option as value.
   */
  private Map<String, Option> allOptions;
  /**
   * Contains all optionGroups, with OptionGroup name as key, OptionGroup as value.
   */
  private Map<String, OptionGroup> optionGroups;

  /**
   * Store all required options name in the list.
   */
  private List<String> requiredOptions;

  /**
   * Store the specified options specific usage.
   */
  private String usage;


  /**
   * Instantiates a new Options.
   */
  public Options() {
    allOptions = new HashMap<>();
    optionGroups = new HashMap<>();
    requiredOptions = new ArrayList<>();
  }

  /**
   * Gets all options.
   *
   * @return the all options
   */
  public Map<String, Option> getAllOptions() {
    return allOptions;
  }

  /**
   * Gets option groups.
   *
   * @return the option groups
   */
  public Collection<OptionGroup> getOptionGroups() {
    return new HashSet<OptionGroup>(optionGroups.values());
  }

  /**
   * Check whether the Options object contains the OptionGroup of the given Option.
   *
   * @param option the option whose optionGroup to be searched.
   * @return true if the Options contains the OptionGroup of the given Option.
   */
  public boolean hasOptionGroup(Option option) {
    return this.optionGroups.containsKey(option.getGroup());
  }

  /**
   * Return the OptionGroup the given Option belongs to.
   *
   * @param option the option whose optionGroup to be searched.
   * @return the OptionGroup the given Option belongs to.
   */
  public OptionGroup getOptionGroup(Option option) {
    return this.optionGroups.get(option.getGroup());
  }

  /**
   * Gets required options.
   *
   * @return the required options
   */
  public List<String> getRequiredOptions() {
    return this.requiredOptions;
  }

  /**
   * Add OptionGroup into the Options object.
   *
   * @param group the group
   * @return the options
   */
  public Options addOptionGroup(OptionGroup group) {
    for (Option option: group.getOptions()) {
      if (group.isRequired()) {
        requiredOptions.add(option.getName());
      }
      allOptions.put(option.getName(), option);
    }
    optionGroups.put(group.getName(), group);
    return this;
  }

  /**
   * Add the Option into the Options object.
   *
   * @param option the option to be added
   * @return the Options object who has add the input option in.
   */
  public Options addOption(Option option) {
    if (option.isRequired()) {
      requiredOptions.add(option.getName());
    }
    allOptions.put(option.getName(), option);
    return this;
  }

  /**
   * Check whether the Options has the Option with the given name.
   *
   * @param opt the name of the Option to be searched
   * @return true if the Options has the Option with the given name, false otherwise
   */
  public boolean hasOption(String opt) {
    return this.allOptions.containsKey(opt);
  }

  /**
   * Gets Option with the given name.
   *
   * @param opt the name of the Option to be searched
   * @return the Option with the given name
   */
  public Option getOption(String opt) {
    return this.allOptions.get(opt);
  }

  /**
   * Sets usage of the Options Object.
   *
   * @param usage the usage of the possible options for a command-line.
   */
  public void setUsage(String usage) {
    this.usage = usage;
  }

  /**
   * Gets usage of the possible options for a command-line.
   *
   * @return the usage the possible options for a command-line.
   */
  public String getUsage() {
    return this.usage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Options options = (Options) o;
    return Objects.equals(allOptions, options.allOptions) &&
        Objects.equals(optionGroups, options.optionGroups) &&
        Objects.equals(requiredOptions, options.requiredOptions) &&
        Objects.equals(usage, options.usage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allOptions, optionGroups, requiredOptions, usage);
  }

  @Override
  public String toString() {
    return "Options{" +
            "allOptions=" + allOptions +
            ", optionGroups=" + optionGroups +
            ", requiredOptions=" + requiredOptions +
            '}';
  }
}
