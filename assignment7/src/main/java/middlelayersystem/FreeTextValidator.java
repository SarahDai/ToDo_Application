package middlelayersystem;

import java.util.Objects;

public class FreeTextValidator implements Validator<String> {
  private int numLines;
  private int numCharPerLine;

  public FreeTextValidator(int numLines, int numCharPerLine) {
    this.numLines = numLines;
    this.numCharPerLine = numCharPerLine;
  }

  @Override
  public boolean isValid(String input) {
    return input.length() <= this.numCharPerLine * this.numLines;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FreeTextValidator that = (FreeTextValidator) o;
    return numLines == that.numLines &&
        numCharPerLine == that.numCharPerLine;
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
