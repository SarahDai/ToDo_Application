package communicationautomation;

import java.util.HashMap;

/**
 * The interface Template parser, parse the template to String with message.
 */
public interface ITemplateParser {

  /**
   * Preprocess template, store template information.
   *
   * @throws InvalidArgumentException if the data in template cannot be parsed correctly
   */
  void preprocessTemplate() throws InvalidArgumentException;

  /**
   * Update template string with the message.
   *
   * @param record the hashmap contains the message
   * @return the string with the message
   */
  String updateTemplate(HashMap<String, String> record);

  /**
   * Gets template type.
   *
   * @return the template type
   */
  String getTemplateType();

  /**
   * Gets template name.
   *
   * @return the template name
   */
  String getTemplateName();

}
