package communicationautomation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;

public class ValidArgsTest {
  private Option emailTemplate;
  private Option letterTemplate;
  private ValidArgs validArgs;

  @Before
  public void setUp() throws Exception {
    emailTemplate = new Option("--email-template", "A filename for the email template");
    letterTemplate = new Option("--letter-template", "A filename for the letter template.");
    validArgs = new ValidArgs();
    validArgs.addOption(emailTemplate);
    validArgs.addOption(letterTemplate);
  }

  @Test
  public void getOption() {
    assertEquals(emailTemplate, validArgs.getOption("--email-template"));
  }

  @Test
  public void getGroupOptions() {
    List<Option> template = new ArrayList<>();
    template.add(emailTemplate);
    template.add(letterTemplate);
    assertEquals(template, validArgs.getGroupOptions("template"));
  }

  @Test
  public void testEquals() {
    assertEquals(validArgs, validArgs);
    assertFalse(validArgs.equals(null));
    assertFalse(validArgs.equals(emailTemplate));
    assertFalse(validArgs.equals(new ValidArgs()));

    ValidArgs copy = new ValidArgs();
    copy.addOption(emailTemplate);
    copy.addOption(letterTemplate);
    assertEquals(validArgs, copy);
  }

  @Test
  public void testHashCode() {
    assertEquals(validArgs.hashCode(), validArgs.hashCode());

    ValidArgs copy = new ValidArgs();
    copy.addOption(emailTemplate);
    copy.addOption(letterTemplate);
    assertEquals(validArgs.hashCode(), copy.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals(
        "ValidArgs{options={--email-template=Option{opt='--email-template', isRequired=false, hasArg=false, pattern=null, argValue='null', group='null', description='A filename for the email template'}, --letter-template=Option{opt='--letter-template', isRequired=false, hasArg=false, pattern=null, argValue='null', group='null', description='A filename for the letter template.'}}, optionTypes={template=[Option{opt='--email-template', isRequired=false, hasArg=false, pattern=null, argValue='null', group='null', description='A filename for the email template'}, Option{opt='--letter-template', isRequired=false, hasArg=false, pattern=null, argValue='null', group='null', description='A filename for the letter template.'}]}}",
        validArgs.toString());
  }
}
