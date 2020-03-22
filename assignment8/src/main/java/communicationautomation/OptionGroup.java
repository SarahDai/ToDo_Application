package communicationautomation;

import java.util.*;

public class OptionGroup {
  private String name;
  private List<Option> options;
  private boolean isRequired;

  public OptionGroup(String name) {
    this.name = name;
    this.options = new ArrayList<>();
  }

  public String getName() {
    return this.name;
  }

  public void addOption(Option option) {
    options.add(option);
    option.setGroup(this.name);
  }

  public boolean isRequired() {
    return this.isRequired;
  }

  public List<Option> getOptions() {
    return this.options;
  }

  public void setRequired(boolean required) {
    isRequired = required;
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
