package communicationautomation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    CommandLineParser commandLineParser = new CommandLineParser(Rules.getOptions());

    try {
      ValidArgs commandLine = commandLineParser.parseCommand(args);

      CSVParser csvParser = new CSVParser(commandLine.getOption("--csv-file").getArgValue());
      List<ITemplateParser> templateParsers = new LinkedList<>();
      List<Option> templateOptions = commandLine.getGroupOptions("template");

      for (Option option : templateOptions) {
        String templateType = option.getName().substring(2);
        String templateFileName = option.getArgValue();
        ITemplateParser templateParser = TemplateParse.createTemplate(templateType, templateFileName);
        templateParsers.add(templateParser);
      }

      IDeliverHandler deliverHandler = new WriteToFileHandler(commandLine.getOption("--output-dir").getArgValue());

      CommunicationAutoGenerator generator = new CommunicationAutoGenerator(csvParser,
          templateParsers, deliverHandler);
      generator.generate();
    } catch (Exception ex) {
      System.out.println("***OOPS! Something went wrong: " + ex.getMessage());
    }
  }
}