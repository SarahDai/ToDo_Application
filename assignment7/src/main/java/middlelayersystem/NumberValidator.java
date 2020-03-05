package middlelayersystem;

import java.util.Objects;

public class NumberValidator implements Validator<String> {
  private int maxValue;
  private int minValue;
  private int numDecimalPlace;
  private static final int MIN_DECIMAL_PLACE = 0;

  public NumberValidator(int maxValue, int minValue, int numDecimalPlace) {
    this.maxValue = maxValue;
    this.minValue = minValue;
    this.numDecimalPlace = validateDecimalPlace(numDecimalPlace);
  }

  private int validateDecimalPlace(int numDecimalPlace) {
    return Math.max(numDecimalPlace, MIN_DECIMAL_PLACE);
  }

  public int getMaxValue() {
    return maxValue;
  }

  public int getMinValue() {
    return minValue;
  }

  public int getNumDecimalPlace() {
    return numDecimalPlace;
  }

  @Override
  public boolean isValid(String input) {
    if (this.getDecimalPlace(input) == MIN_DECIMAL_PLACE) {
      return isInteger(input);
    } else if (this.getDecimalPlace(input) <= this.numDecimalPlace) {
      return isDouble(input);
    }
    return false;
  }

  private int getDecimalPlace(String input) {
    int decimalIndex = input.indexOf('.');
    return decimalIndex == -1 ? 0 : input.length() - decimalIndex - 1;
  }

  private boolean isInteger(String input) {
    try {
      int value = Integer.parseInt(input);
      return value >= this.minValue && value <= this.maxValue;
    } catch (NumberFormatException e) {
      return false;
    }
  }

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
