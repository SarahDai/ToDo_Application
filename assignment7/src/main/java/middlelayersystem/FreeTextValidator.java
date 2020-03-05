package middlelayersystem;

import java.util.Objects;

/**
 * The type Free text validator, stores requirements for user input.
 */
public class FreeTextValidator implements Validator<String> {
  private int numLines;
  private int numCharPerLine;

  /**
   * Instantiates a new Free text validator.
   *
   * @param numLines       the num lines in the text field
   * @param numCharPerLine the number of characters allowed per line
   */
  public FreeTextValidator(int numLines, int numCharPerLine) {
    this.numLines = numLines;
    this.numCharPerLine = numCharPerLine;
  }

  /**
   * Get the number of lines in the text field.
   *
   * @return the num lines in the text field
   */
  public int getNumLines() {
    return this.numLines;
  }

  /**
   * Get the number of characters allowed per line.
   *
   * @return the number of characters allowed per line
   */
  public int getNumCharPerLine() {
    return this.numCharPerLine;
  }

  /**
   * Determines if the provided input meets the requirements
   *
   * @param input the input String to be checked
   * @return true if the input is a valid free text, false otherwise
   */
  @Override
  public boolean isValid(String input) {
    return input.length() <= this.numCharPerLine * this.numLines;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    FreeTextValidator that = (FreeTextValidator) o;
    return this.numLines == that.numLines &&
        this.numCharPerLine == that.numCharPerLine;
  }

  @Override
  public int hashCode() {
    return Objects.hash(numLines, numCharPerLine);
  }

  @Override
  public String toString() {
    return "FreeTextValidator{" +
        "numLines=" + numLines +
        ", numCharPerLine=" + numCharPerLine +
        '}';
  }
}
