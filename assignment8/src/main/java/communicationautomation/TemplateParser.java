package communicationautomation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParser implements ITemplateParser {
    private static final Pattern PATTERN = Pattern.compile("(\\[\\[([\\w]*)]])");
    private static final Pattern FILETYPE1 = Pattern.compile("(^.+).txt");
    private static final Pattern FILETYPE2 = Pattern.compile(".*(/|\\\\)(.+).txt");
    private static final String START_SIGNAL = "[[";
    private String path;
    private String type;
    private List<String> template;

    public static TemplateParser createTemplate(String type, String fileName) {
        return new TemplateParser(type, fileName);
    }

    private TemplateParser(String type, String path) {
        this.type = type;
        this.path = path;
    }

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
        if (start_index < data.length()) template.add(data.substring(start_index, data.length()));
        this.template = template;
    }

    private String templateReader() {
//        file cannot be found/empty/IO exception -> catch -> print out
//        -> return null
//        -> the preprocess function catch the null return, throw exception.
        StringBuilder data = new StringBuilder();
        String row = "";
        try (BufferedReader reader=new BufferedReader(new FileReader(this.path))) {
            while ((row=reader.readLine()) != null) {
                data.append(row + '\n');
            }
            if (data.length() == 0)
                throw new InvalidArgumentException("The template file is empty.");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return data.toString();
    }

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
    public String getTemplateType() {
        return this.type;
    }

    public String getTemplateName() {
        Matcher matcher = FILETYPE1.matcher(this.path);
        if (matcher.find()) return matcher.group(1);
        matcher = FILETYPE2.matcher(this.path);
        if (matcher.find()) return matcher.group(2);
        return null;
    }
}
