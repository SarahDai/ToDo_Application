package todotrackingsystem.utils;

import java.util.List;
import java.util.stream.Collectors;

public class ListFormatter {
    /**
     * Format the list of Strings to be a whole String.
     *
     * @param info the list of Strings to be formatted
     * @return the whole string containing info from the input
     */
    public static String format(List<String> info) {
        return info.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
    }
}
