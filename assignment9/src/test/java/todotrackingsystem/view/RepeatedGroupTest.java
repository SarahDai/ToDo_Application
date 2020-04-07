package todotrackingsystem.view;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.utils.InvalidArgumentException;

public class RepeatedGroupTest {
    private RepeatedGroup empty;
    private RepeatedGroup rg;
    private Option addTodo;
    private Option required;
    private List<Option> repeatedOptions;

    @Before
    public void setUp() throws Exception {
        empty = new RepeatedGroup("name", false);
        rg = new RepeatedGroup("name", false);
        addTodo = new Option.Builder("add", "description").build();
        required = new Option.Builder("add", "description").setRequired().build();
        rg.addOption(addTodo);
        repeatedOptions = new ArrayList<Option>() {{ add(addTodo); }};
    }

    @Test
    public void addRepeatedOptions() throws InvalidArgumentException {
        rg.addRepeatedOptions(repeatedOptions);
    }

    @Test(expected = InvalidArgumentException.class)
    public void addUnknownOptionToRepeatedOptions() throws InvalidArgumentException {
        empty.addRepeatedOptions(repeatedOptions);
    }

    @Test
    public void checkValid() throws InvalidArgumentException {
        rg.checkValid(repeatedOptions);
    }

    @Test(expected = InvalidArgumentException.class)
    public void requiredMissing() throws InvalidArgumentException {
        empty.addOption(required);
        empty.checkValid(new ArrayList<>());
    }

    @Test(expected = InvalidArgumentException.class)
    public void repeatingTooManyTimes() throws InvalidArgumentException {
        rg.checkValid(new ArrayList<Option>() {{ add(addTodo); add(addTodo);}});
    }

    @Test
    public void testEquals() {
        assertTrue(rg.equals(rg));
        assertFalse(rg.equals(empty));
        empty.addOption(addTodo);
        assertTrue(rg.equals(empty));
        assertFalse(rg.equals(null));
        assertFalse(rg.equals("group"));
        RepeatedGroup diffName = new RepeatedGroup("diff", false);
        RepeatedGroup isRequired = new RepeatedGroup("name", true);
        empty.addOption(required);
        assertFalse(rg.equals(diffName));
        assertFalse(rg.equals(isRequired));
        assertFalse(rg.equals(empty));
    }

    @Test
    public void testHashCode() {
        empty.addOption(addTodo);
        assertEquals(rg.hashCode(), empty.hashCode());
    }

    @Test
    public void testToString() {
        empty.addOption(addTodo);
        assertEquals(rg.toString(), empty.toString());
    }
}