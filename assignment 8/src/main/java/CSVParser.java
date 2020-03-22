import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVParser {
  //总共两个function
  // 如果email是null？如果第一行就错了咋办？或者是空的，split以后没有它，记下来，return error message
  // initialization: 文章打开，header存到一个map里去，打开着，什么都不做，
  // 开始之后，读第一行，存下来，更新hash map，传，readline是none的时候，close掉
  private HashMap<Integer, String> header;

  public CSVParser(HashMap<Integer, String> header) {
    this.header = header;
  }

  public HashMap<Integer, String> getHeader() {
    return header;
  }

  public void preProcessing(String file) {
    HashMap<String, String> map = new HashMap<>();
    BufferedReader inputFile = null;
    try {
      inputFile = new BufferedReader(new FileReader(file));
      String line;
      if((line = inputFile.readLine()) != null){
        // split a String by ",", without changing contents in the double quotes
        String[] splitBy = line.split("(\\s){0},(\\s){0}");

        // delete the double quotes in each string
        for (int i = 0; i < splitBy.length; i++){
          String newHeader = splitBy[i].replaceAll("\"","");
          map.put(header.get(i), newHeader);
        }
      }else{
        inputFile.close();
      }
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found!");
    } catch (IOException e) {
      System.out.println("Something went wrong!");
    }
  }

//  public static String[] parseEachLine(String input){
//
////    String[] output = input.trim().split(splitBy, -1);
//    return output;
//  }

  public static HashMap<String, String> dataStore(String[] preprocessedData){
    String keySets = preprocessedData[0];
    for(String eachPersonInfo : preprocessedData) {
      HashMap<String,String> personInfoMap = new HashMap<>();

      for(int i = 0; i < keySets.length(); i++) {
        personInfoMap.put(keySets[i], eachPersonInfo);
      }
    }
    return personInfoMap;

    // remove the first map since it maps key with key
//    storedData.remove(0);
//    return storedData;
  }
}

