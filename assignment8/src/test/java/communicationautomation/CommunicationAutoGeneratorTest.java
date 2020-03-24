package communicationautomation;

import static org.junit.Assert.*;

import com.sun.tools.jdeprscan.CSV;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CommunicationAutoGeneratorTest {
  private List<ITemplateParser> templateParsers;
  private ICSVParser csvParser;
  private IDeliverHandler deliverHandler;
  private CommunicationAutoGenerator generator;

  @Before
  public void setUp() throws Exception {
    csvParser = new CSVParser("nonprofit-supporters.csv");
    deliverHandler = new WriteToFileHandler("test");  //local directory
    templateParsers = new ArrayList<>();
    ITemplateParser templateParser = TemplateParser.createTemplate("email-template", "email-template.txt");
    templateParsers.add(templateParser);
    generator = new CommunicationAutoGenerator(csvParser,templateParsers,deliverHandler);
  }

  @Test
  public void generate() throws InvalidArgumentException {
    generator.generate();
  }

  @Test
  public void testEquals() {
    assertTrue(generator.equals(generator));
    assertFalse(generator.equals(null));
    assertFalse(generator.equals(csvParser));
    assertTrue(generator.equals(new CommunicationAutoGenerator(csvParser,templateParsers,deliverHandler)));

    CSVParser anotherCSV = new CSVParser("empty.csv");
    assertFalse(generator.equals(new CommunicationAutoGenerator(anotherCSV,templateParsers,deliverHandler)));
    List<ITemplateParser> anotherTemplateParsers = new ArrayList<>();
    anotherTemplateParsers.add(TemplateParser.createTemplate("letter","letter-template.txt"));
    assertFalse(generator.equals(new CommunicationAutoGenerator(csvParser,anotherTemplateParsers,deliverHandler)));
    IDeliverHandler anotherWrite = new WriteToFileHandler("test2");
    assertFalse(generator.equals(new CommunicationAutoGenerator(csvParser,templateParsers,anotherWrite)));
  }

  @Test
  public void testHashCode() {
    assertEquals(generator.hashCode(), new CommunicationAutoGenerator(csvParser,templateParsers,deliverHandler).hashCode());
  }

  @Test
  public void testToString() {
//    assertEquals("",generator);
  }
}