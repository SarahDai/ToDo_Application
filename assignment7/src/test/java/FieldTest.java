package middlelayersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FieldTest {
  private Field<String> password;
  private Field<Boolean> checkbox;
  private PasswordValidator pwdValidator;
  private CheckBoxValidator checkBoxValidator;

  @Before
  public void setUp() throws Exception {
    pwdValidator = new PasswordValidator(6,10);
    pwdValidator.setMinUppercase(1);
    pwdValidator.setMinLowercase(1);
    pwdValidator.setMinDigits(1);
    password = new Field<>("password", true, pwdValidator);

    checkBoxValidator = new CheckBoxValidator();
    checkbox = new Field<>("female",false, checkBoxValidator);
  }

  @Test
  public void getLabel() {
    assertEquals("password", password.getLabel());
  }

  @Test
  public void getRequired() {
    assertEquals(true, password.getRequired());
  }

  @Test
  public void getValidator() {
    assertEquals(pwdValidator, password.getValidator());
  }

  @Test
  public void updateValueSuccess() throws InvalidInputException{
    String valid = "abCdef123";
    password.updateValue(valid);
    assertEquals(valid, password.getValue());
  }

  @Test(expected = InvalidInputException.class)
  public void updateValueFail() throws InvalidInputException {
    String invalid = "Abcdef";
    password.updateValue(invalid);
  }

  @Test
  public void isFilled() throws InvalidInputException{
    assertEquals(true, checkbox.isFilled());

    assertEquals(false, password.isFilled());
    String valid = "A1b2c3d4";
    password.updateValue(valid);
    assertEquals(true, password.isFilled());
  }

  @Test
  public void testEquals() throws Exception{
    assertTrue(password.equals(password));
    assertFalse(password.equals(null));
    assertFalse(password.equals(pwdValidator));

    //same fields
    Field<String> another = new Field<>("password", true, pwdValidator);
    assertTrue(another.equals(password));

    //Different fields
    assertFalse(password.equals(new Field<String>("pwd",true, pwdValidator)));
    assertFalse(password.equals(new Field<String>("password", false, pwdValidator)));
    assertFalse(password.equals(new Field<String>("password", true, checkBoxValidator)));

    password.updateValue("A1B2c3d4");
    assertFalse(password.equals(another));
  }

  @Test
  public void testHashCode() {
    Field<String> another = new Field<>("password", true, pwdValidator);
    assertEquals(password.hashCode(), another.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "Field{label='female', value=null, required=false, validator=CheckBoxValidator{}}";
    assertEquals(expected, checkbox.toString());
  }
}