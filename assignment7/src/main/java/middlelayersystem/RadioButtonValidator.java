package middlelayersystem;

import java.util.HashMap;
import middlelayersystem.Validator;

/**
 * Class Radio Button Validator indicating whether or not the button is selected
 */
public class RadioButtonValidator implements Validator<Boolean> {

  /**
   * Helper method that check whether or not the input is valid.
   * @param input a Boolean input
   * @return true if it's not null,false otherwise
   */
  @Override
  public boolean isValid(Boolean input) {
    return input != null;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return "RadioButtonValidator{}";
  }
}
