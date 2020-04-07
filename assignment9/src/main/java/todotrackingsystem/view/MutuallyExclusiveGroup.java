package todotrackingsystem.view;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import todotrackingsystem.utils.InvalidArgumentException;

/**
 * The type Mutually exclusive group.
 */
public class MutuallyExclusiveGroup extends OptionGroup {
    /**
     * The options inside the conflictedOptions set
     * cannot appear simultaneously.
     */
    private Set<String> conflictedOptions;

    /**
     * Instantiates a new Mutually exclusive group.
     *
     * @param name the name
     * @param isRequired the is required
     */
    public MutuallyExclusiveGroup(String name, boolean isRequired) {
        super(name, isRequired);
        this.conflictedOptions = new HashSet<>();
    }

    /**
     * Add conflicted option.
     *
     * @param options the options
     * @throws InvalidArgumentException the invalid argument exception
     */
    public void addConflictedOption(List<Option> options) throws InvalidArgumentException {
        checkOptions(options);
        for (Option option : options) {
            this.conflictedOptions.add(option.getName());
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
        String appear = null;
        for (Option opt : options) {
            String optName = opt.getName();
            if (this.conflictedOptions.contains(optName))
                if (appear == null) {
                    appear = optName;
                } else {
                    throw new InvalidArgumentException(
                        String.format("%s provided, it cannot combined with %s.",
                        appear, optName));
                }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        MutuallyExclusiveGroup that = (MutuallyExclusiveGroup) o;
        return Objects.equals(conflictedOptions, that.conflictedOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), conflictedOptions);
    }

    @Override
    public String toString() {
        return "MutuallyExclusiveGroup{" +
            "conflictedOptions=" + conflictedOptions +
            "} " + super.toString();
    }
}
