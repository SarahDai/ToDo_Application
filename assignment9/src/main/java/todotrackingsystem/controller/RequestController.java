package todotrackingsystem.controller;

import java.util.List;
import java.util.Map.Entry;
import todotrackingsystem.model.CSVFile;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.CommandLineParser;
import todotrackingsystem.view.DisplayToDoList;
import todotrackingsystem.view.ValidArgs;
import todotrackingsystem.view.Option;

public class RequestController {
  private CSVFile csvFile;
  private CommandLineParser commandLineParser;

  public RequestController(CommandLineParser commandLineParser) {
    this.commandLineParser = commandLineParser;
  }
  public void processRequests(String[] args) throws InvalidArgumentException {
    ValidArgs commands = this.commandLineParser.parseCommand(args);
    this.csvFile = CSVFile.readCSV(commands.getIndividualOption(Rules.CSV_FILE).getArgValue());

    for(Entry<String, List<Option>> optionGroup: commands.getOptionTypes().entrySet()){
      IRequest request = RequestFactory
          .sendRequest(optionGroup.getKey(), optionGroup.getValue(), this.csvFile);
      if(request != null){
        request.process();
      }
      else{
        throw new InvalidArgumentException("Unknown request");
      }
    }

    this.csvFile.saveCSVFile(DisplayToDoList.display(this.csvFile.getTodoList()));
  }

}
