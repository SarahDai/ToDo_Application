package todotrackingsystem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    if (map.size() == 1) {
      System.out.println(DisplayToDoList.display(this.toDoList.getTodoList()));
    } else {
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
          throw new IllegalArgumentException(String.format("No Such category %s", category));
        } else {
          displayList = this.toDoList.filterCategory(displayList, category);
          requested.add(Rules.SHOW_CATEGORY);
        }
      }
      if (displayList.isEmpty()) {
        System.out.println("There is no such todo to display, please try again!");
        return;
      }

      if (map.containsKey(Rules.SORT_BY_DATE)) {
        displayList = this.toDoList.sortByDate(displayList);
        requested.add(Rules.SORT_BY_DATE);
      }

      if (map.containsKey(Rules.SORT_BY_PRIORITY)) {
        displayList = this.toDoList.sortByPriority(displayList);
        requested.add(Rules.SORT_BY_PRIORITY);
      }

      System.out.println(String.format("Display todos[based on options %s]: ", ListFormatter.format(requested)));
      System.out.print(Rules.getDefaultHeaders());
      System.out.print(DisplayToDoList.display(displayList));
    }
  }
}
