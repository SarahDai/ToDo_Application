package todotrackingsystem.view;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import todotrackingsystem.utils.InvalidArgumentException;

public class MutuallyExclusiveGroup extends OptionGroup {
    private Set<String> conflictedOptions;

    public MutuallyExclusiveGroup(String name, boolean isRequired) {
        super(name, isRequired);
        this.conflictedOptions = new HashSet<>();
    }

    public void addConflictedOption(List<Option> options) throws InvalidArgumentException {
        checkOptions(options);
        for (Option option : options) {
            this.conflictedOptions.add(option.getName());
        }
    }

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MutuallyExclusiveGroup that = (MutuallyExclusiveGroup) o;
        return Objects.equals(conflictedOptions, that.conflictedOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conflictedOptions);
    }

    @Override
    public String toString() {
        return "MutuallyExclusiveGroup{" +
            "conflictedOptions=" + conflictedOptions +
            "} " + super.toString();
    }
}
