package todotrackingsystem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import todotrackingsystem.utils.ListFormatter;
import todotrackingsystem.view.Option;
import todotrackingsystem.model.CSVFile;
import todotrackingsystem.model.ToDoItem;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.DisplayToDoList;

/**
 * A DisplayRequest class that processes the display request
 */
public class DisplayRequest implements IRequest {
  private List<Option> options;
  private CSVFile csvFile;

  /**
   * Constructor of the class
   * @param options list of options
   * @param csvFile a CSVFile object
   */
  public DisplayRequest(List<Option> options, CSVFile csvFile) {
    this.options = options;
    this.csvFile = csvFile;
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
      System.out.println(DisplayToDoList.display(this.csvFile.getTodoList()));
    } else {
      List<ToDoItem> displayList = this.csvFile.getTodoList();
      List<String> requested = new ArrayList<String>() {{
        add(Rules.DISPLAY_REQUEST);
      }};
      if (map.containsKey(Rules.SHOW_INCOMPLETE)) {
        displayList = this.csvFile.filterIncomplete(displayList);
        requested.add(Rules.SHOW_INCOMPLETE);
      }
      if (map.containsKey(Rules.SHOW_CATEGORY)) {
        String category = map.get(Rules.SHOW_CATEGORY);
        if (!this.csvFile.containsCategory(category)) {
          throw new IllegalArgumentException(String.format("No Such category %s", category));
        } else {
          displayList = this.csvFile.filterCategory(displayList, category);
          requested.add(Rules.SHOW_CATEGORY);
        }
      }
      if (displayList.isEmpty()) {
        System.out.println("There is no such todo to display, please try again!");
        return;
      }

      if (map.containsKey(Rules.SORT_BY_DATE)) {
        displayList = this.csvFile.sortByDate(displayList);
        requested.add(Rules.SORT_BY_DATE);
      }

      if (map.containsKey(Rules.SORT_BY_PRIORITY)) {
        displayList = this.csvFile.sortByPriority(displayList);
        requested.add(Rules.SORT_BY_PRIORITY);
      }

      System.out.println(String.format("Display todos[based on options %s]: ", ListFormatter.format(requested)));
      System.out.print(Rules.getDefaultHeaders());
      System.out.print(DisplayToDoList.display(displayList));
    }
  }
}
