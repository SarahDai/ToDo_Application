package todotrackingsystem.view;

import todotrackingsystem.model.CSVParser;
import todotrackingsystem.model.ICSVParser;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.controller.RequestController;

/**
 * The type Main.
 */
public class Main {

  /**
   * The entry point of Todo tracking system application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    try {
      CommandLineParser commandLineParser = new CommandLineParser(Rules.getOptions());
      ICSVParser csvParser = CSVParser.getParser();
      RequestController request = new RequestController(commandLineParser, csvParser);
      request.processRequests(args);
    } catch (Exception ex) {
      System.out.println("Something went wrong: " + ex.getMessage());
    }
  }

}
