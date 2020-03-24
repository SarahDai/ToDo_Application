package communicationautomation;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.memory.HeapBlock.Header;

public class CSVParserTest {
  private CSVParser parser;
  private CSVParser parser2;

  @Before
  public void setUp() throws Exception {
    parser = new CSVParser("nonprofit-supporters.csv");

    parser.preprocessCSV();
    parser2 = new CSVParser("csv");
  }

  @Test
  public void getCSVName() throws FileNotFoundException {
    assertEquals("nonprofit-supporters.csv", parser.getCSVName());
  }

  @Test
  public void getHeaderIndexMap() throws FileNotFoundException {
    Map<Integer, String> map = parser.getHeaderIndexMap();
  }

  @Test
  public void nextRecord() throws IOException, InvalidArgumentException {
    Map<String, String> map = parser.nextRecord();


    String[] column = new String[100];
    parser.nextRecord();
    parser.closeCSV();
  }

  @Test(expected = InvalidArgumentException.class)
  public void closeCSV() throws IOException, InvalidArgumentException {
    CSVParser p = new CSVParser("a");
    p.closeCSV();

    // tested exception: "A file was not found"
    p.preprocessCSV();
  }

  @Test
  public void preprocessCSV() throws InvalidArgumentException {
    parser.preprocessCSV();
  }

  @Test
  public void testToString() {
    assertNotEquals(parser.toString(), "[java.io.BufferedReader@7e829e0b]");
  }

  @Test
  public void testEquals() {
    assertTrue(parser.equals(parser));
    assertFalse(parser.equals(null));
    assertFalse(parser.equals("000"));
    assertFalse(parser.equals(parser2));
    assertFalse(parser.equals(new CSVParser("csv")));
  }

  @Test
  public void testHashCode() throws FileNotFoundException {
    assertEquals(parser.hashCode(), new CSVParser("nonprofit-supporters.csv").hashCode());
  }
}