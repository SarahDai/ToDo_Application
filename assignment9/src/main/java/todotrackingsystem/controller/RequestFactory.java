package todotrackingsystem.controller;

import java.util.List;
import todotrackingsystem.controller.AddRequest;
import todotrackingsystem.controller.CompleteRequest;
import todotrackingsystem.controller.DisplayRequest;
import todotrackingsystem.controller.IRequest;
import todotrackingsystem.model.CSVFile;
import todotrackingsystem.view.Option;
import todotrackingsystem.utils.Rules;

/**
 * A RequestFactory class that use factory pattern to hold common rules of request
 */
public class RequestFactory {

  /**
   * a static sendRequest method that specifies types of rules
   * @param name name of teh rule
   * @param options list of options
   * @param csvFile a csv file
   * @return specified request
   */
  public static IRequest sendRequest(String name, List<Option> options, CSVFile csvFile){
    switch (name){
      case Rules.ADD_REQUEST:
        return new AddRequest(options, csvFile);
      case Rules.COMPLETE_REQUEST:
        return new CompleteRequest(options, csvFile);
      case Rules.DISPLAY_REQUEST:
        return new DisplayRequest(options, csvFile);
      default:
        return null;
    }
  }

}
