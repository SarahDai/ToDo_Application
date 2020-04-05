import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.InvalidArgumentException;
import utils.ListFormatter;

public class MutuallyExclusiveGroup extends OptionGroup {
    private Map<String, List<String>> conflictedOptions;

    public MutuallyExclusiveGroup(String name, boolean isRequired) {
        super(name, isRequired);
        this.conflictedOptions = new HashMap<>();
    }

    public void addConflictedOption(List<Option> options) throws InvalidArgumentException {
        checkOptions(options);
        List<String> optionNames = flattenOptions(options);
        for (String optName : optionNames) {
            List<String> temp = this.conflictedOptions.getOrDefault(optName, new ArrayList<>());
            temp.addAll(optionNames);
            temp.remove(optName);
            this.conflictedOptions.put(optName, temp);
        }
    }

    @Override
    public void checkValid(List<Option> options) throws InvalidArgumentException {
        basicCheck(options);
        List<String> optionNames = flattenOptions(options);
        for (String optName : optionNames) {
            if (conflictedOptions.containsKey(optName)) {
                List<String> conflicted = new ArrayList<>();
                for (String conflictName : conflictedOptions.get(optName)) {
                    if (optionNames.contains(conflictName)) conflicted.add(conflictName);
                }
                if (!conflicted.isEmpty())
                    throw new InvalidArgumentException(
                        String.format("%s provided, it cannot combined with %s.",
                            optName, ListFormatter.format(conflicted)));
            }
        }
    }


}
