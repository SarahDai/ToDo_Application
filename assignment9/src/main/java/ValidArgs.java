import java.util.*;

/**
 * The type ValidArgs, represents arguments parsed against an Options descriptor.
 */
public class ValidArgs {
  private Map<String, Option> options;
  private Map<String, List<Option>> optionTypes; //groupName-options

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
    if (opt.getGroup() != null) {
      List<Option> opts = this.optionTypes.getOrDefault(opt.getGroup(), new ArrayList<>());
      opts.add(opt);
      this.optionTypes.put(opt.getGroup(), opts);
    }
  }

  public Map<String, List<Option>> getOptionTypes() {
    return optionTypes;
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
    return Objects.equals(options, validArgs.options) &&
        Objects.equals(optionTypes, validArgs.optionTypes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(options, optionTypes);
  }

  @Override
  public String toString() {
    return "ValidArgs{" +
        "options=" + options +
        ", optionTypes=" + optionTypes +
        '}';
  }
}

