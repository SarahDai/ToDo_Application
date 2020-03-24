package communicationautomation;

import java.util.*;

/**
 * The type Valid args.
 */
public class ValidArgs {

  private static final String TEMPLATE = "template";
  private static final List<Option> TEMPLATE_LIST = new ArrayList<>();
  private Map<String, Option> options;
  private Map<String, List<Option>> optionTypes;

  /**
   * Instantiates a new Valid args.
   */
  public ValidArgs() {
    this.options = new HashMap<>();
    this.optionTypes = new HashMap<>();
    this.optionTypes.put(TEMPLATE, TEMPLATE_LIST);
  }

  /**
   * Add option.
   *
   * @param opt the opt
   */
  public void addOption(Option opt) {
    this.options.put(opt.getName(), opt);
    if (opt.getName().contains(TEMPLATE)) {
      TEMPLATE_LIST.add(opt);
      this.optionTypes.put(TEMPLATE, TEMPLATE_LIST);
    }
  }

  /**
   * Gets option.
   *
   * @param opt the opt
   * @return the option
   */
  public Option getOption(String opt) {
    return this.options.get(opt);
  }

  /**
   * Gets group options.
   *
   * @param groupType the group type
   * @return the group options
   */
  public List<Option> getGroupOptions(String groupType) {
    return this.optionTypes.get(groupType);
  }

  /**
   * Gets options.
   *
   * @return the options
   */
  public Collection<Option> getOptions() {
    return this.options.values();
  }

  /**
   * Has option boolean.
   *
   * @param option the option
   * @return the boolean
   */
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

