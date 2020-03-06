package middlelayersystem;

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

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }

    @Override
    public String toString() {
        return "CheckBoxValidator{}";
    }
}
