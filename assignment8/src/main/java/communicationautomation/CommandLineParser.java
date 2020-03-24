package communicationautomation;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Command line parser, parse the command line into valid arguments.
 */
public class CommandLineParser {
  private Options options;
  private List<String> requiredOptions;
  private ValidArgs validArgs = new ValidArgs();

  /**
   * Instantiates a new Command line parser.
   *
   * @param options the possible options for the command line parser
   */
  public CommandLineParser(Options options) {
    this.setOptions(options);
  }

  /**
   * Set the possible options for the command line parser.
   *
   * @param options the possible options for the command line parser
   */
  private void setOptions(Options options) {
    this.options = options;
    this.requiredOptions = new ArrayList<>(options.getRequiredOptions());
  }

  /**
   * Parse the input command line, according to the specified options.
   *
   * @param args the args the input command line arguments to be parsed.
   * @return the valid arguments represented as ValidArgs object.
   * @throws InvalidArgumentException invalid argument exception if an error occurs while parsing.
   */
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

  /**
   * Process the Option using the information retrieved from the arguments iterator
   * and the specified options.
   *
   * @param opt the String value representing an Option.
   * @param iterator the iterator of the command line arguments.
   * @throws InvalidArgumentException invalid argument exception if an error occurs while processing.
   */
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

  /**
   * Process and validate the argument value for the specified Option, using the information
   * retrieved from the arguments iterator and the specified Options.
   * Format the path argument value in this processing.
   * Replace the input path separator with File.separator.
   * Validate the type argument using the the information retrieved from Option required pattern.
   *
   * @param option the Option to be updated
   * @param iterator the iterator of the command line arguments
   * @throws InvalidArgumentException invalid argument exception if an error occurs while processing.
   */
  private void processArg(Option option, ListIterator<String> iterator) throws InvalidArgumentException{
    if (iterator.hasNext()) {
      String str = iterator.next();

      validateArg(option, str);
      str = this.formatPath(str);
      option.setArgValue(str);
    } else throw new InvalidArgumentException(String.format("%s provided but the required argument is not provided!", option.getName()));
  }

  /**
   * Format the input Path into Platform-Independent path in Java.
   *
   * @param str the argument value to be formatted
   * @return the argument value that is independent to platform if it is a path
   */
  private String formatPath(String str) {
    return str.replaceAll("\\\\|/", File.separator);
  }

  /**
   * Check whether the argument value is valid for a specified Option.
   *
   * @param option the specified Option providing the argument value format.
   * @param arg the argument value to be checked.
   * @throws InvalidArgumentException invalid argument exception if an error occurs while validating.
   */
  private void validateArg(Option option, String arg) throws InvalidArgumentException{
    if (arg.startsWith("--")) {
      throw new InvalidArgumentException(String.format("%s provided but you forgot the required argument!\nPlease provide: %s", option.getName(), option.getDescription()));
    }
    if (!option.getPattern().matcher(arg).matches()) {
      throw new InvalidArgumentException(String.format("%s provided but the provided argument format is wrong!\nPlease provide: %s", option.getName(), option.getDescription()));
    }
  }

  /**
   * Check whether the command line contains all required options.
   *
   * @throws InvalidArgumentException invalid argument exception if an error occurs while checking.
   */
  private void checkRequiredOptions() throws InvalidArgumentException {
    if (!this.requiredOptions.isEmpty()) {
      String missingArgs = this.formatString(this.requiredOptions);
      throw new InvalidArgumentException(String.format("%s required but missing.\n\n%s", missingArgs, this.options.getUsage()));
    }
  }

  /**
   * Check whether the command line contains options that has relationships.
   *
   * @throws InvalidArgumentException invalid argument exception if an error occurs while checking.
   */
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

  /**
   * Format the list of String to be a whole String.
   *
   * @param information the list of String to be formatted
   * @return the whole string containing info from the input
   */
  private String formatString(List<String> information) {
    String commaSeparatedString = information.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
    return commaSeparatedString;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandLineParser that = (CommandLineParser) o;
    return Objects.equals(options, that.options);
  }

  @Override
  public int hashCode() {
    return Objects.hash(options);
  }

  @Override
  public String toString() {
    return "CommandLineParser{" +
        "options=" + options +
        '}';
  }
}


