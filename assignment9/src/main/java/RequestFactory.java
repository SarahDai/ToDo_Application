import java.util.List;

public class RequestFactory {
  public static IRequest sendRequest(String name, List<Option> options, CSVFile csvFile){
    switch (name){
      case Rules.ADD_REQUEST:
        return new AddRequest(options, csvFile);
      case Rules.COMPLETE_REQUEST:
        return new CompleteRequest(options, csvFile);
      case Rules.DISPLAY_REQUEST:
        return new DisplayRequest(options, csvFile);
      default:
        return null;
    }
  }

}
