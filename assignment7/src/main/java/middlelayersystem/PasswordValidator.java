package middlelayerpackage;

/**
 * The validator for the Password.
 */
public class PasswordValidator implements Validator<String> {
    private static final int UPPERCASE_LOW_BOUND = 65;
    private static final int UPPERCASE_HIGH_BOUND = 90;
    private static final int LOWERCASE_LOW_BOUND = 97;
    private static final int LOWERCASE_HIGH_BOUND = 122;
    private static final int DIGIT_LOW_BOUND = 48;
    private static final int DIGIT_HIGH_BOUND = 57;
    private static final int SPACE_VAL = 32;
    private int minLen;
    private int maxLen;
    private int minLowerCase;
    private int minUpperCase;
    private int digits;

    /**
     * Instantiates a new Password validator.
     *
     * @param minLen the min length of this password required
     * @param maxLen the max length of this password required
     */
    public PasswordValidator(int minLen, int maxLen) {
        this.minLen = minLen;
        this.maxLen = maxLen;
        this.minLowerCase = 0;
        this.minUpperCase = 0;
        this.digits = 0;
    }

    /**
     * Gets min length of this password required.
     *
     * @return the min length of this password required
     */
    public int getMinLen() {
        return this.minLen;
    }

    /**
     * Gets max length of this password required.
     *
     * @return the max length of this password required
     */
    public int getMaxLen() {
        return this.maxLen;
    }

    /**
     * Gets the minimum number of lowercase letters.
     *
     * @return the minimum number of lowercase letters
     */
    public int getMinLowerCase() {
        return this.minLowerCase;
    }

    /**
     * Gets minimum number of uppercase letters.
     *
     * @return the minimum number of uppercase letters
     */
    public int getMinUpperCase() {
        return this.minUpperCase;
    }

    /**
     * Gets minimum number of digits.
     *
     * @return the minimum number of digits
     */
    public int getDigits() {
        return this.digits;
    }

    /**
     * Sets minimum number of lowercase letters.
     *
     * @param minLowerCase the minimum number of lowercase letters
     */
    public void setMinLowerCase(int minLowerCase) {
        this.minLowerCase = minLowerCase;
    }

    /**
     * Sets minimum number of uppercase letters.
     *
     * @param minUpperCase the minimum number of uppercase letters
     */
    public void setMinUpperCase(int minUpperCase) {
        this.minUpperCase = minUpperCase;
    }

    /**
     * Sets minimum number of digits.
     *
     * @param digits the minimum number of digits
     */
    public void setDigits(int digits) {
        this.digits = digits;
    }

    /**
     * Determines if the provided input meets the requirements
     *
     * @param input the password to be checked
     * @return true if the provided input meets the requirements
     */
    @Override
    public boolean isValid(String input) {
        if ((input.length() < minLen || input.length() > maxLen) ||
            checkNum(input, UPPERCASE_LOW_BOUND, UPPERCASE_HIGH_BOUND, this.minUpperCase) ||
            checkNum(input, LOWERCASE_LOW_BOUND, LOWERCASE_HIGH_BOUND, this.minLowerCase) ||
            checkNum(input, DIGIT_LOW_BOUND, DIGIT_HIGH_BOUND, this.digits) ||
            !checkNum(input, SPACE_VAL, SPACE_VAL, 1)
        ) return false;
        return true;
    }

    /**
     * Check if the number of characters is in the bounds.
     *
     * @param str the string
     * @param lowBound the low bound of the char in this type
     * @param highBound the high bound of the char in this type
     * @param min the minimum number that the password must contain
     * @return true if the number of characters is in the bounds.
     */
    private boolean checkNum(String str, int lowBound, int highBound, int min) {
        int num = 0;
        for (char x : str.toCharArray()) {
            if (x >= lowBound && x <= highBound) num++;
        }
        return num < min;
    }

}
