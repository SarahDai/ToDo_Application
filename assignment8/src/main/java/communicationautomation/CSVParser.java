package communicationautomation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVParser implements ICSVParser {
  private String CSVName;
  private BufferedReader inputFile;
  private HashMap<Integer, String> headerIndexMap;

  public CSVParser(String CSVName) {
    this.CSVName = CSVName;
  }

  public String getCSVName() {
    return CSVName;
  }

  public HashMap<Integer, String> getHeaderIndexMap() {
    return headerIndexMap;
  }

  public void preprocessCSV() throws InvalidArgumentException {
    inputFile = null;

    try {
      inputFile = new BufferedReader(new FileReader(this.CSVName));

      String firstLine = inputFile.readLine();
      // split a String by ",", without changing contents in the double quotes
      String[] headers = firstLine.split(",");
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

  public void closeCSV() {
    if (inputFile != null) {
      try {
        inputFile.close();
      } catch (IOException ioe) {
        System.out.println("Something went wrong in close the CSV file: " + ioe.getMessage());
      }
    }
  }
}
