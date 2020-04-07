package todotrackingsystem.view;

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
  private int numOfAppearing;

  /**
   * The type Builder.
   */
  public static class Builder {
    private String opt;
    private boolean isRequired;
    private boolean hasArg;
    private Pattern pattern;
    private String description;
    private int numOfAppearing;

    /**
     * Instantiates a new Builder.
     *
     * @param opt the opt
     * @param description the description
     */
    public Builder(String opt, String description) {
      this.opt = opt;
      this.description = description;
      this.isRequired = false;
      this.hasArg = false;
      this.pattern = DEFAULT_PATTERN;
      this.numOfAppearing = 1;
    }

    /**
     * Sets required.
     *
     * @return the required
     */
    public Builder setRequired() {
      isRequired = true;
      return this;
    }

    /**
     * Sets has arg.
     *
     * @return the has arg
     */
    public Builder setHasArg() {
      this.hasArg = true;
      return this;
    }

    /**
     * Sets pattern.
     *
     * @param pattern the pattern
     * @return the pattern
     */
    public Builder setPattern(Pattern pattern) {
      this.pattern = pattern;
      return this;
    }

    /**
     * Sets num of appearing.
     *
     * @param numOfAppearing the num of appearing
     * @return the num of appearing
     */
    public Builder setNumOfAppearing(int numOfAppearing) {
      this.numOfAppearing = numOfAppearing;
      return this;
    }

    /**
     * Build option.
     *
     * @return the option
     */
    public Option build() {
      return new Option(this);
    }
  }

  private Option(Builder builder) {
    this.opt = builder.opt;
    this.isRequired = builder.isRequired;
    this.hasArg = builder.hasArg;
    this.pattern = builder.pattern;
    this.description = builder.description;
    this.numOfAppearing = builder.numOfAppearing;
  }

  /**
   * Instantiates a new todotrackingsystem.view.Option.
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
    this.numOfAppearing = opt.numOfAppearing;
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
    return this.description;
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
   * Gets num of appearing.
   *
   * @return the num of appearing
   */
  public int getNumOfAppearing() {
    return this.numOfAppearing;
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
   * Sets argument value for the option.
   *
   * @param argValue the argument value of the option
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
        numOfAppearing == option.numOfAppearing &&
        Objects.equals(opt, option.opt) &&
        Objects.equals(pattern, option.pattern) &&
        Objects.equals(argValue, option.argValue) &&
        Objects.equals(group, option.group) &&
        Objects.equals(description, option.description);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(opt, isRequired, hasArg, pattern, argValue, group, description, numOfAppearing);
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
        ", numOfAppearing=" + numOfAppearing +
        '}';
  }
}
