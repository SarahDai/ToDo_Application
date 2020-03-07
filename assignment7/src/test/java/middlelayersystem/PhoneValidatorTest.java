package middlelayersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PhoneValidatorTest {
    PhoneValidator phone;
    PhoneValidator equal;
    @Before
    public void setUp() throws Exception {
        phone = new PhoneValidator(5);
        equal = new PhoneValidator(5);
    }

    @Test
    public void getLength() {
        assertEquals(5, phone.getLength());
    }

    @Test
    public void isValid() {
        assertTrue(phone.isValid("12345"));
        assertFalse(phone.isValid(null));
        assertFalse(phone.isValid("123"));
        assertFalse(phone.isValid("12!"));
        assertFalse(phone.isValid("12!45"));
    }

    @Test
    public void testEquals() {
        assertEquals(phone, phone);
        assertEquals(phone, equal);
        assertNotEquals(phone, null);
        assertNotEquals(phone, "phone");
        assertNotEquals(phone, new PhoneValidator(3));
    }

    @Test
    public void testHashCode() {
        assertEquals(equal.hashCode(), phone.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("PhoneValidator{length=5}", phone.toString());
    }
}