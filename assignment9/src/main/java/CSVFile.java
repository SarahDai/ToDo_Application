import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import utils.InvalidArgumentException;

public class CSVFile {

  private String csvPath;
  private HashMap<Integer, String> headerMap;
  private TreeMap<Integer, ToDoItem> todoList;
  private String header;
  private Integer currentMaxID;
  private Set<String> categoryList;
  private static CSVFile instance;

  private CSVFile(String csvPath) throws InvalidArgumentException {
    this.csvPath = csvPath;
    this.headerMap = new HashMap<>();
    this.todoList = new TreeMap<>();
    this.currentMaxID = 0;
    this.categoryList = new HashSet<>();
    this.processCSVFile();
  }

  //Singleton Pattern
  public static CSVFile readCSV(String csvPath) throws InvalidArgumentException{
    if (instance == null) {
      instance = new CSVFile(csvPath);
    }
    return instance;
  }

  private void processCSVFile() throws InvalidArgumentException{
    BufferedReader inputFile = null;

    try {
      inputFile = new BufferedReader(new FileReader(this.csvPath));

      String line = inputFile.readLine();
      if(line == null) return;  // Empty CSV file, no need for processing.
      this.processHeader(line);
      while ((line = inputFile.readLine()) != null) {
        this.processToDoItem(line);
      }

      inputFile.close();
    } catch (FileNotFoundException fnfe) {
      throw new InvalidArgumentException("OOPS! File not found!");
    } catch (IOException ioe) {
      throw new InvalidArgumentException("There is somthing wrong!");
    }
  }

  private void processHeader(String header) {
    this.header = header + System.lineSeparator();  // Store the header for writing back.

    String[] headers = header.split("\",\"");
    for (int i = 0; i < headers.length; i++) {
      String columnName = headers[i].replaceAll("\"", "");
      this.headerMap.put(i, columnName);
    }
  }

  private void processToDoItem(String line) {
    String[] items = line.split(("\",\""));
    // Skip the invalid line
    if (items.length != this.headerMap.size()) {
      System.out.println(String.format("The line %s has wrong columns. Please fix it!", line));
      return;
    }

    HashMap<String, String> mappingTable = new HashMap<>();
    for (int i = 0; i < items.length; i++) {
      String item = items[i].replaceAll("\"", "");
      item = item.equals("?") ? null : item;
      mappingTable.put(headerMap.get(i), item);
    }
    ToDoItem todo = this.parseToDoItem(mappingTable);
    this.todoList.put(Integer.parseInt(mappingTable.get("id")), todo);
  }

  private ToDoItem parseToDoItem(HashMap<String, String> mappingTable) {
    this.currentMaxID = Math.max(Integer.parseInt(mappingTable.get("id")), currentMaxID);

    String date = mappingTable.get("due");
    LocalDate localDate = null;
    if (date != null) {
      //Date Format: MM/DD/YYYY
      String[] dates = date.split("/");
      localDate = LocalDate
          .of(Integer.parseInt(dates[2]), Integer.parseInt(dates[0]),
              Integer.parseInt(dates[1]));
    }
    Integer priority = mappingTable.get("priority") == null ? null
        : Integer.parseInt(mappingTable.get("priority"));

    ToDoItem todo = new ToDoItem(Integer.parseInt(mappingTable.get("id")), mappingTable.get("text"),
        Boolean.parseBoolean(mappingTable.get("completed")), localDate, priority,
        mappingTable.get("category"));
    this.categoryList.add(mappingTable.get("category"));
    return todo;
  }

  public Integer getCurrentMaxID() {
    return this.currentMaxID;
  }

  public List<ToDoItem> getTodoList() {
    return new ArrayList<ToDoItem>(this.todoList.values());
  }

  public void addToDo(ToDoItem todo) {
    this.todoList.put(this.currentMaxID++, todo);
  }

  public boolean containsID(Integer id) {
    return this.todoList.containsKey(id);
  }

  public boolean containsCategory(String category) {
    return this.categoryList.contains(category);
  }

  public void completeToDo(Integer id) {
    this.todoList.get(id).completeToDo(true);
  }

  public void saveCSVFile(String output) {
    BufferedWriter outputFile = null;

    try {
      outputFile = new BufferedWriter(new FileWriter(this.csvPath));
      if (this.header == null) {
        this.header = Rules.getDefaultHeaders();
      }
      outputFile.write(header);
      outputFile.write(output);
      outputFile.close();
    } catch (FileNotFoundException fnfe) {
      System.out.println("File not found");
    } catch (IOException ioe) {
      System.out.println("Something went wrong");
    }
  }

  public List<ToDoItem> filterIncomplete(List<ToDoItem> toDoItemList) {
    List<ToDoItem> incompleteList = new ArrayList<>();
    for (ToDoItem todo: toDoItemList) {
      if (!todo.isCompleted()) {
        incompleteList.add(todo);
      }
    }
    return incompleteList;
  }

  public List<ToDoItem> filterCategory(List<ToDoItem> toDoItemList, String category) {
    List<ToDoItem> categoryToDoItemList = new ArrayList<>();
    for (ToDoItem todo: toDoItemList) {
      if (todo.getCategory().equals(category)) {
        categoryToDoItemList.add(todo);
      }
    }
    return categoryToDoItemList;
  }

  public List<ToDoItem> sortByDate(List<ToDoItem> todoList) {
    Collections.sort(todoList, (t1, t2) -> {
      if (t1.getDue() == null) return 1;
      if (t2.getDue() == null) return -1;
      return t1.getDue().compareTo(t2.getDue());
    });
    return todoList;
  }

  public List<ToDoItem> sortByPriority(List<ToDoItem> toDoItemList) {
    Collections.sort(toDoItemList, (t1, t2) -> {
      if (t1.getPriority() == null) return 1;
      if (t2.getPriority() == null) return -1;
      return t1.getPriority() - t2.getPriority();
    });
    return toDoItemList;
  }



  public static void main(String[] args) {
    try {
      CSVFile csv = CSVFile.readCSV("todos.csv");
      List<ToDoItem> list = csv.getTodoList();
      List<ToDoItem> sorted = csv.sortByDate(list);
      System.out.println(DisplayToDoList.display(sorted));
    } catch (InvalidArgumentException ex) {
      System.out.println("Something went wrong!");
    }
//    for (int i: csv.todoMap.keySet()) {
//      System.out.println(i + ",");
//    }
  }
}
