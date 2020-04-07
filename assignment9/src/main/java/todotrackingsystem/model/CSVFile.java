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
import java.util.Objects;
import java.util.Set;
import todotrackingsystem.model.ToDoItem.Builder;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.view.DisplayToDoList;
import todotrackingsystem.utils.Rules;

/**
 * The type CSVFile, read info from the csv file and manipulate on the file.
 */
public class CSVFile {

  private String csvPath;
  private HashMap<Integer, String> headerMap;
  private LinkedHashMap<Integer, ToDoItem> todoList;
  private String header;
  private Integer currentMaxID;
  private Set<String> categoryList;
  private static CSVFile instance;

  /**
   * Instantiates an CSVFile object.
   *
   * @param csvPath the csv file path
   * @throws InvalidArgumentException if there is something wrong processing the CSV file
   */
  private CSVFile(String csvPath) throws InvalidArgumentException {
    this.csvPath = csvPath;
    this.headerMap = new HashMap<>();
    this.todoList = new LinkedHashMap<>();
    this.currentMaxID = 0;
    this.categoryList = new HashSet<>();
    this.processCSVFile();
  }

  /**
   * Instantiates the csv file, the class could only have one instance.
   *
   * @param csvPath the csv path
   * @return the only csv file instance of the CSVFile
   * @throws InvalidArgumentException the invalid argument exception
   */
  // Singleton Pattern
  public static CSVFile readCSV(String csvPath) throws InvalidArgumentException{
    if (instance == null) {
      instance = new CSVFile(csvPath);
    }
    return instance;
  }

  /**
   * Processes the CSVFile, read it in and parse the todos to store in the todoList.
   * If the CSVFile is empty, directly return.
   *
   * @throws InvalidArgumentException if there is something wrong in the processing
   */
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

  /**
   * Processes the header of the csv file, in order to match column header with column content easier.
   *
   * @param header the header line of a csv file
   */
  private void processHeader(String header) {
    this.header = header + System.lineSeparator();  // Store the header for writing back.

    String[] headers = header.split("\",\"");
    for (int i = 0; i < headers.length; i++) {
      String columnName = headers[i].replaceAll("\"", "");
      this.headerMap.put(i, columnName);
    }
  }

  /**
   * Processes each line in the csv file, parse them into a ToDoItem instance and store in the todoList.
   * If the line is invalid, inform the user, and skip the line.
   *
   * @param line one single line in the csv file
   */
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

  /**
   * Parses the <Column-header, Column-content> key-value pair into corresponding ToDoItem.
   *
   * @param mappingTable the <Column-header, Column-content> key-value pair of each line in csv file
   * @return a corresponding ToDoItem of the line in csv file
   */
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
   * Gets current max id of the csv file.
   *
   * @return the current max id
   */
  public Integer getCurrentMaxID() {
    return this.currentMaxID;
  }

  /**
   * Gets whole todolist, representing in List<ToDoItem>.
   *
   * @return the todo list storing in the CSVFile
   */
  public List<ToDoItem> getTodoList() {
    return new ArrayList<ToDoItem>(this.todoList.values());
  }

  /**
   * Add a new ToDoItem in the csv file.
   *
   * @param todo the todo
   */
  public void addToDo(ToDoItem todo) {
    this.todoList.put(++this.currentMaxID, todo);
  }

  /**
   * Checks whether the todoList contains the input id.
   *
   * @param id the id to search for
   * @return true if the id is in the todoList, false otherwise
   */
  public boolean containsID(Integer id) {
    return this.todoList.containsKey(id);
  }

  /**
   * Checks whether the todoList contains the input category.
   *
   * @param category the category to search for
   * @return true if the category is in the todoList, false otherwise
   */
  public boolean containsCategory(String category) {
    return this.categoryList.contains(category);
  }

  /**
   * Complete to ToDoItem with the id. Inform the user if the specified ToDoItem is already completed.
   *
   * @param id the id associated with the ToDoItem to complete
   */
  public void completeToDo(Integer id) {
    this.todoList.get(id).completeToDo();
  }

  /**
   * Writes the todoList back to the csv file.
   *
   * @param output the output to be written back
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
   * Filter the todoList by incomplete todos.
   *
   * @param toDoItemList the toDoList to filter
   * @return the list containing only incomplete todos
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
   * Filter the todoList by the specified category.
   *
   * @param toDoItemList the toDoList to filter
   * @param category     the category to search for
   * @return the list containing only the specified category todos
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
   * Sort the todoList by due date.
   * Sort in ascending order. For ToDoItems with no due date, list at the end of the list.
   *
   * @param todoList the todoList to sort
   * @return the list sorted by the due date
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
   * Sort the todoList by priority. With 1 being the highest priority, 3 being the lowest priority.
   * Sort in ascending order. For ToDoItem with no priority, treated as lowest priority
   * and list at the end of the list.
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CSVFile csvFile = (CSVFile) o;
    return Objects.equals(csvPath, csvFile.csvPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(csvPath);
  }

  @Override
  public String toString() {
    return "CSVFile{" +
        "csvPath='" + csvPath + '\'' +
        '}';
  }
}
