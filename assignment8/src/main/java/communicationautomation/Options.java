package communicationautomation;

import java.util.*;


public class Options {
  private Map<String, Option> allOptions;
  private Map<String, OptionGroup> optionGroups;
  private List<String> requiredOptions;
  private String usage;


  public Options() {
    allOptions = new HashMap<>();
    optionGroups = new HashMap<>();
    requiredOptions = new ArrayList<>();
  }

  public Map<String, Option> getAllOptions() {
    return allOptions;
  }

  public Collection<OptionGroup> getOptionGroups() {
    return new HashSet<OptionGroup>(optionGroups.values());
  }

  public boolean hasOptionGroup(Option option) {
    return this.optionGroups.containsKey(option.getGroup());
  }

  public OptionGroup getOptionGroup(Option option) {
    return this.optionGroups.get(option.getGroup());
  }

  public List<String> getRequiredOptions() {
    return this.requiredOptions;
  }

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

  public Options addOption(Option option) {
    if (option.isRequired()) {
      requiredOptions.add(option.getName());
    }
    allOptions.put(option.getName(), option);
    return this;
  }

  public boolean hasOption(String opt) {
    return this.allOptions.containsKey(opt);
  }

  public Option getOption(String opt) {
    return this.allOptions.get(opt);
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

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
