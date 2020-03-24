package communicationautomation;

/**
 * The interface Deliver handler, contains .
 */
public interface IDeliverHandler {

  /**
   * Deliver.
   *
   * @param file     the file
   * @param index    the index
   * @param fileType the file type
   */
  void deliver(String file, int index, String fileType);

  /**
   * Gets output dir.
   *
   * @return the output dir
   */
  String getOutputDir();
}
