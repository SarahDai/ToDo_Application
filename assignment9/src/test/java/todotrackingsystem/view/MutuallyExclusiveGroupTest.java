package todotrackingsystem.view;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MutuallyExclusiveGroupTest {
    private String[] conflictingExisted;
    @Before
    public void setUp() throws Exception {
        conflictingExisted = new String[]{"--display", "--sort-by-date", "--sort-by-priority", "--csv-file", "abc.csv"};
    }

    @Test
    public void addConflictedOption() {
    }

    @Test
    public void checkValid() {
    }
}