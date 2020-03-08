package middlelayersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PasswordValidatorTest {
  PasswordValidator passwordValidator;
  PasswordValidator another;

  @Before
  public void setUp() throws Exception {
    passwordValidator = new PasswordValidator(3,3);
    another = new PasswordValidator(8,12);
    another.setDigits(1);
    another.setMinUpperCase(1);
    another.setMinLowerCase(4);
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
    // check white space
    assertEquals(false, passwordValidator.isValid("Ab "));

    // check input is null
    assertFalse(passwordValidator.isValid(null));

   // check input length
    assertFalse(passwordValidator.isValid(""));
    assertFalse(passwordValidator.isValid("bbbbbb"));
  }

  @Test
  public void isValid2(){
    String invalidLowerCase = "aABCDEFG666";
    assertEquals(false, another.isValid(invalidLowerCase));

    String invalidUpperCase = "bcef123wo";
    assertEquals(false, another.isValid(invalidUpperCase));
  }

  @Test
  public void testEquals() {
    assertTrue(passwordValidator.equals(passwordValidator));
    assertFalse(passwordValidator.equals(null));
    assertFalse(passwordValidator.equals(8880));
    assertFalse(passwordValidator.equals(another));
    assertFalse(passwordValidator.equals(new PasswordValidator(3,6)));

    PasswordValidator copy = new PasswordValidator(8, 12);
    //Different minLowerCase
    copy.setMinLowerCase(2);
    assertFalse(another.equals(copy));
    //Different minUpperCase
    copy.setMinLowerCase(4);
    copy.setMinUpperCase(2);
    assertFalse(another.equals(copy));
    //Different minDigit
    copy.setMinUpperCase(1);
    copy.setDigits(2);
    assertFalse(another.equals(copy));

    //all same properties
    copy.setDigits(1);
    assertTrue(another.equals(copy));
  }

  @Test
  public void testHashCode() {
    assertNotEquals(passwordValidator.hashCode(), another.hashCode());
    assertEquals(passwordValidator.hashCode(), new PasswordValidator(3,3).hashCode());
  }

  @Test
  public void testToString() {
    assertEquals(passwordValidator.toString(), "PasswordValidator{minLen=3, maxLen=3, minLowerCase=0, minUpperCase=0, minDigits=0}");
  }
}