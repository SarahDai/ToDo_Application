package communicationautomation;

import static org.junit.Assert.*;

import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;

public class OptionTest {
  private Option email;
  private Option another;
  private Option emailTemplate;
  private Pattern pattern = Pattern.compile(".+\\.txt");

  @Before
  public void setUp() throws Exception {
    email = new Option("--email", "Generate email messages. If this option is provided, then --email-template must also be provided.");
    another = new Option("--another", true, "This is an required Option.");
    emailTemplate = new Option("--email", false, true, pattern,"A filename for the email template");
  }

  @Test
  public void getArgValue() {
    assertEquals(null, emailTemplate.getArgValue());

    emailTemplate.setArgValue("email-template.txt");
    assertEquals("email-template.txt", emailTemplate.getArgValue());
  }

  @Test
  public void testEquals() {
    assertTrue(email.equals(email));
    assertFalse(email.equals(null));
    assertFalse(email.equals("12"));

    assertFalse(email.equals(another));
    assertTrue(email.equals(new Option("--email", "Generate email messages. If this option is provided, then --email-template must also be provided.")));
    assertFalse(emailTemplate.equals(new Option("--email", false, false, Pattern.compile(".+\\.txt"),"A filename for the email template")));
    assertFalse(emailTemplate.equals(new Option("--email-template", false, true, Pattern.compile(".+\\.txt"),"A filename for the email template")));
    assertFalse(emailTemplate.equals(new Option("--email", false, true, Pattern.compile(".+\\.csv"),"A filename for the email template")));

    Option copy = new Option("--email", false, true, pattern,"A filename for the email template");
    copy.setArgValue("test.txt");
    assertFalse(emailTemplate.equals(copy));

    Option anotherCopy = new Option("--email", false, true, pattern,"A filename for the email template");
    anotherCopy.setGroup("email");
    assertFalse(emailTemplate.equals(anotherCopy));
    assertFalse(emailTemplate.equals(new Option("--email", false, true, pattern,"error")));
  }

  @Test
  public void testHashCode() {
    assertEquals(another.hashCode(), new Option("--another", true, "This is an required Option.").hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("Option{opt='--another', isRequired=true, hasArg=false, pattern=null, argValue='null', group='null', description='This is an required Option.'}", another.toString());
  }
}