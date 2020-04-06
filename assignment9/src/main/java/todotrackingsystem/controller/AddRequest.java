package todotrackingsystem.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import todotrackingsystem.model.ToDoItem.Builder;
import todotrackingsystem.view.Option;
import todotrackingsystem.model.CSVFile;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.model.ToDoItem;

public class AddRequest implements IRequest {
  private List<Option> options;
  private CSVFile csvFile;

  public AddRequest(List<Option> options, CSVFile csvFile) {
    this.options = options;
    this.csvFile = csvFile;
  }

  @Override
  public void process() {
    Map<String, String> map = new HashMap<>();
    for(Option option: options){
      map.put(option.getName(), option.getArgValue());
    }

    String text = map.get(Rules.TODO_TEXT);
    ToDoItem.Builder builderOptions = new Builder(this.csvFile.getCurrentMaxID()+1, text);

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

    this.csvFile.addToDo(builderOptions.build());
  }
}
