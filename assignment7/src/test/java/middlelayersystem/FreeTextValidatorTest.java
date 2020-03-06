package middlelayersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FreeTextValidatorTest {
  FreeTextValidator freeTextValidator;

  @Before
  public void setUp() throws Exception {
    freeTextValidator = new FreeTextValidator(2, 2);
  }

  @Test
  public void getNumLines() {
    assertEquals(2, freeTextValidator.getNumLines());
  }

  @Test
  public void getNumCharPerLine() {
    assertEquals(2, freeTextValidator.getNumCharPerLine());
  }

  @Test
  public void isValid() {
    String input = "555";
    assertTrue(input.length() <= freeTextValidator.getNumCharPerLine() * freeTextValidator.getNumLines() );
    String input2 = "88888";
    assertFalse(input2.length() <= freeTextValidator.getNumLines() * freeTextValidator.getNumCharPerLine());
  }

  @Test
  public void testEquals() {

  }

  @Test
  public void testHashCode() {
    assertEquals();
  }

  @Test
  public void testToString() {
  }
}