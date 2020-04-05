import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.InvalidArgumentException;
import utils.ListFormatter;

public class MutuallyDependentGroup extends OptionGroup {
    private Map<String, List<String>> connectedOptions;

    public MutuallyDependentGroup(String name, boolean isRequired) {
        super(name, isRequired);
        this.connectedOptions = new HashMap<>();
    }

    public void addConnectedOptions(List<Option> options) throws InvalidArgumentException {
        checkOptions(options);
        List<String> optionNames = flattenOptions(options);
        for (String optName : optionNames) {
            List<String> temp = this.connectedOptions.getOrDefault(optName, new ArrayList<>());
            temp.addAll(optionNames);
            this.connectedOptions.put(optName, temp);
        }
    }

    @Override
    public void checkValid(List<Option> options) throws InvalidArgumentException {
        basicCheck(options);
        List<String> optionNames = flattenOptions(options);
        for (String optName: optionNames) {
            if (connectedOptions.containsKey(optName)) {
                List<String> provided = new ArrayList<>();
                List<String> missing = new ArrayList<>();
                for (String connectOpt: connectedOptions.get(optName)) {
                    if (!optionNames.contains(connectOpt)) {
                        missing.add(connectOpt);
                    } else {
                        provided.add(connectOpt);
                    }
                }
                if (!missing.isEmpty())
                    throw new InvalidArgumentException(
                        String.format("%s provided but no %s was given.",
                            ListFormatter.format(provided), ListFormatter.format(missing)));
            }
        }
    }

}
