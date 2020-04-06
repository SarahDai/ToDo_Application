package todotrackingsystem.view;

import java.util.*;

/**
 * The type todotrackingsystem.view.ValidArgs, represents arguments parsed against an todotrackingsystem.view.Options descriptor.
 */
public class ValidArgs {
  private Map<String, Option> individualOptions;
  private Map<String, List<Option>> optionTypes; //groupName-options

  /**
   * Instantiates a new todotrackingsystem.view.ValidArgs object.
   */
  public ValidArgs() {
    this.individualOptions = new HashMap<>();
    this.optionTypes = new HashMap<>();
  }

  /**
   * Add the processed todotrackingsystem.view.Option to the todotrackingsystem.view.ValidArgs.
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

  public Map<String, List<Option>> getOptionTypes() {
    return optionTypes;
  }

  public Option getIndividualOption(String optName) {
    return individualOptions.get(optName);
  }

  public List<Option> getOptionGroup(String groupName) {
    return optionTypes.get(groupName);
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
    return "todotrackingsystem.view.ValidArgs{" +
        "individualOptions=" + individualOptions +
        ", optionTypes=" + optionTypes +
        '}';
  }
}

