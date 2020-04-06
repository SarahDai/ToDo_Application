package todotrackingsystem.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import todotrackingsystem.utils.InvalidArgumentException;

public class RepeatedGroup extends OptionGroup {
    private Set<String> repeatedOptions;

    public RepeatedGroup(String name, boolean isRequired) {
        super(name, isRequired);
        this.repeatedOptions = new HashSet<>();
    }

    public void addRepeatedOptions(List<Option> options) throws InvalidArgumentException {
        checkOptions(options);
        for (Option option : options) {
            this.repeatedOptions.add(option.getName());
        }
    }

    @Override
    public void checkValid(List<Option> options) throws InvalidArgumentException {
        List<Option> optionsNotRepeated = options.stream().filter(
            option -> this.repeatedOptions.contains(option)).collect(
            Collectors.toList());
        basicCheck(optionsNotRepeated);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepeatedGroup that = (RepeatedGroup) o;
        return Objects.equals(repeatedOptions, that.repeatedOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repeatedOptions);
    }

    @Override
    public String toString() {
        return "RepeatedGroup{" +
            "repeatedOptions=" + repeatedOptions +
            "} " + super.toString();
    }
    //    public static void main(String[] args) throws InvalidArgumentException {
//        RepeatedGroup rg = new RepeatedGroup("name", false);
//        Option addTodo = new Option.Builder("add",
//            "Add a new todo. If this option is provided, then --todo-text must also be "
//                + "provided.").setRequired().build();
//        Option todoText = new Option.Builder("text",
//            "A description of the todo.").setRequired().setHasArg().build();
//        rg.addOption(addTodo);
//        rg.addOption(todoText);
//        List<Option> addTodoConnected = new ArrayList<Option>() {{
//            add(addTodo);
//            add(todoText);
//        }};
//        rg.addRepeatedOptions(addTodoConnected);
//        rg.checkValid(addTodoConnected);
//    }

}
