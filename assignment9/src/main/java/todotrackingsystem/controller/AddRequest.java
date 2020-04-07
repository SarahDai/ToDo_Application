package todotrackingsystem.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import todotrackingsystem.model.ToDoItem.Builder;
import todotrackingsystem.model.ToDoList;
import todotrackingsystem.view.Option;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.model.ToDoItem;

/**
 * A AddRequest class that process the add request
 */
public class AddRequest implements IRequest {
  private List<Option> options;
  private ToDoList toDoList;

  /**
   * Constructor of the class
   * @param options List of options
   * @param toDoList CSVFile object
   */
  public AddRequest(List<Option> options, ToDoList toDoList) {
    this.options = options;
    this.toDoList = toDoList;
  }

  /**
   * A helper method that process the add request
   */
  @Override
  public void process() {
    Map<String, String> map = new HashMap<>();
    for(Option option: options){
      map.put(option.getName(), option.getArgValue());
    }

    String text = map.get(Rules.TODO_TEXT);
    ToDoItem.Builder builderOptions = new Builder(this.toDoList.getCurrentMaxID()+1, text);

    boolean completed = map.containsKey(Rules.SET_NEW_TODO_COMPLETED);
    if (completed) builderOptions.complete();

    LocalDate due = null;
    if(map.containsKey(Rules.SET_NEW_TODO_DUE)){
      String date = map.get(Rules.SET_NEW_TODO_DUE);
      String[] dates = date.split("/");
      due = LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[0]), Integer.parseInt(dates[1]));
      builderOptions.setDueDate(due);
    }

    if (map.containsKey(Rules.SET_NEW_TODO_PRIORITY)) {
      int priority = Integer.parseInt(map.get(
          Rules.SET_NEW_TODO_PRIORITY));
      builderOptions.setPriority(priority);
    }

    if (map.containsKey(Rules.SET_NEW_TODO_CATEGORY)) {
      builderOptions.setCategory(map.get(Rules.SET_NEW_TODO_CATEGORY));
    }

    this.toDoList.addNewToDo(builderOptions.build());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AddRequest)) {
      return false;
    }
    AddRequest that = (AddRequest) o;
    return Objects.equals(options, that.options) &&
        Objects.equals(toDoList, that.toDoList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(options, toDoList);
  }

  @Override
  public String toString() {
    return "AddRequest{" +
        "options=" + options +
        ", toDoList=" + toDoList +
        '}';
  }
}
