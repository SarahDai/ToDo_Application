import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.InvalidArgumentException;
import utils.ListFormatter;

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


}
