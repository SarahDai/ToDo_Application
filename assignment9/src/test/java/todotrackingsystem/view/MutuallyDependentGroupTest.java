package todotrackingsystem.view;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.utils.InvalidArgumentException;

public class MutuallyDependentGroupTest {
    private MutuallyDependentGroup empty;
    private MutuallyDependentGroup mdg;
    private Option opt1;
    private Option opt2;
    private List<Option> connectedOptions;
    @Before
    public void setUp() throws Exception {
        empty = new MutuallyDependentGroup("name", false);
        mdg = new MutuallyDependentGroup("name", false);
        opt1 = new Option.Builder("opt1", "text").build();
        opt2 = new Option.Builder("opt2", "text").build();
        mdg.addOption(opt1);
        mdg.addOption(opt2);
        connectedOptions = new ArrayList<Option>() {{
            add(opt1);
            add(opt2);
        }};
        mdg.addConnectedOptions(connectedOptions);
    }

    @Test
    public void addConnectedOptions() throws InvalidArgumentException {
        mdg.addConnectedOptions(connectedOptions);

    }

    @Test
    public void checkValid() throws InvalidArgumentException {
        mdg.checkValid(connectedOptions);
    }

    @Test(expected = InvalidArgumentException.class)
    public void missingOption() throws InvalidArgumentException {
        Option addTodo = new Option.Builder("add", "description").build();
        mdg.checkValid(new ArrayList<Option>() {{add(opt1); add(addTodo);}});
    }

    @Test
    public void testEquals() throws InvalidArgumentException {
        assertFalse(mdg.equals(empty));
        empty.addOption(opt1);
        empty.addOption(opt2);
        empty.addConnectedOptions(connectedOptions);
        assertTrue(mdg.equals(empty));
    }

    @Test
    public void testHashCode() {
        assertNotEquals(mdg.hashCode(), empty.hashCode());
    }

    @Test
    public void testToString() {
        assertNotEquals(mdg.toString(), empty.toString());
    }
}