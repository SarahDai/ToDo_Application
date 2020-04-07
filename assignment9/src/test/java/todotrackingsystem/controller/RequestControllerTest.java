package todotrackingsystem.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.model.CSVParser;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.CommandLineParser;
import todotrackingsystem.view.Options;

public class RequestControllerTest {
  private RequestController controller;
  private CSVParser csvParser;
  private CommandLineParser commandLineParser;

  @Before
  public void setUp() throws Exception {
    csvParser = CSVParser.getParser();
    commandLineParser = new CommandLineParser(Rules.getOptions());
    controller = new RequestController(commandLineParser, csvParser);
  }

  @Test
  public void processRequests() throws InvalidArgumentException {
    String[] arguments = new String[]{"--csv-file", "empty.csv", "--display"};
    controller.processRequests(arguments);
  }

  @Test
  public void testEquals() throws InvalidArgumentException {
    assertTrue(controller.equals(controller));
    assertFalse(controller.equals(null));
    assertFalse(controller.equals(23));

    assertFalse(controller.equals(new RequestController(new CommandLineParser(new Options()), csvParser)));
    assertTrue(controller.equals(new RequestController(new CommandLineParser(Rules.getOptions()),csvParser)));
  }

  @Test
  public void testHashCode() throws InvalidArgumentException {
    RequestController copy = new RequestController(new CommandLineParser(Rules.getOptions()), csvParser);

    assertEquals(controller.hashCode(), controller.hashCode());
    assertEquals(controller.hashCode(), copy.hashCode());
  }

  @Test
  public void testToString() throws InvalidArgumentException{
    RequestController copy = new RequestController(new CommandLineParser(Rules.getOptions()), csvParser);

    assertEquals(copy.toString(), controller.toString());
  }
}