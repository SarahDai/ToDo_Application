import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    String text = map.get("--todo-text");
    boolean completed = map.containsKey("--completed");
    LocalDate due = null;
    if(map.containsKey("--due")){
      String date = map.get("--due");
      String[] dates = date.split("/");
      due = LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[0]), Integer.parseInt(dates[1]));
    }
    Integer priority = map.get("--priority") == null? null : Integer.parseInt(map.get("--priority"));
    String category = map.get("--category");
    this.csvFile.addToDo(new ToDoItem(this.csvFile.getCurrentMaxID() + 1, text, completed, due, priority, category));
  }
}
