package todotrackingsystem.controller;

import java.util.List;
import todotrackingsystem.controller.AddRequest;
import todotrackingsystem.controller.CompleteRequest;
import todotrackingsystem.controller.DisplayRequest;
import todotrackingsystem.controller.IRequest;
import todotrackingsystem.model.CSVFile;
import todotrackingsystem.view.Option;
import todotrackingsystem.utils.Rules;

public class RequestFactory {
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
