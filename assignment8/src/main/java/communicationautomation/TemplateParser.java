package communicationautomation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParser implements ITemplateParser{
    private static final Pattern PATTERN = Pattern.compile("(\\[\\[([\\w]*)]])");
    private static final String START_SIGNAL = "[[";

//    Initialization:
//    input: a list of path of different template.
//    process: open the template, and store all placeholders in a hashset

//    Replacement:
//    input: a hashmap <key:header, value:row_data>
//    return a string containing the template with replaced data.

//    private String path;
    private String type;
    private List<String> template;

    public static ITemplateParser createTemplate(String type, String fileName)
        throws InvalidArgumentException {
        return new TemplateParser(type, fileName);
    }

    public TemplateParser(String type, String path) throws InvalidArgumentException {
        this.type = type;
//        this.path = path;
        this.template = this.templateProcessor(path);
    }

    private List<String> templateProcessor(String path)
        throws InvalidArgumentException {
        List<String> template = new ArrayList<>();
        String data = this.templateReader(path);
        Matcher matcher = PATTERN.matcher(data);
        int start_index = 0;
        while (matcher.find()) {
            template.add(data.substring(start_index, matcher.start() - 1));
            template.add(START_SIGNAL + matcher.group(2));
            start_index = matcher.end();
        }
        if (start_index < data.length()) template.add(data.substring(start_index, data.length()));
        return template;
    }

    private String templateReader(String path) throws InvalidArgumentException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String row = "";
            while ((row = reader.readLine()) != null) {
                row += row + '\n';
            }
            if (row.length() == 0)
                throw new InvalidArgumentException("The template file is empty.");
            return row;
        } catch (FileNotFoundException e) {
            throw new InvalidArgumentException("The file is not found.");
        } catch (IOException e) {
            throw new InvalidArgumentException("IO exception.");
        }
    }

    @Override
    public void preprocessTemplate() {

    }

    @Override
    public String updateTemplate(HashMap<String, String> record) throws InvalidArgumentException {
        StringBuilder output = new StringBuilder();
        for (String part : template) {
            if (part.startsWith(START_SIGNAL)) {
                String row = record.getOrDefault(part.substring(2), null);
                if (row == null)
                    throw new InvalidArgumentException("Template's placeholder: " +
                        part.substring(2) + ", cannot be found");
                output.append(row);
            } else {
                output.append(part);
            }
        }
        return output.toString();
    }

    @Override
    public String getTemplateType() {
        return this.type;
    }
}
