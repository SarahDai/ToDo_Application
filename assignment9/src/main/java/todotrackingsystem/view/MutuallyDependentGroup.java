package todotrackingsystem.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import todotrackingsystem.utils.InvalidArgumentException;
import todotrackingsystem.utils.ListFormatter;

public class MutuallyDependentGroup extends OptionGroup {
    private Set<String> connectedOptions;

    public MutuallyDependentGroup(String name, boolean isRequired) {
        super(name, isRequired);
        this.connectedOptions = new HashSet<>();
    }

    public void addConnectedOptions(List<Option> options) throws InvalidArgumentException {
        checkOptions(options);
        for (Option option : options) {
            this.connectedOptions.add(option.getName());
        }
    }

    @Override
    public void checkValid(List<Option> options) throws InvalidArgumentException {
        basicCheck(options);
        List<String> missing = new ArrayList<>(this.connectedOptions);
        for (Option opt : options) {
            String optName = opt.getName();
            if (this.connectedOptions.contains(optName)) missing.remove(optName);


        }
        if (!missing.isEmpty())
            throw new InvalidArgumentException(
                String.format("%s should be provided.", ListFormatter.format(missing)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MutuallyDependentGroup that = (MutuallyDependentGroup) o;
        return Objects.equals(connectedOptions, that.connectedOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectedOptions);
    }

    @Override
    public String toString() {
        return "MutuallyDependentGroup{" +
            "connectedOptions=" + connectedOptions +
            "} " + super.toString();
    }
}
