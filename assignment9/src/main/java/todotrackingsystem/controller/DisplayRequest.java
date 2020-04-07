package todotrackingsystem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import todotrackingsystem.model.ToDoList;
import todotrackingsystem.utils.ListFormatter;
import todotrackingsystem.view.Option;
import todotrackingsystem.model.ToDoItem;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.DisplayToDoList;

/**
 * A DisplayRequest class that processes the display request
 */
public class DisplayRequest implements IRequest {
  private List<Option> options;
  private ToDoList toDoList;

  /**
   * Constructor of the class
   * @param options list of options
   * @param toDoList a CSVFile object
   */
  public DisplayRequest(List<Option> options, ToDoList toDoList) {
    this.options = options;
    this.toDoList = toDoList;
  }

  /**
   * A helper method that process the display request
   */
  @Override
  public void process() {
    Map<String, String> map = new HashMap<>();
    for(Option option: options){
      map.put(option.getName(), option.getArgValue());
    }

    List<ToDoItem> displayList = this.toDoList.getTodoList();
    List<String> requested = new ArrayList<String>() {{
      add(Rules.DISPLAY_REQUEST);
    }};
    if (map.containsKey(Rules.SHOW_INCOMPLETE)) {
      displayList = this.toDoList.filterIncomplete(displayList);
      requested.add(Rules.SHOW_INCOMPLETE);
    }
    if (map.containsKey(Rules.SHOW_CATEGORY)) {
      String category = map.get(Rules.SHOW_CATEGORY);
      if (!this.toDoList.containsCategory(category)) {
        System.out.println(String.format("This is no such category: %s in the list.", category));
        return;
      } else {
        displayList = this.toDoList.filterCategory(displayList, category);
        requested.add(Rules.SHOW_CATEGORY);
      }
    }

    if (map.containsKey(Rules.SORT_BY_DATE)) {
      displayList = this.toDoList.sortByDate(displayList);
      requested.add(Rules.SORT_BY_DATE);
    }

    if (map.containsKey(Rules.SORT_BY_PRIORITY)) {
      displayList = this.toDoList.sortByPriority(displayList);
      requested.add(Rules.SORT_BY_PRIORITY);
    }

    if (displayList.isEmpty()) {
      System.out.println("There is no such todo to display, please try again!");
      return;
    }

    System.out.println(String.format("Display todos[based on options %s]: ", ListFormatter.format(requested)));
    System.out.print(Rules.getDefaultHeaders());
    System.out.print(DisplayToDoList.display(displayList));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DisplayRequest that = (DisplayRequest) o;
    return Objects.equals(options, that.options) &&
        Objects.equals(toDoList, that.toDoList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(options, toDoList);
  }

  @Override
  public String toString() {
    return "DisplayRequest{" +
        "options=" + options +
        ", toDoList=" + toDoList +
        '}';
  }
}
