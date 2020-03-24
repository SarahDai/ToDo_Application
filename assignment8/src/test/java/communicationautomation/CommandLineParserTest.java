package communicationautomation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CommandLineParserTest {
  private Options options;
  private CommandLineParser commandLineParser;
  private String[] validInput;
  private String[] missingRequired;
  private String[] missingRelated;
  private String[] missingArgumentValue;
  private String[] wrongArgumentFormat;
  private String[] unknownArgument;

  @Before
  public void setUp() throws Exception {
    options = Rules.getOptions();
    commandLineParser = new CommandLineParser(options);
    validInput = new String[]{"--csv-file", "abc.csv", "--output-dir", "user/dir","--email-template", "email-template.txt","--email"};
    missingRequired = new String[]{"--csv-file", "abc.csv"};
    missingRelated = new String[]{"--csv-file", "abc.csv", "--email-template", "email-template.txt", "--output-dir", "user/dir"};
    missingArgumentValue = new String[]{"--csv-file", "--email-template", "email-template.txt", "--output-dir", "user/dir"};
    wrongArgumentFormat = new String[]{"--csv-file", "abc.txt", "--output-dir", "user/dir"};
    unknownArgument = new String[]{"--csv-file", "abc.csv", "--output-dir", "user/dir", "--commit"};
  }

  @Test
  public void parseCommand() throws InvalidArgumentException {
    ValidArgs validArgs = commandLineParser.parseCommand(validInput);

    assertTrue(validArgs.hasOption("--csv-file"));
    assertTrue(validArgs.hasOption("--output-dir"));
    assertTrue(validArgs.hasOption("--email"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void parseCommandMissingRequired() throws InvalidArgumentException{
    commandLineParser.parseCommand(missingRequired);
  }

  @Test(expected = InvalidArgumentException.class)
  public void parseCommandMissingRelated() throws InvalidArgumentException{
    commandLineParser.parseCommand(missingRelated);
  }

  @Test(expected = InvalidArgumentException.class)
  public void parseCommandMissingArgumentValue() throws InvalidArgumentException{
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
  public void testEquals() {
    assertTrue(commandLineParser.equals(commandLineParser));
    assertFalse(commandLineParser.equals(null));
    assertFalse(commandLineParser.equals("command"));
    assertTrue(commandLineParser.equals(new CommandLineParser(Rules.getOptions())));
  }

  @Test
  public void testHashCode() {
    assertEquals(commandLineParser.hashCode(), new CommandLineParser(options).hashCode());
  }

  @Test
  public void testToString() {
    assertEquals(new CommandLineParser(Rules.getOptions()).toString(), commandLineParser.toString());
  }
}