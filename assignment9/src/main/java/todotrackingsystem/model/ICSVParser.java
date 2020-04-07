package todotrackingsystem.model;

import todotrackingsystem.utils.InvalidArgumentException;

/**
 * The interface ICSVParser, provide general functionality for csv parsers.
 */
public interface ICSVParser {

  /**
   * Read csv in and parse the lines into ToDoList.
   *
   * @param csvPath the csv path
   * @return the to do list
   * @throws InvalidArgumentException the invalid argument exception
   */
  ToDoList readCSV(String csvPath) throws InvalidArgumentException;

  /**
   * Save csv file to the specified csv file path.
   *
   * @param csvPath the csv path
   * @param output  the output
   */
  void saveCSVFile(String csvPath, String output);
}
