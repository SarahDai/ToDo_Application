package todotrackingsystem.view;

import java.util.*;


/**
 * The type todotrackingsystem.view.Options, represents a collection of todotrackingsystem.view.Option objects, which
 * describe the possible options for a command-line.
 */
public class Options {

  /**
   * Contains all options, with todotrackingsystem.view.Option name as key, todotrackingsystem.view.Option as value.
   */
  private Map<String, Option> allOptions;
  /**
   * Contains all optionGroups, with todotrackingsystem.view.OptionGroup name as key, todotrackingsystem.view.OptionGroup as value.
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
   * Instantiates a new todotrackingsystem.view.Options.
   */
  public Options() {
    allOptions = new HashMap<>();
    optionGroups = new HashMap<>();
    requiredOptions = new ArrayList<>();
  }

  public Map<String, Option> getAllOptions() {
    return allOptions;
  }

  public OptionGroup getOptionGroup(String groupName) {
    return this.optionGroups.get(groupName);
  }

  public List<String> getRequiredOptions() {
    return this.requiredOptions;
  }

  public void addOptionGroup(OptionGroup group) {
//   If this group is required,
//   then add the required option in this group to the requiredOptions
    if (group.isRequired()) {
      for (Option option: group.getOptions()) {
        this.addOption(option);
      }
    } else {
//      If not, just add all options in this group to all options
//      whether this option is required or not in this group
      for (Option option: group.getOptions()) {
        allOptions.put(option.getName(), option);
      }
    }
    optionGroups.put(group.getName(), group);
  }

  public void addOption(Option option) {
    allOptions.put(option.getName(), option);
    if (option.isRequired()) requiredOptions.add(option.getName());
  }


  /**
   * Check whether the todotrackingsystem.view.Options has the todotrackingsystem.view.Option with the given name.
   *
   * @param opt the name of the todotrackingsystem.view.Option to be searched
   * @return true if the todotrackingsystem.view.Options has the todotrackingsystem.view.Option with the given name, false otherwise
   */
  public boolean hasOption(String opt) {
    return this.allOptions.containsKey(opt);
  }

  /**
   * Gets todotrackingsystem.view.Option with the given name.
   *
   * @param opt the name of the todotrackingsystem.view.Option to be searched
   * @return the todotrackingsystem.view.Option with the given name
   */
  public Option getOption(String opt) {
    return this.allOptions.get(opt);
  }


  /**
   * Sets usage of the todotrackingsystem.view.Options Object.
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
  public String toString() {
    return "todotrackingsystem.view.Options{" +
        "allOptions=" + allOptions +
        ", optionGroups=" + optionGroups +
        ", requiredOptions=" + requiredOptions +
        ", usage='" + usage + '\'' +
        '}';
  }
}
