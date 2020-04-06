import utils.InvalidArgumentException;

public class Main {
  public static void main(String[] args) {
    try {
      CommandLineParser commandLineParser = new CommandLineParser(Rules.getOptions());
      RequestController request = new RequestController(commandLineParser);
      request.processRequests(args);
    } catch (InvalidArgumentException ex) {
      System.out.println("Something went wrong: " + ex.getMessage());
    }
  }

}
