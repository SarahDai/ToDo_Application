package todotrackingsystem.utils;

import java.util.Comparator;
import java.util.HashMap;
import todotrackingsystem.view.OptionGroup;

public class OptionRequestComparator implements Comparator<String> {
  private static final HashMap<String, Integer> ORDER = new HashMap<String, Integer>() {{
    put(Rules.ADD_REQUEST, 1);
    put(Rules.COMPLETE_REQUEST, 2);
    put(Rules.DISPLAY_REQUEST, 3);
  }};

  @Override
  public int compare(String o1, String o2) {
    return ORDER.get(o1) - ORDER.get(o2);
  }
}
