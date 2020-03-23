package communicationautomation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParser {
    private static final Pattern pattern = Pattern.compile("(\\[\\[([\\w]*)]])");
    private static final String START_SIGNAL = "[[";

//    Initialization:
//    input: a list of path of different template.
//    process: open the template, and store all placeholders in a hashset

//    Replacement:
//    input: a hashmap <key:header, value:row_data>
//    return a string containing the template with replaced data.
    private List<String> template;

    public TemplateParser(String path) throws InvalidArgumentException {
        this.template = templateProcessor(path);
    }

    private List<String> templateProcessor(String path)
        throws InvalidArgumentException {
        List<String> template = new ArrayList<>();
        String data = this.templateReader(path);
        Matcher matcher = pattern.matcher(data);
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

    public List<String> replace(Map<String, String> map) throws InvalidArgumentException {
        List<String> outputs = new ArrayList<>();

        StringBuilder output = new StringBuilder();
        for (String part : template) {
            if (part.startsWith(START_SIGNAL)) {
                String row = map.getOrDefault(part.substring(2), null);
                if (row == null) throw new InvalidArgumentException("Template's placeholder: " +
                    part.substring(2) + ", cannot be found");
                output.append(row); //replace by data from input.
            } else {
                output.append(part);
            }
        }
        outputs.add(output.toString());
        return outputs;
    }

}
