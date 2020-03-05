package middlelayersystem;

import java.util.Objects;

public class PhoneValidator implements Validator<String> {
  private int length;

  public PhoneValidator(int length) {
    this.length = length;
  }

  @Override
  public boolean isValid(String input) {
    return this.lengthRequirement(input) && this.typeRequirement(input);
  }

  private boolean typeRequirement(String input) {
    for (char c: input.toCharArray()) {
      if (!Character.isDigit(c)) {
        return false;
      }
    }
    return true;
  }

  private boolean lengthRequirement(String input) {
    return this.length == input.length();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PhoneValidator that = (PhoneValidator) o;
    return length == that.length;
  }

  @Override
  public int hashCode() {
    return Objects.hash(length);
  }

  @Override
  public String toString() {
    return "PhoneValidator{" +
        "length=" + length +
        '}';
  }
}
