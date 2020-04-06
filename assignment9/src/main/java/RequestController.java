import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import utils.InvalidArgumentException;

public class RequestController {
  private CSVFile csvFile;
  private CommandLineParser commandLineParser;

  public RequestController(CommandLineParser commandLineParser) {
    this.commandLineParser = commandLineParser;
  }
  public void processRequests(String[] args) throws InvalidArgumentException {
    ValidArgs commands = this.commandLineParser.parseCommand(args);
    this.csvFile = CSVFile.readCSV(commands.getIndividualOption("--csv-file").getArgValue());

    for(Entry<String, List<Option>> optionGroup: commands.getOptionTypes().entrySet()){
      IRequest request = RequestFactory.sendRequest(optionGroup.getKey(), optionGroup.getValue(), this.csvFile);
      if(request != null){
        request.process();
      }
      else{
        throw new IllegalArgumentException("Unknown request");
      }
    }

  }

}
