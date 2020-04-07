package todotrackingsystem.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.utils.InvalidArgumentException;

public class CSVParserTest {
  private CSVParser csvParser = CSVParser.getParser();

  @Test
  public void getParser() {
    assertEquals(csvParser, CSVParser.getParser());
  }

  @Test
  public void readCSV() throws InvalidArgumentException {
    ToDoList emptyList = new ToDoList();
    assertEquals(emptyList, csvParser.readCSV("empty.csv"));

    ToDoList oneItem = new ToDoList();
    ToDoItem homework = new ToDoItem.Builder(1, "Finish HW9").setPriority(1).setDueDate(
        LocalDate.of(2020,3,22)).setCategory("school").build();
    oneItem.addExistingToDo(1, homework);
    assertEquals(oneItem, csvParser.readCSV("oneline.csv"));

    ToDoList another = new ToDoList();
    ToDoItem mail = new ToDoItem.Builder(2, "Mail passport").complete().build();
    another.addExistingToDo(2, mail);
    assertEquals(another, csvParser.readCSV("oneinvalidline.csv"));
  }

  @Test
  public void saveCSVFile() throws InvalidArgumentException {
    ToDoItem homework = new ToDoItem.Builder(1, "Finish HW9").setPriority(1).setDueDate(
        LocalDate.of(2020,3,22)).setCategory("school").build();

    String outputPath = "output_test.csv";
    csvParser.saveCSVFile(outputPath, homework.toString());

    ToDoList toDoList = new ToDoList() {{
      addExistingToDo(1, homework);
    }};
    assertEquals(toDoList.toString(), csvParser.readCSV(outputPath).toString());
  }
}