package todotrackingsystem.view;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.utils.OptionRequestComparator;

public class ValidArgsTest {

    private Option opt1;
    private Map<String, List<Option>> groupMap;
    private ValidArgs validArgs;
    private ValidArgs empty;
    private List<Option> options;

    @Before
    public void setUp() throws Exception {
        validArgs = new ValidArgs();
        empty = new ValidArgs();
        opt1 = new Option.Builder("opt1", "text").build();
        groupMap = new TreeMap<>(new OptionRequestComparator());
        options = new ArrayList<Option>() {{
            add(opt1);
        }};
    }

    @Test
    public void getOptionTypes() {
        groupMap.put("--complete-todo", options);
        OptionGroup opg = new RepeatedGroup("--complete-todo", true);
        opg.addOption(opt1);
        validArgs.addOption(opt1);
        assertEquals(groupMap, validArgs.getOptionTypes());
    }

    @Test
    public void getIndividualOption() {
        validArgs.addOption(opt1);
        assertEquals(opt1, validArgs.getIndividualOption("opt1"));
    }

    @Test
    public void getOptionGroup() {
        OptionGroup opg = new RepeatedGroup("--complete-todo", true);
        opg.addOption(opt1);
        validArgs.addOption(opt1);
        assertEquals(options, validArgs.getOptionGroup("--complete-todo"));
    }

    @Test
    public void testEquals() {
        assertTrue(validArgs.equals(validArgs));
        assertTrue(validArgs.equals(empty));
        assertFalse(validArgs.equals(null));
        assertFalse(validArgs.equals("args"));
        validArgs.addOption(opt1);
        assertFalse(empty.equals(validArgs));
        OptionGroup opg = new RepeatedGroup("--complete-todo", true);
        opg.addOption(opt1);
        ValidArgs withGroup = new ValidArgs();
        withGroup.addOption(opt1);
        assertFalse(withGroup.equals(validArgs));
        assertFalse(withGroup.equals(empty));
    }

    @Test
    public void testHashCode() {
        assertEquals(validArgs.hashCode(), empty.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(validArgs.toString(), empty.toString());
    }
}