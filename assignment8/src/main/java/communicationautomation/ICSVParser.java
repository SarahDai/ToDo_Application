package communicationautomation;

import java.util.HashMap;

public interface ICSVParser {

  void preprocessCSV() throws InvalidArgumentException;

  HashMap<String, String> nextRecord();

  void closeCSV();

}