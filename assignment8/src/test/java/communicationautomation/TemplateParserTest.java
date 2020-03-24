package communicationautomation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TemplateParserTest {
    TemplateParser parser;


    @Before
    public void setUp() throws Exception {
        parser = TemplateParser.createTemplate("test", "template_test.txt");

    }

    @Test
    public void createTemplate() {
        TemplateParser temp = TemplateParser.createTemplate("email", "email-template.txt");
    }

    @Test
    public void preprocessTemplate() throws InvalidArgumentException {
//        TemplateParser temp = TemplateParser.createTemplate("test", "/template_test.txt");
        TemplateParser temp = TemplateParser.createTemplate("test", "template_test2.txt");
        temp.preprocessTemplate();
    }

    @Test(expected = InvalidArgumentException.class)
    public void testInvalidPath() throws InvalidArgumentException {
        TemplateParser parserWithInvalidPath = TemplateParser.createTemplate("test",
            "nonexistent.txt");
        parserWithInvalidPath.preprocessTemplate();
    }

    @Test(expected = InvalidArgumentException.class)
    public void testEmpty() throws InvalidArgumentException {
        TemplateParser parserEmpty = TemplateParser.createTemplate("test",
            "empty.txt");
        parserEmpty.preprocessTemplate();
    }

    @Test
    public void updateTemplate() throws InvalidArgumentException {
        parser.preprocessTemplate();
        HashMap<String, String> validDict = new HashMap<String, String>() {{
            put("firstName", "fn");put("lastName", "ln");put("age", "2");
        }};
        assertEquals("Dear fn ln:\n"
            + "Test line with no place holder\n"
            + "2", parser.updateTemplate(validDict));

        HashMap<String, String> invalidDict = new HashMap<String, String>() {{
            put("firstName", "fn");put("lastName", "ln");
        }};
        assertEquals(null, parser.updateTemplate(invalidDict));
    }

    @Test
    public void getTemplateType() {
        assertEquals("test", parser.getTemplateType());
    }

    @Test
    public void getTemplateName() {
        assertEquals("template_test", parser.getTemplateName());
        TemplateParser invalidName = TemplateParser.createTemplate("type", "invalid");
        assertEquals(null, invalidName.getTemplateName());
    }
}