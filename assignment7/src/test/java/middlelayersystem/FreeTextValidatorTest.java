package middlelayersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FreeTextValidatorTest {
  FreeTextValidator freeTextValidator;

  @Before
  public void setUp() throws Exception {
    freeTextValidator = new FreeTextValidator(2,2);

  }

  @Test
  public void getNumLines() {
    assertEquals(freeTextValidator.getNumLines(), 2);
  }

  @Test
  public void getNumCharPerLine() {
    assertEquals(freeTextValidator.getNumCharPerLine(), 2);
  }

  @Test
  public void isValid() {
    assertFalse(freeTextValidator.isValid(null));
    String input = "2333";
    assertTrue(freeTextValidator.isValid(input));
    String input2 = "666666666";
    assertFalse(freeTextValidator.isValid(input2));
  }

  @Test
  public void testEquals() {
    FreeTextValidator freeTextValidator1 = new FreeTextValidator(2,2);
    assertTrue(freeTextValidator.equals(freeTextValidator));
    assertFalse(freeTextValidator.equals(null));
    assertFalse(freeTextValidator.equals(888));

    assertNotEquals(freeTextValidator, new FreeTextValidator(2,3));
    assertNotEquals(freeTextValidator, new FreeTextValidator(3,2));
    assertEquals(freeTextValidator, new FreeTextValidator(2, 2));
  }

  @Test
  public void testHashCode() {
    FreeTextValidator freeTextValidator1 = new FreeTextValidator(2,2);
    assertEquals(freeTextValidator1.hashCode(), freeTextValidator.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals(freeTextValidator.toString(), "FreeTextValidator{numLines=2, numCharPerLine=2}");
  }
}