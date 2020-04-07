package todotrackingsystem.controller;

import java.util.List;
import todotrackingsystem.model.ToDoList;
import todotrackingsystem.view.Option;
import todotrackingsystem.utils.Rules;

/**
 * A RequestFactory class that use factory pattern to hold common rules of request
 */
public class RequestFactory {
  public static IRequest sendRequest(String name, List<Option> options, ToDoList toDoList){
    switch (name){
      case Rules.ADD_REQUEST:
        return new AddRequest(options, toDoList);
      case Rules.COMPLETE_REQUEST:
        return new CompleteRequest(options, toDoList);
      case Rules.DISPLAY_REQUEST:
        return new DisplayRequest(options, toDoList);
      default:
        return null;
    }
  }

}
