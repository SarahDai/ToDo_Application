package middlelayersystem;

import java.util.Objects;

/**
 * The type Number validator, stores requirements for user input.
 */
public class NumberValidator implements Validator<String> {
  private int maxValue;
  private int minValue;
  private int numDecimalPlace;
  private static final int MIN_DECIMAL_PLACE = 0;

  /**
   * Instantiates a new Number validator.
   *
   * @param maxValue        the max value for the number
   * @param minValue        the min value for the number
   * @param numDecimalPlace the maximum number of decimal place
   */
  public NumberValidator(int maxValue, int minValue, int numDecimalPlace) {
    this.maxValue = maxValue;
    this.minValue = minValue;
    this.numDecimalPlace = validateDecimalPlace(numDecimalPlace);
  }

  /**
   * Validate number of decimal place, if given negative number of decimal place,
   * reset it to 0.
   *
   * @param numDecimalPlace the user input number of decimal place
   * @return if the input is negative, return 0. Otherwise return the user input
   */
  private int validateDecimalPlace(int numDecimalPlace) {
    return Math.max(numDecimalPlace, MIN_DECIMAL_PLACE);
  }

  /**
   * Gets max value for the number.
   *
   * @return the max value for the number
   */
  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * Gets min value for the number.
   *
   * @return the min value for the number
   */
  public int getMinValue() {
    return this.minValue;
  }

  /**
   * Gets the maximum number of decimal place allowed.
   *
   * @return the maximum number of decimal place
   */
  public int getNumDecimalPlace() {
    return this.numDecimalPlace;
  }

  /**
   * Determines if the provided input String meets the requirements
   * If the number of decimal place of the input is 0, the input must be an Integer
   * i.e. "3." would be an invalid case
   *
   * @param input the input to be checked
   * @return true if the input is a valid phone number, false otherwise
   */
  @Override
  public boolean isValid(String input) {
    if (this.getDecimalPlace(input) == MIN_DECIMAL_PLACE) {
      return this.isInteger(input);
    } else if (this.getDecimalPlace(input) <= this.numDecimalPlace) {
      return this.isDouble(input);
    }
    return false;
  }

  /**
   * Calculate the number of decimal place for the input String.
   *
   * @param input the input String to be checked
   * @return the number of decimal place for the input
   */
  private int getDecimalPlace(String input) {
    int decimalIndex = input.indexOf('.');
    return decimalIndex == -1 ? 0 : input.length() - decimalIndex - 1;
  }

  /**
   * Check whether the input could be parsed into an Integer.
   *
   * @param input the input String to be checked
   * @return true if the input is an Integer, false otherwise
   */
  private boolean isInteger(String input) {
    try {
      int value = Integer.parseInt(input);
      return value >= this.minValue && value <= this.maxValue;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Check whether the input could be parsed into a Double.
   *
   * @param input the input String to be checked
   * @return true if the input is a Double, false otherwise
   */
  private boolean isDouble(String input) {
    try {
      double value = Double.parseDouble(input);
      return value >= this.minValue && value <= this.maxValue;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NumberValidator that = (NumberValidator) o;
    return maxValue == that.maxValue &&
        minValue == that.minValue &&
        numDecimalPlace == that.numDecimalPlace;
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxValue, minValue, numDecimalPlace);
  }

  @Override
  public String toString() {
    return "NumberValidator{" +
        "maxValue=" + maxValue +
        ", minValue=" + minValue +
        ", numDecimalPlace=" + numDecimalPlace +
        '}';
  }
}
