package communicationautomation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;

public class OptionGroupTest {
  private Option email;
  private Option emailTemplate;
  private OptionGroup emails;

  @Before
  public void setUp() throws Exception {
    email = new Option("--email", "Generate email messages. If this option is provided, then --email-template must also be provided.");
    emailTemplate = new Option("--email-template", false, true, Pattern.compile(".+\\.txt"),"A filename for the email template");
    emails = new OptionGroup("emails");
  }

  @Test
  public void getName() {
    assertEquals("emails", emails.getName());
  }

  @Test
  public void addOption() {
    emails.addOption(email);
    assertEquals("emails", email.getGroup());

    emails.addOption(emailTemplate);
    assertEquals("emails", emailTemplate.getGroup());
  }

  @Test
  public void isRequired() {
    assertEquals(false, emails.isRequired());
  }

  @Test
  public void getOptions() {
    emails.addOption(email);
    emails.addOption(emailTemplate);

    List<Option> optionList = new ArrayList<>();
    optionList.add(email);
    optionList.add(emailTemplate);

    assertEquals(optionList, emails.getOptions());
  }

  @Test
  public void setRequired() {
    assertEquals(false, emails.isRequired());

    emails.setRequired(true);
    assertEquals(true, emails.isRequired());
  }

  @Test
  public void testEquals() {
    assertTrue(emails.equals(emails));
    assertFalse(emails.equals(null));
    assertFalse(emails.equals(email));

    OptionGroup another = new OptionGroup("emails");
    assertTrue(emails.equals(another));

    another.addOption(email);
    assertFalse(emails.equals(another));
    assertFalse(emails.equals(new OptionGroup("letters")));
    another.setRequired(true);
    assertFalse(emails.equals(another));
  }

  @Test
  public void testHashCode() {
    assertEquals(new OptionGroup("emails").hashCode(), emails.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("OptionGroup{name='emails', options=[], isRequired=false}", emails.toString());
  }
}