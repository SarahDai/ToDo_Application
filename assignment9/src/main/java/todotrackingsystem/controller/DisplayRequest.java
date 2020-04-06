package todotrackingsystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import todotrackingsystem.view.Option;
import todotrackingsystem.model.CSVFile;
import todotrackingsystem.model.ToDoItem;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.DisplayToDoList;

/**
 * A DisplayRequest class that processes the display request
 */
public class DisplayRequest implements IRequest {
  private static final int ONE = 1;
  private List<Option> options;
  private CSVFile csvFile;

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
    if(map.size() == ONE){
      System.out.println(DisplayToDoList.display(this.csvFile.getTodoList()));
    }else{
      List<ToDoItem> displayList = this.csvFile.getTodoList();
      if(map.containsKey(Rules.SHOW_INCOMPLETE)){
        displayList = this.csvFile.filterIncomplete(displayList);
      }
      if(map.containsKey(Rules.SHOW_CATEGORY)){
        String category = map.get(Rules.SHOW_CATEGORY);
        if(!this.csvFile.containsCategory(category)){
          throw new IllegalArgumentException(String.format("No Such category %s", category));
        }else{
          displayList = this.csvFile.filterCategory(displayList, category);
        }
      }
      if(displayList.isEmpty()){
        System.out.println("There is no such todo to display, please try again!");
        return;
      }
      if(map.containsKey(Rules.SORT_BY_DATE)){
        displayList = this.csvFile.sortByDate(displayList);
      }
      if(map.containsKey(Rules.SORT_BY_PRIORITY)){
        displayList = this.csvFile.sortByPriority(displayList);
      }
      System.out.println(DisplayToDoList.display(displayList));
    }
  }
}
