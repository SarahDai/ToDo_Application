package communicationautomation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Template parser, parse the template to String with message.
 */
public class TemplateParser implements ITemplateParser {
    private static final Pattern PATTERN = Pattern.compile("(\\[\\[([\\w]*)]])");
    private static final Pattern FILE_TYPE = Pattern.compile("(.*(/))?(.+).txt");
    private static final String START_SIGNAL = "[[";
    private String path;
    private String type;
    private List<String> template;

    /**
     * Gets template type.
     * @return the template type
     */
    @Override
    public String getTemplateType() {
        return this.type;
    }

    /**
     * Gets template name.
     * @return the template name
     */
    @Override
    public String getTemplateName() {
        Matcher matcher = FILE_TYPE.matcher(this.path);
        if (matcher.find()) return matcher.group(3);
        return null;
    }

    /**
     * Create the template parser with the name and path.
     * @param type the type of the template
     * @param path the path of the template
     * @return the template parser
     */
    public static TemplateParser createTemplate(String type, String path) {
        return new TemplateParser(type, path);
    }

    /**
     * The constructor to create the template parser with the name and path.
     * @param type the type of the template
     * @param path the path of the template
     */
    private TemplateParser(String type, String path) {
        this.type = type;
        this.path = path;
    }

    /**
     * Preprocess template, store template information.
     * @throws InvalidArgumentException if the data in template cannot be parsed correctly
     */
    @Override
    public void preprocessTemplate() throws InvalidArgumentException {
        List <String> template = new ArrayList<>();
        String data = this.templateReader();
        if (data == null) throw new InvalidArgumentException("Template error!");
        Matcher matcher = PATTERN.matcher(data);
        int start_index = 0;
        while (matcher.find()) {
            template.add(data.substring(start_index, matcher.start()));
            template.add(START_SIGNAL + matcher.group(2));
            start_index = matcher.end();
        }
        if (start_index < data.length()) template.add(data.substring(start_index));
        this.template = template;
    }

    /**
     * A helper method to read the template and transform a template file
     * to a String
     * @return a whole string of the template file or return null if there
     * is any exception including cases such as file cannot be found,
     * the template file is empty
     */
    private String templateReader() {
        StringBuilder data = new StringBuilder();
        String row = "";
        try (BufferedReader reader=new BufferedReader(new FileReader(this.path))) {
            while ((row=reader.readLine()) != null) {
                data.append(row + '\n');
            }
            if (data.length() == 0)
                throw new InvalidArgumentException("The template file is empty.");
            data = data.delete(data.length() - 1, data.length());
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return data.toString();
    }

    /**
     * Update template string with the message.
     * @param record the hashmap contains the message
     * @return the string with the message
     */
    @Override
    public String updateTemplate(HashMap<String, String> record) {
        StringBuilder output = new StringBuilder();
        try {
            for (String part: this.template) {
                if (part.startsWith(START_SIGNAL)) {
                    String row = record.getOrDefault(part.substring(2), null);
                    if (row == null)
                        throw new InvalidArgumentException("Template's placeholder: " +
                            part.substring(2) + ", cannot be found.");
                    output.append(row);
                } else {
                    output.append(part);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return output.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemplateParser that = (TemplateParser) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return "TemplateParser{" +
            "path='" + path + '\'' +
            ", type='" + type + '\'' +
            '}';
    }
}
