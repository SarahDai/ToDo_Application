package communicationautomation;

import java.util.HashMap;
import java.util.List;

/**
 * The type CommunicationAutoGenerator, served as coordinator for input output generation.
 */
public class CommunicationAutoGenerator {

  private List<ITemplateParser> templateParsers;
  private ICSVParser csvParser;
  private IDeliverHandler deliverHandler;

  /**
   * Instantiates a new Communication auto generator.
   *
   * @param csvParser       the csv parser
   * @param templateParsers the list of template parsers
   * @param deliverHandler  the deliver handler
   */
  public CommunicationAutoGenerator(ICSVParser csvParser,
      List<ITemplateParser> templateParsers,
      IDeliverHandler deliverHandler) {
    this.csvParser = csvParser;
    this.templateParsers = templateParsers;
    this.deliverHandler = deliverHandler;
  }

  /**
   * Start generate the information based on the CSV and Template files,
   * and deliver them to the output path.
   *
   * @throws InvalidArgumentException the invalid argument exception
   */
  public void generate() throws InvalidArgumentException{
    for (ITemplateParser template : templateParsers) {
      template.preprocessTemplate();
    }
    csvParser.preprocessCSV();

    HashMap<String, String> contact;
    int index = 0;
    while ((contact = csvParser.nextRecord()) != null) {
      index++;
      for (ITemplateParser template : templateParsers) {
        String generatedTemplate = template.updateTemplate(contact);
        if (generatedTemplate != null) {
          deliverHandler.deliver(generatedTemplate, index, template.getTemplateType());
        }
      }
    }
    System.out.println(String.format("Successfully generate files in %s folder.", deliverHandler.getOutputDir()));
  }
}
