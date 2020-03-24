package communicationautomation;

import java.util.HashMap;

/**
 *
 * The interface ICSVParser
 *
 */
public interface ICSVParser {

  /**
   * pre process CSV file
   * @throws InvalidArgumentException if file not found or anything went wrong
   */
  void preprocessCSV() throws InvalidArgumentException;

  /**
   * process the content of the CSV file
   * @return a record that stores each person's information with corresponding header
   */
  HashMap<String, String> nextRecord();

  /**
   * close the CSV file
   */
  void closeCSV();

}