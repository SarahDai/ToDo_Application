import java.util.*;

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
    this.optionTypes = new HashMap<>();
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
    return "ValidArgs{" +
        "individualOptions=" + individualOptions +
        ", optionTypes=" + optionTypes +
        '}';
  }
}

