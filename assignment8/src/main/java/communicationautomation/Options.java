package communicationautomation;

import java.util.*;


/**
 * The type Options, represents a collection of Option objects, which
 * describe the possible options for a command-line..
 */
public class Options {
  private Map<String, Option> allOptions;
  private Map<String, OptionGroup> optionGroups;
  private List<String> requiredOptions;
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
   * Has option group boolean.
   *
   * @param option the option
   * @return the boolean
   */
  public boolean hasOptionGroup(Option option) {
    return this.optionGroups.containsKey(option.getGroup());
  }

  /**
   * Gets option group.
   *
   * @param option the option
   * @return the option group
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
   * Add option group options.
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
   * Add option options.
   *
   * @param option the option
   * @return the options
   */
  public Options addOption(Option option) {
    if (option.isRequired()) {
      requiredOptions.add(option.getName());
    }
    allOptions.put(option.getName(), option);
    return this;
  }

  /**
   * Has option boolean.
   *
   * @param opt the opt
   * @return the boolean
   */
  public boolean hasOption(String opt) {
    return this.allOptions.containsKey(opt);
  }

  /**
   * Gets option.
   *
   * @param opt the opt
   * @return the option
   */
  public Option getOption(String opt) {
    return this.allOptions.get(opt);
  }

  /**
   * Sets usage.
   *
   * @param usage the usage
   */
  public void setUsage(String usage) {
    this.usage = usage;
  }

  /**
   * Gets usage.
   *
   * @return the usage
   */
  public String getUsage() {
    return this.usage;
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
