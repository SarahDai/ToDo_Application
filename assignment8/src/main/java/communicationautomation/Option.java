package communicationautomation;

import java.util.regex.Pattern;

public class Option {
  private static final Pattern DEFAULT_PATTERN = Pattern.compile(".+");
  private String opt;
  private boolean isRequired;
  private boolean hasArg;
  private Pattern pattern;
  private String argValue;
  private String group;
  private String description;

  public Option(String opt, String description) {
    this.opt = opt;
    this.description = description;
  }

  public Option(String opt, boolean isRequired, String description) {
    this.opt = opt;
    this.isRequired = isRequired;
    this.description = description;
  }

  public Option(String opt, boolean isRequired, boolean hasArg, String description) {
    this.opt = opt;
    this.isRequired = isRequired;
    this.hasArg = hasArg;
    this.argValue = null;
    this.pattern = DEFAULT_PATTERN;
    this.description = description;
  }

  public Option(String opt, boolean isRequired, boolean hasArg, Pattern pattern, String description) {
    this.opt = opt;
    this.isRequired = isRequired;
    this.hasArg = hasArg;
    this.argValue = null;
    this.pattern = pattern;
    this.description = description;
  }

  public Option(Option opt) {
    this.opt = opt.opt;
    this.isRequired = opt.isRequired;
    this.hasArg = opt.hasArg;
    this.argValue = null;
    this.pattern = opt.pattern;
    this.group = opt.group;
    this.description = opt.description;
  }

  public String getName() {
    return this.opt;
  }

  public String getOpt() {
    return opt;
  }

  public String getDescription() {
    return description;
  }

  public boolean isRequired() {
    return this.isRequired;
  }

  public boolean hasArg() {
    return this.hasArg;
  }

  public String getArgValue() {
    return this.argValue;
  }

  public Pattern getPattern() {
    return this.pattern;
  }

  public String getGroup() {
    return this.group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public void setArgValue(String argValue) {
    this.argValue = argValue;
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
