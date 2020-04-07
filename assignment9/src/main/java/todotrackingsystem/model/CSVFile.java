package todotrackingsystem.model;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import todotrackingsystem.model.ToDoItem.Builder;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.view.DisplayToDoList;
import todotrackingsystem.utils.Rules;

/**
 * The type Csv file.
 */
public class CSVFile {

  private String csvPath;
  private HashMap<Integer, String> headerMap;
  private LinkedHashMap<Integer, ToDoItem> todoList;
  private String header;
  private Integer currentMaxID;
  private Set<String> categoryList;
  private static CSVFile instance;

  private CSVFile(String csvPath) throws InvalidArgumentException {
    this.csvPath = csvPath;
    this.headerMap = new HashMap<>();
    this.todoList = new LinkedHashMap<>();
    this.currentMaxID = 0;
    this.categoryList = new HashSet<>();
    this.processCSVFile();
  }

  /**
   * Read csv csv file.
   *
   * @param csvPath the csv path
   * @return the csv file
   * @throws InvalidArgumentException the invalid argument exception
   */
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
    Integer id = Integer.parseInt(mappingTable.get("id"));
    this.currentMaxID = Math.max(id, currentMaxID);
    String text = mappingTable.get("text");
    ToDoItem.Builder builderOptions = new Builder(id,text);

    boolean completed = Boolean.parseBoolean(mappingTable.get("completed"));
    if (completed) builderOptions.complete();

    LocalDate due = null;
    String date = mappingTable.get("due");
    if (date != null) {
      //Date Format: MM/DD/YYYY
      String[] dates = date.split("/");
      due = LocalDate
          .of(Integer.parseInt(dates[2]), Integer.parseInt(dates[0]),
              Integer.parseInt(dates[1]));
      builderOptions.setDueDate(due);
    }

    if (mappingTable.get("priority") != null) {
      int priority = Integer.parseInt(mappingTable.get("priority"));
      builderOptions.setPriority(priority);
    }

    if (mappingTable.get("category") != null) {
      String category = mappingTable.get("category");
      builderOptions.setCategory(category);
      this.categoryList.add(category);
    }

    return builderOptions.build();
  }

  /**
   * Gets current max id.
   *
   * @return the current max id
   */
  public Integer getCurrentMaxID() {
    return this.currentMaxID;
  }

  /**
   * Gets todo list.
   *
   * @return the todo list
   */
  public List<ToDoItem> getTodoList() {
    return new ArrayList<ToDoItem>(this.todoList.values());
  }

  /**
   * Add to do.
   *
   * @param todo the todo
   */
  public void addToDo(ToDoItem todo) {
    this.todoList.put(++this.currentMaxID, todo);
  }

  /**
   * Contains id boolean.
   *
   * @param id the id
   * @return the boolean
   */
  public boolean containsID(Integer id) {
    return this.todoList.containsKey(id);
  }

  /**
   * Contains category boolean.
   *
   * @param category the category
   * @return the boolean
   */
  public boolean containsCategory(String category) {
    return this.categoryList.contains(category);
  }

  /**
   * Complete to do.
   *
   * @param id the id
   */
  public void completeToDo(Integer id) {
    this.todoList.get(id).completeToDo();
  }

  /**
   * Save csv file.
   *
   * @param output the output
   */
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

  /**
   * Filter incomplete list.
   *
   * @param toDoItemList the to do item list
   * @return the list
   */
  public List<ToDoItem> filterIncomplete(List<ToDoItem> toDoItemList) {
    List<ToDoItem> incompleteList = new ArrayList<>();
    for (ToDoItem todo: toDoItemList) {
      if (!todo.isCompleted()) {
        incompleteList.add(todo);
      }
    }
    return incompleteList;
  }

  /**
   * Filter category list.
   *
   * @param toDoItemList the to do item list
   * @param category     the category
   * @return the list
   */
  public List<ToDoItem> filterCategory(List<ToDoItem> toDoItemList, String category) {
    List<ToDoItem> categoryToDoItemList = new ArrayList<>();
    for (ToDoItem todo: toDoItemList) {
      if (todo.getCategory().equals(category)) {
        categoryToDoItemList.add(todo);
      }
    }
    return categoryToDoItemList;
  }

  /**
   * Sort by date list.
   *
   * @param todoList the todo list
   * @return the list
   */
  public List<ToDoItem> sortByDate(List<ToDoItem> todoList) {
    Collections.sort(todoList, (t1, t2) -> {
      if (t1.getDue() == null) return 1;
      if (t2.getDue() == null) return -1;
      return t1.getDue().compareTo(t2.getDue());
    });
    return todoList;
  }

  /**
   * Sort by priority list.
   *
   * @param toDoItemList the to do item list
   * @return the list
   */
  public List<ToDoItem> sortByPriority(List<ToDoItem> toDoItemList) {
    Collections.sort(toDoItemList, (t1, t2) -> {
      if (t1.getPriority() == null) return 1;
      if (t2.getPriority() == null) return -1;
      return t1.getPriority() - t2.getPriority();
    });
    return toDoItemList;
  }


  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
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
