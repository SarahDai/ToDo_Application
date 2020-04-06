

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import utils.InvalidArgumentException;

public class Rules {
    private static Options RULES;
    private static Pattern csvPattern = Pattern.compile(".+\\.csv");
    private static Pattern datePattern = Pattern.compile("((1[012])|(0?[1-9]))\\/((3[01])|(([12])|(0?))[1-9])\\/\\d{4}");
    private static Pattern priorityPattern = Pattern.compile("^[123]$");
    private static Pattern idPattern = Pattern.compile("^\\d+$");
    private static Option input = new Option.Builder("--csv-file",
        "The CSV file to process. This option is required.").setRequired().setHasArg().setPattern(csvPattern).build();

    private static Option addTodo = new Option.Builder("--add-todo",
        "Add a new todo. If this option is provided, then --todo-text must also be "
            + "provided.").setRequired().build();
    private static Option todoText = new Option.Builder("--todo-text",
        "A description of the todo.").setRequired().setHasArg().build();

    private static Option completed = new Option.Builder("--completed",
        "Sets the completed status of a new todo to true.").build();
    private static Option due = new Option.Builder("--due",
        "mm/dd/yyyy").setPattern(datePattern).setHasArg().build();
    private static Option priority = new Option.Builder("--priority",
        "Sets the priority of a new todo.").setHasArg().setPattern(priorityPattern).build();
    private static Option category = new Option.Builder("--category",
        "Sets the category of a new todo.").setHasArg().build();
    private static Option completeTodo = new Option.Builder("--complete-todo",
        "Mark the Todo with the provided ID as complete.").setRequired().setHasArg().setPattern(idPattern).build();
    private static Option display = new Option.Builder("--display",
        "Display all todos.").setRequired().build();
    private static Option showIncomplete = new Option.Builder("--show-incomplete",
        "If --display is provided, only incomplete todos should be displayed.").build();
    private static Option showWithCategory = new Option.Builder("--show-category",
        "If --display is provided, only todos with the given category should be "
            + "displayed.").setHasArg().build();
    private static Option sortByDate = new Option.Builder("--sort-by-date",
        "If --display is provided, sort the list of todos by date order"
            + " (ascending). Cannot be combined with --sort-by-priority.").build();
    private static Option sortByPriority = new Option.Builder("--sort-by-priority",
        "If --display is provided, sort the list of todos by priority"
            + " (ascending). Cannot be combined with --sort-by-date.").build();

    private static final String USAGE = "Usage:\n" +
            "--csv-file <path/to/folder> The CSV file to process. This option is required. \n" +
            "--add-todo Add a new todo. If this option is provided, then --todo-text must also be provided. \n" +
            "--completed Mark the Todo with the provided ID as complete. \n" +
            "--display Display all todos. \n";

    public static Options getOptions() throws InvalidArgumentException {
        RULES = new Options();
        MutuallyDependentGroup addTodoGroup = new MutuallyDependentGroup("addTodo", false);
        addTodoGroup.addOption(addTodo);
        addTodoGroup.addOption(todoText);
        addTodoGroup.addOption(completed);
        addTodoGroup.addOption(due);
        addTodoGroup.addOption(priority);
        addTodoGroup.addOption(category);
        List<Option> addTodoConnected = new ArrayList<Option>() {{
            add(addTodo);
            add(todoText);
        }};
        addTodoGroup.addConnectedOptions(addTodoConnected);
        MutuallyExclusiveGroup displayGroup = new MutuallyExclusiveGroup("displayTodo", false);
        displayGroup.addOption(display);
        displayGroup.addOption(showIncomplete);
        displayGroup.addOption(showWithCategory);
        displayGroup.addOption(sortByDate);
        displayGroup.addOption(sortByPriority);
        List<Option> displayConflictedOptions = new ArrayList<Option>() {{
            add(sortByDate);
            add(sortByPriority);
        }};
        displayGroup.addConflictedOption(displayConflictedOptions);
        OptionGroup completeTodoGroup = new RepeatedGroup("completeTodo", false);
        completeTodoGroup.addOption(completeTodo);
        RULES.addOption(input);
        RULES.addOptionGroup(addTodoGroup);
        RULES.addOptionGroup(displayGroup);
        RULES.addOptionGroup(completeTodoGroup);
        RULES.setUsage(USAGE);
        return RULES;
    }

    public static String getDefaultHeaders() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append("id");
        sb.append("\",\"").append("text");
        sb.append("\",\"").append("completed");
        sb.append("\",\"").append("due");
        sb.append("\",\"").append("priority");
        sb.append("\",\"").append("category");
        sb.append("\"").append(System.lineSeparator());
        return sb.toString();
    }
}
