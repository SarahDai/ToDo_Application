package todotrackingsystem.controller;

import todotrackingsystem.model.ToDoList;
import todotrackingsystem.view.Option;
import todotrackingsystem.model.CSVFile;

import java.util.ArrayList;
import java.util.List;

/**
 * A CompleteRequest class that process the complete request
 */
public class CompleteRequest implements IRequest {
  private List<Option> options;
  private ToDoList toDoList;

  /**
   * Constructor of the class
   * @param options list of options
   * @param toDoList CSVFile object
   */
  public CompleteRequest(List<Option> options, ToDoList toDoList) {
    this.options = options;
    this.toDoList = toDoList;
  }

  /**
   * A helper method that process the complete request
   */
  @Override
  public void process() {
    List<Integer> ids = new ArrayList<>();
    for(Option option: options){
      try{
        Integer id = Integer.parseInt(option.getArgValue());
        if(!this.toDoList.containsID(id)){
          System.out.println(String.format("The todo with ID %s doesn't exist.", id));
          continue;
        }
        this.toDoList.completeToDo(id);
      }catch(NumberFormatException ex){
        System.out.println(String.format("Invalid format for option %s with value %s.", option.getName(), option.getArgValue()));
        continue;
      }
    }
  }
}
