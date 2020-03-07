package middlelayersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PasswordValidatorTest {
  PasswordValidator passwordValidator;

  @Before
  public void setUp() throws Exception {
    passwordValidator = new PasswordValidator(3, 3);
  }

  @Test
  public void getMinLen() {
    assertEquals(passwordValidator.getMinLen(), 3);
  }

  @Test
  public void getMaxLen() {
    assertEquals(passwordValidator.getMaxLen(), 3);
  }

  @Test
  public void getMinLowerCase() {
    assertEquals(0, passwordValidator.getMinLowerCase());
  }

  @Test
  public void getMinUpperCase() {
    assertEquals(0, passwordValidator.getMinUpperCase());
  }

  @Test
  public void getDigits() {
    assertEquals(0, passwordValidator.getDigits());
  }

  @Test
  public void isValid() {
    assertFalse(passwordValidator.isValid(null));
  }

  @Test
  public void testEquals() {
    assertTrue(passwordValidator.equals(passwordValidator));
    assertFalse(passwordValidator.equals(null));
    assertFalse(passwordValidator.equals(888));
    assertFalse(super.equals(passwordValidator));

    assertEquals(passwordValidator.getMinLen(), new PasswordValidator(3, 3).getMinLen());

  }

  @Test
  public void testHashCode() {
    assertEquals(passwordValidator.hashCode(), 31489087);
  }

  @Test
  public void testToString() {
    assertEquals(passwordValidator.toString(), "PasswordValidator{minLen=3, maxLen=3, minLowerCase=0, minUpperCase=0, minDigits=0}");
  }
}