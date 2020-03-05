package middlelayersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RadioButtonValidatorTest {
  private RadioButtonValidator radioButton;

  @Before
  public void setUp() throws Exception {
    radioButton = new RadioButtonValidator();
  }

  @Test
  public void isValid() {
    assertEquals(true, radioButton.isValid(true));
    assertEquals(false, radioButton.isValid(null));
  }

  @Test
  public void testEquals() {
    assertTrue(radioButton.equals(radioButton));
    assertFalse(radioButton.equals(null));
    assertFalse(radioButton.equals(2));
    assertTrue(radioButton.equals(new RadioButtonValidator()));
  }

  @Test
  public void testHashCode() {
    assertEquals(31, radioButton.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("RadioButtonValidator{}", radioButton.toString());
  }
}