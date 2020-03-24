package communicationautomation;

import java.util.*;

/**
 * The type OptionGroup, representing a collection of the mutually dependent Options.
 * The options in a group is dependent and must co-occur.
 */
public class OptionGroup {
  private String name;
  private List<Option> options;
  private boolean isRequired;

  /**
   * Instantiates a new Option group.
   *
   * @param name the name
   */
  public OptionGroup(String name) {
    this.name = name;
    this.options = new ArrayList<>();
    isRequired = false;
  }

  /**
   * Gets name of the OptionGroup.
   *
   * @return the name of the OptionGroup.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Add option into the option group, set the group name of the Option meanwhile.
   *
   * @param option the option to be added into the option group.
   */
  public void addOption(Option option) {
    options.add(option);
    option.setGroup(this.name);
  }

  /**
   * Check whether the whole option group is required or not.
   *
   * @return true if the group is required, false otherwise
   */
  public boolean isRequired() {
    return this.isRequired;
  }

  /**
   * Gets the list of options that belongs to the option group.
   *
   * @return the options belonging to the option group.
   */
  public List<Option> getOptions() {
    return this.options;
  }

  /**
   * Sets required flag of the Option Group.
   *
   * @param required boolean value whether the option group is required.
   */
  public void setRequired(boolean required) {
    isRequired = required;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OptionGroup that = (OptionGroup) o;
    return isRequired == that.isRequired &&
        Objects.equals(name, that.name) &&
        Objects.equals(options, that.options);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, options, isRequired);
  }

  @Override
  public String toString() {
    return "OptionGroup{" +
            "name='" + name + '\'' +
            ", options=" + options +
            ", isRequired=" + isRequired +
            '}';
  }
}
