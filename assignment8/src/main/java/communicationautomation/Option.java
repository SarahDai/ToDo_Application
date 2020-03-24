package communicationautomation;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The type Option.
 */
public class Option {
  private static final Pattern DEFAULT_PATTERN = Pattern.compile(".+");
  private String opt;
  private boolean isRequired;
  private boolean hasArg;
  private Pattern pattern;
  private String argValue;
  private String group;
  private String description;

  /**
   * Instantiates a new Option.
   *
   * @param opt         the opt
   * @param description the description
   */
  public Option(String opt, String description) {
    this.opt = opt;
    this.description = description;
  }

  /**
   * Instantiates a new Option.
   *
   * @param opt         the opt
   * @param isRequired  the is required
   * @param description the description
   */
  public Option(String opt, boolean isRequired, String description) {
    this.opt = opt;
    this.isRequired = isRequired;
    this.description = description;
  }

  /**
   * Instantiates a new Option.
   *
   * @param opt         the opt
   * @param isRequired  the is required
   * @param hasArg      the has arg
   * @param description the description
   */
  public Option(String opt, boolean isRequired, boolean hasArg, String description) {
    this.opt = opt;
    this.isRequired = isRequired;
    this.hasArg = hasArg;
    this.argValue = null;
    this.pattern = DEFAULT_PATTERN;
    this.description = description;
  }

  /**
   * Instantiates a new Option.
   *
   * @param opt         the opt
   * @param isRequired  the is required
   * @param hasArg      the has arg
   * @param pattern     the pattern
   * @param description the description
   */
  public Option(String opt, boolean isRequired, boolean hasArg, Pattern pattern, String description) {
    this.opt = opt;
    this.isRequired = isRequired;
    this.hasArg = hasArg;
    this.argValue = null;
    this.pattern = pattern;
    this.description = description;
  }

  /**
   * Instantiates a new Option.
   *
   * @param opt the opt
   */
  public Option(Option opt) {
    this.opt = opt.opt;
    this.isRequired = opt.isRequired;
    this.hasArg = opt.hasArg;
    this.argValue = null;
    this.pattern = opt.pattern;
    this.group = opt.group;
    this.description = opt.description;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return this.opt;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Is required boolean.
   *
   * @return the boolean
   */
  public boolean isRequired() {
    return this.isRequired;
  }

  /**
   * Has arg boolean.
   *
   * @return the boolean
   */
  public boolean hasArg() {
    return this.hasArg;
  }

  /**
   * Gets arg value.
   *
   * @return the arg value
   */
  public String getArgValue() {
    return this.argValue;
  }

  /**
   * Gets pattern.
   *
   * @return the pattern
   */
  public Pattern getPattern() {
    return this.pattern;
  }

  /**
   * Gets group.
   *
   * @return the group
   */
  public String getGroup() {
    return this.group;
  }

  /**
   * Sets group.
   *
   * @param group the group
   */
  public void setGroup(String group) {
    this.group = group;
  }

  /**
   * Sets arg value.
   *
   * @param argValue the arg value
   */
  public void setArgValue(String argValue) {
    this.argValue = argValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Option option = (Option) o;
    return isRequired == option.isRequired &&
        hasArg == option.hasArg &&
        Objects.equals(opt, option.opt) &&
        Objects.equals(pattern, option.pattern) &&
        Objects.equals(argValue, option.argValue) &&
        Objects.equals(group, option.group) &&
        Objects.equals(description, option.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(opt, isRequired, hasArg, pattern, argValue, group, description);
  }

  @Override
  public String toString() {
    return "Option{" +
            "opt='" + opt + '\'' +
            ", isRequired=" + isRequired +
            ", hasArg=" + hasArg +
            ", pattern=" + pattern +
            ", argValue='" + argValue + '\'' +
            ", group='" + group + '\'' +
            ", description='" + description + '\'' +
            '}';
  }
}
