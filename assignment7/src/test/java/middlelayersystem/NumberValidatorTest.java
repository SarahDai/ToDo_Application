package middlelayersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NumberValidatorTest {
    NumberValidator validator;
    NumberValidator equal;

    @Before
    public void setUp() throws Exception {
        validator = new NumberValidator(5, 1, 3);
        equal = new NumberValidator(5, 1, 3);

    }

    @Test
    public void getMaxValue() {
        assertEquals(validator.getMaxValue(), 5);
    }

    @Test
    public void getMinValue() {
        assertEquals(validator.getMinValue(), 1);
    }

    @Test
    public void getNumDecimalPlace() {
        assertEquals(validator.getNumDecimalPlace(), 3);
    }

    @Test
    public void isValid() {
        assertTrue(validator.isValid("3.4"));
        assertTrue(validator.isValid("3"));
        assertFalse(validator.isValid(null));
        assertFalse(validator.isValid("0"));
        assertFalse(validator.isValid("6"));
        assertFalse(validator.isValid("3.1234"));
        assertFalse(validator.isValid("0.5"));
        assertFalse(validator.isValid("5.5"));
        assertFalse(validator.isValid("5.5."));
        assertFalse(validator.isValid("3."));
    }



    @Test
    public void testEquals() {
        NumberValidator diff_maxValue = new NumberValidator(3, 1, 3);
        NumberValidator diff_minValue = new NumberValidator(5, 4, 3);
        NumberValidator diff_decimalPlace = new NumberValidator(5, 1, 5);
        assertEquals(validator, equal);
        assertEquals(validator, validator);
        assertNotEquals(validator, null);
        assertNotEquals(validator, "validator");
        assertNotEquals(validator, diff_maxValue);
        assertNotEquals(validator, diff_minValue);
        assertNotEquals(validator, diff_decimalPlace);
    }

    @Test
    public void testHashCode() {
        assertEquals(equal.hashCode(), validator.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("NumberValidator{maxValue=5, minValue=1, numDecimalPlace=3}", validator.toString());
    }
}