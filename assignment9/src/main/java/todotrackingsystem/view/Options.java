package todotrackingsystem.view;

import java.util.*;


/**
 * The type Options, represents a collection of
 * Option objects, which describe the possible options for a command-line.
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
   * Gets option group.
   *
   * @param groupName the group name
   * @return the option group
   */
  public OptionGroup getOptionGroup(String groupName) {
    return this.optionGroups.get(groupName);
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
   * Add option group.
   * If this group is required, then add the required option in this group
   * to the requiredOptions, If not, just add all options in this group to
   * all options whether this option is required or not in this group.
   * @param group the group to be added
   */
  public void addOptionGroup(OptionGroup group) {
    if (group.isRequired()) {
      for (Option option: group.getOptions()) {
        this.addOption(option);
      }
    } else {
      for (Option option: group.getOptions()) {
        allOptions.put(option.getName(), option);
      }
    }
    optionGroups.put(group.getName(), group);
  }

  /**
   * Add option.
   * The option will be added to required options
   * if it's required in this group.
   * @param option the option
   */
  public void addOption(Option option) {
    allOptions.put(option.getName(), option);
    if (option.isRequired()) requiredOptions.add(option.getName());
  }


  /**
   * Check whether the Options has the option with
   * the given name.
   *
   * @param opt the name of the Option to be searched
   * @return true if the Options has the Option
   * with the given name, false otherwise
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

  /**
   * Gets option groups.
   *
   * @return the option groups
   */
  public Collection<OptionGroup> getOptionGroups() {
    return new HashSet<>(optionGroups.values());
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
        ", usage='" + usage + '\'' +
        '}';
  }
}
