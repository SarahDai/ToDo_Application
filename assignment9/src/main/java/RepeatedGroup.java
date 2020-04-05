import java.util.List;
import utils.InvalidArgumentException;

public class RepeatedGroup extends OptionGroup {


    public RepeatedGroup(String name, boolean isRequired) {
        super(name, isRequired);
    }

    @Override
    public void checkValid(List<Option> options){

    }
}
