package communicationautomation;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CSVParserTest {
  private CSVParser parser;
  private CSVParser invalid;

  @Before
  public void setUp() throws Exception {
    parser = new CSVParser("nonprofit-supporters.csv");
    invalid = new CSVParser("abc.csv");
  }

  @Test
  public void getCSVName() throws FileNotFoundException {
    assertEquals("nonprofit-supporters.csv", parser.getCSVName());
  }

  @Test
  public void getHeaderIndexMap() throws InvalidArgumentException {
    assertEquals(null, parser.getHeaderIndexMap());

    parser.preprocessCSV();
    assertEquals(12, parser.getHeaderIndexMap().size());
  }

  @Test
  public void nextRecord() throws IOException, InvalidArgumentException {
    parser.preprocessCSV();
    Map<String, String> firstContact = parser.nextRecord();
    Map<Integer, String> headers = parser.getHeaderIndexMap();

    //verify the columns are correctly parsed
    String firstName = "James";
    int firstNameIndex = 0;
    assertEquals(firstName, firstContact.get(headers.get(firstNameIndex)));

    String companyName = "Benton, John B Jr";
    int companyCol = 2;
    assertEquals(companyName, firstContact.get(headers.get(companyCol)));

    parser.closeCSV();
  }

  @Test
  public void preprocessCSV() throws InvalidArgumentException {
    parser.preprocessCSV();
    assertEquals(12, parser.getHeaderIndexMap().size());
    assertEquals("first_name", parser.getHeaderIndexMap().get(0));

    parser.closeCSV();
  }

  @Test(expected = InvalidArgumentException.class)
  public void fileNotFoundPreprocessCSV() throws InvalidArgumentException {
    invalid.preprocessCSV();
  }

  @Test
  public void testEquals() {
    assertTrue(parser.equals(parser));
    assertFalse(parser.equals(null));
    assertFalse(parser.equals("000"));
    assertFalse(parser.equals(invalid));
    assertTrue(invalid.equals(new CSVParser("abc.csv")));
  }

  @Test
  public void testHashCode() throws FileNotFoundException {
    assertEquals(parser.hashCode(), new CSVParser("nonprofit-supporters.csv").hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("CSVParser{CSVName='nonprofit-supporters.csv'}", parser.toString());
  }
}