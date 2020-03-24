package communicationautomation;

import java.util.regex.Pattern;

public class Rules {
    private static Options RULES;

    private static Pattern txtPattern = Pattern.compile(".+\\.txt");
    private static Pattern csvPattern = Pattern.compile(".+\\.csv");
    private static Option email = new Option("--email", "Generate email messages. If this option is provided, then --email-template must also be provided.");
    private static Option emailTemplate = new Option("--email-template", false, true, txtPattern,"A filename for the email template");
    private static Option letter = new Option("--letter","Generate letters. If this option is provided, then --letter-template must also be provided.");
    private static Option letterTemplate = new Option("--letter-template", false, true, txtPattern,"A filename for the letter template.");
    private static Option input = new Option("--csv-file",true, true, csvPattern,"The CSV file to process. This option is required.");
    private static Option output = new Option("--output-dir", true, true,"The folder to store all generated files. This option is required.");

    private static OptionGroup emails = new OptionGroup("emails");
    private static OptionGroup letters = new OptionGroup("letters");

    private static final String USAGE = "Usage:\n" +
            "--email Generate email messages. If this option is provided, then --email-template must also be provided.\n" +
            "--email-template <path/to/file> A filename for the email template. --letter Generate letters. If this option is provided, then --letter-template must also be provided.\n" +
            "--letter-template <path/to/file> A filename for the letter template.\n" +
            "--output-dir <path/to/folder> The folder to store all generated files. This option is required.\n" +
            "--csv-file <path/to/folder> The CSV file to process. This option is required.";

    public static Options getOptions(){
        RULES = new Options();

        emails.addOption(email);
        emails.addOption(emailTemplate);

        letters.addOption(letter);
        letters.addOption(letterTemplate);

        RULES.addOption(email);
        RULES.addOption(input);
        RULES.addOption(output);
        RULES.addOption(emailTemplate);
        RULES.addOption(letterTemplate);
        RULES.addOption(letter);

        RULES.addOptionGroup(emails);
        RULES.addOptionGroup(letters);

        RULES.setUsage(USAGE);

        return RULES;
    }





}
