package todotrackingsystem.view;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.utils.Rules;

public class OptionsTest {

    private Option opt1;
    private Option opt2;
    private RepeatedGroup rg;
    private MutuallyDependentGroup mdg;
    private Options options;
    private List<Option> opts;

    @Before
    public void setUp() throws Exception {
        opt1 = new Option.Builder("opt1", "text").build();
        opt2 = new Option.Builder("opt2", "text").setRequired().build();
        rg = new RepeatedGroup("name", false);
        mdg = new MutuallyDependentGroup("name", true);
        opts = new ArrayList<Option>() {{
            add(opt1);
            add(opt2);
        }};
        mdg.addOption(opt1);
        mdg.addOption(opt2);
        mdg.addConnectedOptions(opts);
        options = new Options();
    }

    @Test
    public void getOptionGroups() {
        assertEquals(new HashSet<OptionGroup>(), options.getOptionGroups());
    }

    @Test
    public void addOptionGroup() {
        options.addOptionGroup(rg);
        options.addOptionGroup(mdg);
    }

    @Test
    public void testEquals() throws InvalidArgumentException {
        assertTrue(options.equals(options));
        assertFalse(options.equals(null));
        assertFalse(options.equals("options"));
        assertFalse(options.equals(Rules.getOptions()));
        Options diffUsage = new Options();
        diffUsage.setUsage("new usage.");
        Options diffGroup = new Options();
        diffGroup.addOption(opt1);
        diffGroup.addOption(opt1);
        Options diffAllOptions = new Options();
        diffAllOptions.addOptionGroup(rg);

        assertFalse(options.equals(diffUsage));
        assertFalse(options.equals(diffAllOptions));
        options.addOptionGroup(mdg);
        assertFalse(options.equals(diffGroup));
    }
}