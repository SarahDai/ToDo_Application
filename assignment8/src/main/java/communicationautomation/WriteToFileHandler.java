package communicationautomation;

import java.io.*;

public class WriteToFileHandler implements IDeliverHandler {

  private String outputPath;

  public WriteToFileHandler(String outputPath) {
    this.outputPath = outputPath;
  }

  @Override
  public void deliver(String file, int index, String fileType) {
    try {
      String folderPath = String.format("%s%s%s", outputPath, File.separator, fileType);
      File fileFolder = new File(folderPath);
      fileFolder.mkdir();
      BufferedWriter outputFile = new BufferedWriter(
          new FileWriter(String.format("%s%s%s.txt", folderPath, File.separator, index)));
      outputFile.write(file);
      outputFile.close();
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
    }
  }

  @Override
  public String toString() {
    return "WriteToFileHandler{" +
        "outputPath='" + outputPath + '\'' +
        '}';
  }

  //  public static void main(String[] args) {
//      WriteToFileHandler handler = new WriteToFileHandler("/Users/xinyu/Downloads/Assignment8");
//      handler.deliver("test output", 0, "test");
//  }
}
