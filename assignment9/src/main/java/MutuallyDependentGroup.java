import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.InvalidArgumentException;
import utils.ListFormatter;

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
}
