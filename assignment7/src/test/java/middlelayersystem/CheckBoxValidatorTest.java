package middlelayersystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class CheckBoxValidatorTest {
  private CheckBoxValidator checkbox = new CheckBoxValidator();

  @Test
  public void isValid() {
    assertEquals(true, checkbox.isValid(null));
    assertEquals(true, checkbox.isValid(false));
  }

  @Test
  public void testEquals() {
    assertTrue(checkbox.equals(checkbox));
    assertFalse(checkbox.equals(null));
    assertFalse(checkbox.equals(1));
    assertTrue(checkbox.equals(new CheckBoxValidator()));
  }

  @Test
  public void testHashCode() {
    assertEquals(31, checkbox.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("CheckBoxValidator{}", checkbox.toString());
  }
}