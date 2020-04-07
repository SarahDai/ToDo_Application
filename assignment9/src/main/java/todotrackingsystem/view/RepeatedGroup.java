package todotrackingsystem.view;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import todotrackingsystem.utils.InvalidArgumentException;

/**
 * The type Repeated group.
 */
public class RepeatedGroup extends OptionGroup {
    private Set<String> repeatedOptions;

    /**
     * Instantiates a new Repeated group.
     *
     * @param name the name
     * @param isRequired the is required
     */
    public RepeatedGroup(String name, boolean isRequired) {
        super(name, isRequired);
        this.repeatedOptions = new HashSet<>();
    }

    /**
     * Add repeated options.
     *
     * @param options the options
     * @throws InvalidArgumentException the invalid argument exception
     */
    public void addRepeatedOptions(List<Option> options) throws InvalidArgumentException {
        checkOptions(options);
        for (Option option : options) {
            this.repeatedOptions.add(option.getName());
        }
    }

    /**
     * Check the list of options is valid according to the group's rules.
     *
     * @param options the options
     * @throws InvalidArgumentException the invalid argument exception if the list of options
     * is invalid according to the group's rules
     */
    @Override
    public void checkValid(List<Option> options) throws InvalidArgumentException {
        basicCheck(options);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        RepeatedGroup that = (RepeatedGroup) o;
        return Objects.equals(repeatedOptions, that.repeatedOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), repeatedOptions);
    }

    @Override
    public String toString() {
        return "RepeatedGroup{" +
            "repeatedOptions=" + repeatedOptions +
            "} " + super.toString();
    }
}
