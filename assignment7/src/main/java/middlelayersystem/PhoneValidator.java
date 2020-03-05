package middlelayersystem;

import java.util.Objects;

/**
 * The type Phone validator, stores requirements for user input.
 */
public class PhoneValidator implements Validator<String> {
  private int length;

  /**
   * Instantiates a new Phone validator.
   *
   * @param length the specified length supplied by client code
   */
  public PhoneValidator(int length) {
    this.length = length;
  }

  /**
   * Get the specified length supplied by client code
   *
   * @return the specified length supplied by client code
   */
  public int getLength() {
    return this.length;
  }

  /**
   * Determines if the provided input String meets the requirements
   *
   * @param input the input String to be checked
   * @return true if the input is a valid phone number, false otherwise
   */
  @Override
  public boolean isValid(String input) {
    return this.lengthRequirement(input) && this.typeRequirement(input);
  }

  /**
   * Check if the input String is a numeric type.
   *
   * @param input the input String to be checked
   * @return true if the input is numeric, false otherwise
   */
  private boolean typeRequirement(String input) {
    for (char c: input.toCharArray()) {
      if (!Character.isDigit(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if the input String meets the specified length supplied by client code.
   *
   * @param input the input String to be checked
   * @return true if the input matches the exact length, false otherwise
   */
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
