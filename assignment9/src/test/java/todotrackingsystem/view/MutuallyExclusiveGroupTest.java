package todotrackingsystem.view;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.utils.InvalidArgumentException;

public class MutuallyExclusiveGroupTest {
    private MutuallyExclusiveGroup empty;
    private MutuallyExclusiveGroup meg;
    private Option sort1;
    private Option sort2;
    private List<Option> conflictedOptions;
    @Before
    public void setUp() throws Exception {
        empty = new MutuallyExclusiveGroup("name", false);
        meg = new MutuallyExclusiveGroup("name", false);
        sort1 = new Option.Builder("byDate", "text").build();
        sort2 = new Option.Builder("byNum", "text").build();
        meg.addOption(sort1);
        meg.addOption(sort2);
        conflictedOptions = new ArrayList<Option>() {{
            add(sort1);
            add(sort2);
        }};
        meg.addConflictedOption(conflictedOptions);
    }

    @Test
    public void addConflictedOption() throws InvalidArgumentException {
        meg.addConflictedOption(conflictedOptions);
    }

    @Test
    public void checkValid() throws InvalidArgumentException {
        Option addTodo = new Option.Builder("add", "description").build();
        meg.checkValid(new ArrayList<Option>() {{add(sort1); add(addTodo);}});
    }

    @Test(expected = InvalidArgumentException.class)
    public void conflictOptions() throws InvalidArgumentException {
        meg.checkValid(conflictedOptions);
    }


    @Test
    public void testEquals() throws InvalidArgumentException {
        assertFalse(meg.equals(empty));
        empty.addOption(sort1);
        empty.addOption(sort2);
        empty.addConflictedOption(conflictedOptions);
        assertTrue(meg.equals(empty));
    }

    @Test
    public void testHashCode() {
        assertNotEquals(meg.hashCode(), empty.hashCode());
    }

    @Test
    public void testToString() {
        assertNotEquals(meg.toString(), empty.toString());
    }
}