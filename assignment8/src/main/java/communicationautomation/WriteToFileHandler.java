package communicationautomation;

import java.io.*;

public class WriteToFileHandler implements IDeliverHandler {

  private String outputDir;

  public WriteToFileHandler(String outputDir) {
    this.outputDir = outputDir;
  }

  @Override
  public void deliver(String file, int index, String fileType) {
    try {
      String folderPath = String.format("%s%s%s", outputDir, File.separator, fileType);
      File fileFolder = new File(folderPath);
      if (!fileFolder.exists()) {
        fileFolder.mkdir();
      }

      BufferedWriter outputFile = new BufferedWriter(
          new FileWriter(String.format("%s%s%s.txt", folderPath, File.separator, index)));
      outputFile.write(file);
      outputFile.close();
    } catch (IOException ioe) {
      System.out.println(String.format("Something went wrong in writing file %s! : %s", index, ioe.getMessage()));
    }
  }

  @Override
  public String getOutputDir() {
    return this.outputDir;
  }

  @Override
  public String toString() {
    return "WriteToFileHandler{" +
        "outputPath='" + outputDir + '\'' +
        '}';
  }

  //  public static void main(String[] args) {
//      WriteToFileHandler handler = new WriteToFileHandler("/Users/xinyu/Downloads/Assignment8");
//      handler.deliver("test output", 0, "test");
//  }
}
