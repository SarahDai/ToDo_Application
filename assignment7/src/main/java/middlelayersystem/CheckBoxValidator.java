package middlelayerpackage;

/**
 * The validator for the Check box .
 */
public class CheckBoxValidator implements Validator<Boolean> {

    /**
     * Determines if the provided input meets the requirements
     *
     * @param input the check box to be checked
     * @return true if the provided input meets the requirements
     */
    @Override
    public boolean isValid(Boolean input) {
        return true;
    }
}
