package communicationautomation;

import java.io.*;
import java.util.Objects;

/**
 * The Write to file handler object, handles the functions for writing String into File.
 */
public class WriteToFileHandler implements IDeliverHandler {

  private String outputDir;

  /**
   * Instantiates a new WriteToFileHandler.
   *
   * @param outputDir the output dir for the object to write file to
   */
  public WriteToFileHandler(String outputDir) {
    this.outputDir = outputDir;
  }

  /**
   * Given the input String, name it using the given fileName and the index.
   * Write it into the output directory and the folder based on the fileType.
   * The content with same fileType would be stored in the outputDir/fileType folder.
   * For same fileType, the content would be differentiated by the fileName.
   * The format would be outputPath/fileType/fileName-index.txt.
   *
   * @param file     the file to be delivered
   * @param index    the index of the current file
   * @param fileType the file type
   * @param fileName the name of the file to be delivered
   */
  @Override
  public void deliver(String file, int index, String fileType, String fileName) {
    try {
      String folderPath = getTemplateFolderPath(fileType);

      BufferedWriter outputFile = new BufferedWriter(
          new FileWriter(String.format("%s%s%s-%s.txt", folderPath, File.separator, fileName, index)));
      outputFile.write(file);
      outputFile.close();
    } catch (IOException ioe) {
      System.out.println(String.format("Something went wrong in writing file %s : %s", index, ioe.getMessage()));
    }
  }

  /**
   * Given the fileType, get the folder path for the fileType.
   * If the desired outputPath is not exist, we would create the folder for you and print the info.
   *
   * @param fileType the folder path for the template file type
   * @return the folder path of the template file type represented as a String
   */
  private String getTemplateFolderPath(String fileType) {
    File outputFolder = new File(this.outputDir);
    if (!outputFolder.exists()) {
      System.out.println(String.format("Created %s folder for you.", this.outputDir));
    }
    String folderPath = String.format("%s%s%s", outputDir, File.separator, fileType);
    File fileFolder = new File(folderPath);
    if (!fileFolder.exists()) {
      fileFolder.mkdirs();
    }
    return folderPath;
  }

  /**
   * Gets the output directory of the deliver.
   *
   * @return the output directory
   */
  @Override
  public String getOutputDir() {
    return this.outputDir;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WriteToFileHandler that = (WriteToFileHandler) o;
    return Objects.equals(outputDir, that.outputDir);
  }

  @Override
  public int hashCode() {
    return Objects.hash(outputDir);
  }

  @Override
  public String toString() {
    return "WriteToFileHandler{" +
        "outputPath='" + outputDir + '\'' +
        '}';
  }
}
