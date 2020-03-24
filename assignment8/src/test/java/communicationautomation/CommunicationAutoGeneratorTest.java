package communicationautomation;

import static org.junit.Assert.*;

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
    List<ITemplateParser> templateParsers = new ArrayList<>();
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
//    assertTrue(generator.equals(new CommunicationAutoGenerator(csvParser,templateParsers,deliverHandler)));
  }

  @Test
  public void testHashCode() {
//    assertEquals(generator.hashCode(), new CommunicationAutoGenerator(csvParser,templateParsers,deliverHandler).hashCode());
  }

  @Test
  public void testToString() {
//    assertEquals("CommunicationAutoGenerator{templateParsers=[communicationautomation.TemplateParser@676d73c5], csvParser=communicationautomation.CSVParser@7498b4af, deliverHandler=WriteToFileHandler{outputPath=' /Users/monkey/Documents/cs5004/Team_repo_Xinyu_Dai_Lingya-_Hu_Yafei_Wang/assignment8/test'}}\n",generator);
  }
}