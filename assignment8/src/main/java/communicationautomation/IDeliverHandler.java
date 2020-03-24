package communicationautomation;

/**
 * The interface Deliver handler, contains .
 */
public interface IDeliverHandler {

  /**
   * Deliver the input String, name it using the given fileType and the index.
   *
   * @param file     the file content to be delivered
   * @param index    the index of the current file
   * @param fileType the file type of the file
   * @param fileName the name of the file to be delivered
   */
  void deliver(String file, int index, String fileType, String fileName);

  /**
   * Gets the output directory of the deliver.
   *
   * @return the output directory
   */
  String getOutputDir();
}
