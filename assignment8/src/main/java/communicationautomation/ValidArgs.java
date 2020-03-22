package communicationautomation;

import java.util.*;

public class ValidArgs {

  private static final String TEMPLATE = "template";
  private static final List<Option> TEMPLATE_LIST = new ArrayList<>();
  private Map<String, Option> options;
  private Map<String, List<Option>> optionTypes;

  public ValidArgs() {
    this.options = new HashMap<>();
    this.optionTypes = new HashMap<>();
    this.optionTypes.put(TEMPLATE, TEMPLATE_LIST);
  }

  //TODO: not hard code TEMPLATE here
  public void addOption(Option opt) {
    this.options.put(opt.getName(), opt);
    if (opt.getName().contains(TEMPLATE)) {
      TEMPLATE_LIST.add(opt);
      this.optionTypes.put(TEMPLATE, TEMPLATE_LIST);
    }
  }

  public Option getOption(String opt) {
    return this.options.get(opt);
  }

  public List<Option> getGroupOptions(String groupType) {
    return this.optionTypes.get(groupType);
  }

  public Collection<Option> getOptions() {
    return this.options.values();
  }

  public boolean hasOption(String option) {
    return this.options.containsKey(option);
  }

  @Override
  public String toString() {
    return "ValidArgs{" +
        "options=" + options +
        ", optionTypes=" + optionTypes +
        '}';
  }
}

