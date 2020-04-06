import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayRequest implements IRequest {
  private List<Option> options;
  private CSVFile csvFile;

  public DisplayRequest(List<Option> options, CSVFile csvFile) {
    this.options = options;
    this.csvFile = csvFile;
  }

  @Override
  public void process() {
    Map<String, String> map = new HashMap<>();
    for(Option option: options){
      map.put(option.getName(), option.getArgValue());
    }
    if(map.size() == 1){
      System.out.println(DisplayToDoList.display(this.csvFile.getTodoList()));
    }else{
      List<ToDoItem> displayList = this.csvFile.getTodoList();
      if(map.containsKey("--show-incomplete")){
        displayList = this.csvFile.filterIncomplete(displayList);
      }
      if(map.containsKey("--show-category")){
        String category = map.get("--show-category");
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
      if(map.containsKey("--sort-by-date")){
        displayList = this.csvFile.sortByDate(displayList);
      }
      if(map.containsKey("--sort-by-priority")){
        displayList = this.csvFile.sortByPriority(displayList);
      }
      System.out.println(DisplayToDoList.display(displayList));
    }
  }
}
