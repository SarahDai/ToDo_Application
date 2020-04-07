package todotrackingsystem.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import todotrackingsystem.model.ToDoItem.Builder;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.utils.Rules;

/**
 * The type CSV Parser, parse the csv file into todos list.
 */
public class CSVParser implements ICSVParser{
  private String csvPath;
  private HashMap<Integer, String> headerMap;
  private String header;
  private ToDoList toDoList;
  private static CSVParser instance;

  /**
   * Instantiates a CSV parser.
   */
  private CSVParser() {
    this.headerMap = new HashMap<>();
    this.header = null;
    this.toDoList = new ToDoList();
  }

  /**
   * Gets a default CSV parser, there could only be one CSV Parser for the class.
   *
   * @return the csv parser object
   */
  // Singleton Pattern
  public static CSVParser getParser() {
    if (instance == null) {
      instance = new CSVParser();
    }
    return instance;
  }

  /**
   * Processes the csv file, read it in and parse the todos to store in the todoList.
   * If the csv file is empty, directly return.
   *
   * @param csvPath the csv path
   * @return the ToDoList object
   * @throws InvalidArgumentException if there is something wrong in the processing
   */
  @Override
  public ToDoList readCSV(String csvPath) throws InvalidArgumentException {
    this.csvPath = csvPath;
    this.toDoList = new ToDoList();

    BufferedReader inputFile = null;
    try {
      inputFile = new BufferedReader(new FileReader(this.csvPath));

      String line = inputFile.readLine();
      if (line == null) {
        inputFile.close();
        return this.toDoList;
      }  // Empty CSV file, no need for processing.
      this.processHeader(line);
      while ((line = inputFile.readLine()) != null) {
        this.processToDoItem(line);
      }
      inputFile.close();
      return this.toDoList;
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
    this.toDoList.addExistingToDo(Integer.parseInt(mappingTable.get("id")), todo);
  }

  /**
   * Parses the <Column-header, Column-content> key-value pair into corresponding ToDoItem.
   *
   * @param mappingTable the <Column-header, Column-content> key-value pair of each line in csv file
   * @return a corresponding ToDoItem of the line in csv file
   */
  private ToDoItem parseToDoItem(HashMap<String, String> mappingTable) {
    Integer id = Integer.parseInt(mappingTable.get("id"));

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
    }

    return builderOptions.build();
  }

  /**
   * Write the output back into the csv file.
   *
   * @param output the output content to be stored in the csv file
   */
  @Override
  public void saveCSVFile(String csvPath, String output) {
    this.csvPath = csvPath;
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
}
