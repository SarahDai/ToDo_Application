package communicationautomation;

import java.util.HashMap;

public interface ITemplateParser {
  void preprocessTemplate() throws InvalidArgumentException;

  String updateTemplate(HashMap<String, String> record) throws InvalidArgumentException;

  String getTemplateType();

}
