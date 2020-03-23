package communicationautomation;

public interface IDeliverHandler {

  void deliver(String file, int index, String fileType);

  String getOutputDir();
}
