package middlelayersystem;

import java.util.Objects;

/**
 * The validator for the Password.
 */
public class PasswordValidator implements Validator<String> {
    private int minLen;
    private int maxLen;
    private int minLowerCase;
    private int minUpperCase;
    private int minDigits;

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
        this.minDigits = 0;
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
        return this.minDigits;
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
        this.minDigits = digits;
    }

    /**
     * Determines if the provided input meets the requirements
     *
     * @param input the password to be checked
     * @return true if the provided input meets the requirements
     */
    @Override
    public boolean isValid(String input) {
        if (input == null || !checkLength(input) || !checkLetterNum(input)) return false;
        return true;
    }

    /**
     * Check if the input has valid length
     * @param input the string to be checked
     * @return true if the input has valid length
     */
    private boolean checkLength(String input) {
        if (input.length() < this.minLen || input.length() > this.maxLen) return false;
        return true;
    }

    /**
     * Check if the input meets the required length to different types of letters
     * @param input the string to be checked
     * @return true if the input meets the requirement
     */
    private boolean checkLetterNum(String input) {
        int lowerCase = 0;
        int upperCase = 0;
        int digitsNum = 0;
        for (char x : input.toCharArray()) {
            if (Character.isLowerCase(x)) lowerCase++;
            if (Character.isUpperCase(x)) upperCase++;
            if (Character.isDigit(x)) digitsNum++;
            if (Character.isSpaceChar(x)) return false;
        }
        return lowerCase >= this.minLowerCase && upperCase >= this.minUpperCase
            && digitsNum >= this.minDigits;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        PasswordValidator that = (PasswordValidator) object;
        return minLen == that.minLen &&
            maxLen == that.maxLen &&
            minLowerCase == that.minLowerCase &&
            minUpperCase == that.minUpperCase &&
            minDigits == that.minDigits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minLen, maxLen, minLowerCase, minUpperCase, minDigits);
    }

    @Override
    public String toString() {
        return "PasswordValidator{" +
            "minLen=" + minLen +
            ", maxLen=" + maxLen +
            ", minLowerCase=" + minLowerCase +
            ", minUpperCase=" + minUpperCase +
            ", minDigits=" + minDigits +
            '}';
    }
}
