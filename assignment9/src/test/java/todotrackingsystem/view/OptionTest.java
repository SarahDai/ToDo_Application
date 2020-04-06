package todotrackingsystem.view;

import static org.junit.Assert.*;

import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;

public class OptionTest {
    private Option addTodo;
    private Option another;
    private Pattern DEFAULT_PATTERN = Pattern.compile(".+");

    @Before
    public void setUp() throws Exception {
        addTodo = new Option.Builder("add",
            "description").build();
        another = new Option.Builder("add",
            "description").build();
    }

    @Test
    public void getArgValue() {
        assertEquals(null, addTodo.getArgValue());
    }

    @Test
    public void getNumOfAppearing() {
        assertEquals(1, addTodo.getNumOfAppearing());
    }

    @Test
    public void getName() {
        assertEquals("add",addTodo.getName());
    }

    @Test
    public void getDescription() {
        assertEquals("description", addTodo.getDescription());
    }

    @Test
    public void isRequired() {
        assertEquals(false, addTodo.isRequired());
    }

    @Test
    public void hasArg() {
        assertEquals(false, addTodo.hasArg());
    }

    @Test
    public void getPattern() {
        Option withDefaultPattern = new Option.Builder("add",
            "description").setPattern(DEFAULT_PATTERN).build();
        assertEquals(DEFAULT_PATTERN, withDefaultPattern.getPattern());
    }

    @Test
    public void getGroup() {
        assertEquals(null, addTodo.getGroup());
    }


    @Test
    public void setGroup() {
        addTodo.setGroup("group");
        assertEquals("group", addTodo.getGroup());
    }

    @Test
    public void setArgValue() {
        addTodo.setArgValue("arg value");
        assertEquals("arg value", addTodo.getArgValue());
    }

    @Test
    public void testEquals() {
        assertTrue(addTodo.equals(addTodo));
        assertTrue(addTodo.equals(another));
        assertFalse(addTodo.equals(null));
        assertFalse(addTodo.equals("add todo"));
        Option diffName = new Option.Builder("diff",
            "description").build();
        Option Required = new Option.Builder("add",
            "description").setRequired().build();
        Option hasArg = new Option.Builder("add",
            "description").setHasArg().build();
        Option diffPattern = new Option.Builder("add",
            "description").setPattern(Pattern.compile(".+\\.csv")).build();
        Option diffDescription = new Option.Builder("add",
            "diff").build();
        Option diffNum = new Option.Builder("add",
            "description").setNumOfAppearing(2).build();

        Option diffArgValue = new Option(addTodo);
        diffArgValue.setArgValue("arg value");

        Option diffGroup = new Option(addTodo);
        diffGroup.setGroup("group name");

        assertFalse(addTodo.equals(diffName));
        assertFalse(addTodo.equals(Required));
        assertFalse(addTodo.equals(hasArg));
        assertFalse(addTodo.equals(diffPattern));
        assertFalse(addTodo.equals(diffDescription));
        assertFalse(addTodo.equals(diffNum));
        assertFalse(addTodo.equals(diffArgValue));
        assertFalse(addTodo.equals(diffGroup));
    }

    @Test
    public void testHashCode() {
        assertEquals(addTodo.hashCode(), another.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(addTodo.toString(), another.toString());
    }
}