package todotrackingsystem.controller;

import java.util.List;
import java.util.Map.Entry;
import todotrackingsystem.model.CSVFile;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.view.CommandLineParser;
import todotrackingsystem.view.ValidArgs;
import todotrackingsystem.view.Option;

/**
 * A request Controller class that controls the process of request
 */
public class RequestController {
  private CSVFile csvFile;
  private CommandLineParser commandLineParser;

  public RequestController(CommandLineParser commandLineParser) {
    this.commandLineParser = commandLineParser;
  }

  /**
   * A helper method that process request from command line parser
   * @param args a String array type
   * @throws InvalidArgumentException if unable to process request
   */
  public void processRequests(String[] args) throws InvalidArgumentException {
    ValidArgs commands = this.commandLineParser.parseCommand(args);
    this.csvFile = CSVFile.readCSV(commands.getIndividualOption("--csv-file").getArgValue());

    for(Entry<String, List<Option>> optionGroup: commands.getOptionTypes().entrySet()){
      IRequest request = RequestFactory
          .sendRequest(optionGroup.getKey(), optionGroup.getValue(), this.csvFile);
      if(request != null){
        request.process();
      }
      else{
        throw new IllegalArgumentException("Unknown request");
      }
    }

  }

}
