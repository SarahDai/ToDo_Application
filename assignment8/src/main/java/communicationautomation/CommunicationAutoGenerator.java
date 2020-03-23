package communicationautomation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CommunicationAutoGenerator {

  private List<ITemplateParser> templateParsers;
  private ICSVParser csvParser;
  private IDeliverHandler deliverHandler;

  public CommunicationAutoGenerator(ICSVParser csvParser,
      List<ITemplateParser> templateParsers,
      IDeliverHandler deliverHandler) {
    this.csvParser = csvParser;
    this.templateParsers = templateParsers;
    this.deliverHandler = deliverHandler;
  }

  public void generate() throws InvalidArgumentException{
    for (ITemplateParser template : templateParsers) {
      template.preprocessTemplate();
    }

    csvParser.preprocessCSV();

    HashMap<String, String> contact = new HashMap<>();
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
