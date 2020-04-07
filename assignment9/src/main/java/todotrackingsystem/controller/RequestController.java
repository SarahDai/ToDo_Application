package todotrackingsystem.controller;

import java.util.List;
import java.util.Map.Entry;
import todotrackingsystem.model.CSVParser;
import todotrackingsystem.model.ToDoList;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.CommandLineParser;
import todotrackingsystem.view.DisplayToDoList;
import todotrackingsystem.view.ValidArgs;
import todotrackingsystem.view.Option;

/**
 * A request Controller class that controls the process of request
 */
public class RequestController {
  private CSVParser csvParser;
  private CommandLineParser commandLineParser;

  /**
   * Constructor of the class
   * @param commandLineParser a CommandLineParser object
   */
  public RequestController(CommandLineParser commandLineParser, CSVParser csvParser) {
    this.commandLineParser = commandLineParser;
    this.csvParser = csvParser;
  }

  /**
   * A helper method that process request from command line parser
   * @param args a String array type
   * @throws InvalidArgumentException if unable to process request
   */
  public void processRequests(String[] args) throws InvalidArgumentException {
    ValidArgs commands = this.commandLineParser.parseCommand(args);
    String csvPath = commands.getIndividualOption(Rules.CSV_FILE).getArgValue();
    ToDoList toDoList = csvParser.readCSV(csvPath);

    for(Entry<String, List<Option>> optionGroup: commands.getOptionTypes().entrySet()){
      IRequest request = RequestFactory
          .sendRequest(optionGroup.getKey(), optionGroup.getValue(), toDoList);
      if(request != null){
        request.process();
      }
      else{
        throw new InvalidArgumentException("Unknown request");
      }
    }

    this.csvParser.saveCSVFile(csvPath, DisplayToDoList.display(toDoList.getTodoList()));
  }
}
