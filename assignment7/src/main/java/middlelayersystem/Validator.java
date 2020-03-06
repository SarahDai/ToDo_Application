package middlelayersystem;

/**
 * The interface Validator defining public behaviors of validators.
 *
 */
public interface Validator<T> {

    /**
     * Determines if the provided input meets the requirements
     *
     * @param input the input to be checked
     * @return true if the provided input meets the requirements
     */
    boolean isValid(T input);
}
