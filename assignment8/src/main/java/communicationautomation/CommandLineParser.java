package communicationautomation;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandLineParser {
  private Options options;
  private List<String> requiredOptions;
  private ValidArgs validArgs = new ValidArgs();

  public CommandLineParser(Options options) {
    this.setOptions(options);
  }

  private void setOptions(Options options) {
    this.options = options;
    this.requiredOptions = new ArrayList<>(options.getRequiredOptions());
  }

  public ValidArgs parseCommand(String[] args) throws InvalidArgumentException {
    ListIterator<String> iterator = Arrays.asList(args).listIterator();

    while (iterator.hasNext()) {
      String arg = iterator.next();
      if (this.options.hasOption(arg)) {
        this.processOption(arg, iterator);
      } else {
        throw new InvalidArgumentException(String.format("Unknown arguments: %s!\n\n%s", arg, this.options.getUsage()));
      }
    }
    checkRequiredOptions();
    checkRelatedOptions();

    return this.validArgs;
  }

  private void processOption(String opt, ListIterator<String> iterator) throws InvalidArgumentException{
    Option option = new Option(this.options.getOption(opt));

    if (option.hasArg()) {
      processArg(option,iterator);
    }

    if (option.isRequired()) {
      this.requiredOptions.remove(option.getName());
    }

    this.validArgs.addOption(option);
  }

  private void processArg(Option option, ListIterator<String> iterator) throws InvalidArgumentException{
    if (iterator.hasNext()) {
      String str = iterator.next();

      if (isValidArg(option, str)) {
        option.setArgValue(str);
      }
    } else throw new InvalidArgumentException(String.format("%s provided but the required argument is not provided!", option.getName()));
  }

  private boolean isValidArg(Option option, String arg) throws InvalidArgumentException{
    if (!arg.startsWith("--") && !arg.startsWith("-") && option.getPattern().matcher(arg).matches()) {
      return true;
    } else throw new InvalidArgumentException(String.format("%s provided but the provided argument format is wrong!\n Please provide: %s", option.getName(), option.getDescription()));
  }

  private void checkRequiredOptions() throws InvalidArgumentException {
    if (!this.requiredOptions.isEmpty()) {
      String missingArgs = this.formatString(this.requiredOptions);
      throw new InvalidArgumentException(String.format("%s required but missing.\n\n%s", missingArgs, this.options.getUsage()));
    }
  }

  private void checkRelatedOptions() throws InvalidArgumentException {
    for (Option option: this.validArgs.getOptions()) {
      if (this.options.hasOptionGroup(option)) {
        OptionGroup group = this.options.getOptionGroup(option);
        List<String> provided = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        for (Option opt: group.getOptions()) {
          if (!this.validArgs.hasOption(opt.getName())) {
            missing.add(opt.getName());
          } else {
            provided.add(opt.getName());
          }
        }
        if (!missing.isEmpty()) {
          throw new InvalidArgumentException(String.format("%s provided but no %s was given.\n\n%s", this.formatString(provided), this.formatString(missing), this.options.getUsage()));
        }
      }
    }

  }

  private String formatString(List<String> information) {
    String commaSeparatedString = information.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
    return commaSeparatedString;
  }

  public static void main(String[] args) {

    CommandLineParser cmd = new CommandLineParser(Rules.getOptions());
    String[] in = {"--email-template", "email-template.txt"};

    try {
      cmd.parseCommand(in);
    } catch (InvalidArgumentException ex) {
      System.out.println("***OOPS! Something went wrong: " + ex.getMessage());
    }
  }
}


