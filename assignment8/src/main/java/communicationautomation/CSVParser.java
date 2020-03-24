package communicationautomation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * A CSV Parser class that parse the input CSV file
 */
public class CSVParser implements ICSVParser {
  private String CSVName;
  private BufferedReader inputFile;
  private HashMap<Integer, String> headerIndexMap;

  /**
   * Constructor of the class
   * @param CSVName type String
   */
  public CSVParser(String CSVName) {
    this.CSVName = CSVName;
  }

  /**
   * Getter of the CSV file name
   * @return the CSV file name
   */
  public String getCSVName() {
    return CSVName;
  }

  /**
   * Getter of the header index map
   * @return the header index map
   */
  public HashMap<Integer, String> getHeaderIndexMap() {
    return headerIndexMap;
  }

  /**
   * Helper method that pre process the CSV header and store
   * the header into a header index map
   * @throws InvalidArgumentException if file not found or anything went wrong
   */
  public void preprocessCSV() throws InvalidArgumentException {
    inputFile = null;

    try {
      inputFile = new BufferedReader(new FileReader(this.CSVName));

      String firstLine = inputFile.readLine();
      // split a String by ",", without changing contents in the double quotes
      String[] headers = firstLine.split("\"");
      headerIndexMap = new HashMap<>();
      for (int i = 0; i < headers.length; i++) {
        String header = headers[i].replaceAll("\"", "");
        headerIndexMap.put(i, header);
      }
    } catch (FileNotFoundException fnfe) {
      throw new InvalidArgumentException("A File was Not Found: " + fnfe.getMessage());
    } catch (IOException ioe) {
      throw new InvalidArgumentException("Something went wrong! : " + ioe.getMessage());
    }
  }


  /**
   * Helper method that content of the CSV file
   * @return a record that stores each person's information with corresponding header
   */
  public HashMap<String, String> nextRecord() {
    HashMap<String, String> record = new HashMap<>();

    try{
      String line;
      while((line = this.inputFile.readLine()) != null){
        String[] columns = line.split("\",\"");
        if(columns.length != headerIndexMap.size()){
          System.out.println(String.format("This line %s has wrong format. Please fix it!", line));
          continue;
        }
        for(int i = 0; i < columns.length; i++){
          String header = columns[i].replaceAll("\"", "");
          record.put(headerIndexMap.get(i), header);
        }
        break;
      }
      if(line == null)  this.closeCSV();
    }catch(IOException ioe){
      System.out.println("Something went wrong in close the CSV file: " + ioe.getMessage());
    }
    return record.isEmpty()? null: record;
  }

  /**
   * Helper method that close the CSV file while done
   * will throw exception and return corresponding message
   * if anything went wrong during closure
   */
  public void closeCSV() {
    if (inputFile != null) {
      try {
        inputFile.close();
      } catch (IOException ioe) {
        System.out.println("Something went wrong in close the CSV file: " + ioe.getMessage());
      }
    }
  }

  @Override
  public String toString() {
    return "CSVParser{" +
        "CSVName='" + CSVName + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CSVParser)) {
      return false;
    }
    CSVParser csvParser = (CSVParser) o;
    return Objects.equals(CSVName, csvParser.CSVName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(CSVName);
  }
}
