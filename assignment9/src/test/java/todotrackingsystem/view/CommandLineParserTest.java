package todotrackingsystem.view;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.utils.Rules;

public class CommandLineParserTest {
    private Options options;
    private CommandLineParser commandLineParser;
    private String[] validInput;
    private String[] missingRequired;
    private String[] missingRelated;
    private String[] missingArgumentValueAtEnd;
    private String[] wrongArgumentFormat;
    private String[] unknownArgument;
    private String[] missingArgumentValue;
    @Before
    public void setUp() throws Exception {
        options = Rules.getOptions();
        commandLineParser = new CommandLineParser(options);
        validInput = new String[]{"--csv-file", "abc.csv", "--sort-by-priority", "--display"};
        missingRequired = new String[]{"display", "--sort-by-priority"};
        missingRelated = new String[]{"--csv-file", "abc.csv", "--add-todo"};
        missingArgumentValueAtEnd = new String[]{"--csv-file"};
        missingArgumentValue = new String[]{"--csv-file", "--sort-by-priority", "--display"};
        wrongArgumentFormat = new String[]{"--csv-file", "abc.txt"};
        unknownArgument = new String[]{"--csv-file", "abc.csv", "--output-dir"};
    }

    @Test
    public void parseCommand() throws InvalidArgumentException {
        ValidArgs validArgs = commandLineParser.parseCommand(validInput);
        assertTrue(validArgs.hasIndividualOption("--csv-file"));
        assertTrue(validArgs.hasGroup(Rules.DISPLAY_REQUEST));
    }

    @Test(expected = InvalidArgumentException.class)
    public void parseCommandMissingRequired() throws InvalidArgumentException {
        commandLineParser.parseCommand(missingRequired);
    }

    @Test(expected = InvalidArgumentException.class)
    public void parseCommandMissingRelated() throws InvalidArgumentException {
        commandLineParser.parseCommand(missingRelated);
    }

    @Test(expected = InvalidArgumentException.class)
    public void parseCommandMissingArgumentValueAtEnd() throws InvalidArgumentException {
        commandLineParser.parseCommand(missingArgumentValueAtEnd);
    }

    @Test(expected = InvalidArgumentException.class)
    public void parseCommandMissingArgumentValue() throws InvalidArgumentException {
        commandLineParser.parseCommand(missingArgumentValue);
    }

    @Test(expected = InvalidArgumentException.class)
    public void parseCommandWrongArgumentFormat() throws InvalidArgumentException{
        commandLineParser.parseCommand(wrongArgumentFormat);
    }

    @Test(expected = InvalidArgumentException.class)
    public void parseCommandUnknownArg() throws InvalidArgumentException{
        commandLineParser.parseCommand(unknownArgument);
    }

    @Test
    public void testEquals() throws InvalidArgumentException {
        assertTrue(commandLineParser.equals(commandLineParser));
        assertFalse(commandLineParser.equals(null));
        assertFalse(commandLineParser.equals("command"));
        CommandLineParser same = new CommandLineParser(Rules.getOptions());
        CommandLineParser diffOptions = new CommandLineParser(new Options());
        assertTrue(commandLineParser.equals(same));
        same.parseCommand(validInput);
        assertFalse(validInput.equals(same));
        assertFalse(validInput.equals(diffOptions));
        Options opts = new Options();
        Option opt = new Option.Builder("opt", "text").setRequired().build();
        opts.addOption(opt);
        assertFalse(commandLineParser.equals(new CommandLineParser(opts)));

    }

    @Test
    public void testHashCode() {
        assertEquals(commandLineParser.hashCode(), new CommandLineParser(options).hashCode());
    }

    @Test
    public void testToString() throws InvalidArgumentException {
        assertEquals(new CommandLineParser(Rules.getOptions()).toString(), commandLineParser.toString());
    }
}