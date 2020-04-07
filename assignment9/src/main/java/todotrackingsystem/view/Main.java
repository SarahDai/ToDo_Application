package todotrackingsystem.view;

import todotrackingsystem.model.CSVParser;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.controller.RequestController;

public class Main {
  public static void main(String[] args) {
    try {
      CommandLineParser commandLineParser = new CommandLineParser(Rules.getOptions());
      CSVParser csvParser = CSVParser.getParser();
      RequestController request = new RequestController(commandLineParser, csvParser);
      request.processRequests(args);
    } catch (InvalidArgumentException ex) {
      System.out.println("Something went wrong: " + ex.getMessage());
    }
  }

}
