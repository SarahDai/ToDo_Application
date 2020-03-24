package communicationautomation;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;

public class OptionsTest {
  private Option email;
  private Option emailTemplate;
  private Option letter;
  private Option letterTemplate;
  private Pattern txtPattern = Pattern.compile(".+\\.txt");
  private OptionGroup emails = new OptionGroup("emails");
  private OptionGroup letters = new OptionGroup("letters");
  private Options options = new Options();

  @Before
  public void setUp() throws Exception {
    email = new Option("--email", "Generate email messages. If this option is provided, then --email-template must also be provided.");
    emailTemplate = new Option("--email-template", false, true, txtPattern,"A filename for the email template");
    letter = new Option("--letter","Generate letters. If this option is provided, then --letter-template must also be provided.");
    letterTemplate = new Option("--letter-template", false, true, txtPattern,"A filename for the letter template.");
    emails.addOption(email);
    emails.addOption(emailTemplate);
    letters.addOption(letter);
    letters.addOption(letterTemplate);
  }

  @Test
  public void getOptionGroups() {
    assertEquals(new HashSet<OptionGroup>(), options.getOptionGroups());
    options.addOptionGroup(emails);

    HashSet<OptionGroup> optionGroups = new HashSet<>();
    optionGroups.add(emails);
    assertEquals(optionGroups, options.getOptionGroups());
  }

  @Test
  public void addOptionGroup() {
    assertEquals(0, options.getOptionGroups().size());
    options.addOptionGroup(emails);
    assertEquals(2, options.getAllOptions().size());

    letters.setRequired(true);
    options.addOptionGroup(letters);
    assertEquals(2, options.getRequiredOptions().size());
  }

  @Test
  public void testEquals() {
    assertTrue(options.equals(options));
    assertFalse(options.equals(null));
    assertFalse(options.equals(email));

    assertFalse(options.equals(Rules.getOptions()));
  }
}