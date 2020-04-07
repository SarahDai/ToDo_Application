package todotrackingsystem.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import org.junit.Before;
import org.junit.Test;

public class ToDoItemTest {
  private ToDoItem allOptions;
  private ToDoItem copy;

  @Before
  public void setUp() throws Exception {
    allOptions = new ToDoItem.Builder(1, "Finish HW9").setDueDate(LocalDate.of(2020,3,22)).setPriority(1).setCategory("school").build();
    copy = new ToDoItem.Builder(1, "Finish HW9").setDueDate(LocalDate.of(2020,3,22)).setPriority(1).setCategory("school").build();
  }

  @Test
  public void getId() {
    assertEquals(1, (int)allOptions.getId());
  }

  @Test
  public void getText() {
    assertEquals("Finish HW9", allOptions.getText());
  }

  @Test
  public void isCompleted() {
    assertEquals(false, allOptions.isCompleted());
  }

  @Test
  public void getDue() {
    LocalDate date = LocalDate.of(2020,3,22);
    assertEquals(date, allOptions.getDue());
  }

  @Test
  public void getPriority() {
    assertEquals(1, allOptions.getPriority().intValue());
  }

  @Test
  public void getCategory() {
    assertEquals("school", allOptions.getCategory());
  }

  @Test
  public void completeToDo() {
    assertEquals(false, allOptions.isCompleted());

    allOptions.completeToDo();
    assertEquals(true, allOptions.isCompleted());
    //Complete a todoItem which is already complete, will print information to the console.
    allOptions.completeToDo();
    assertEquals(true, allOptions.isCompleted());
  }

  @Test
  public void testEquals() {
    assertTrue(allOptions.equals(allOptions));
    assertFalse(allOptions.equals(null));
    assertFalse(allOptions.equals("Today"));
    assertTrue(allOptions.equals(copy));

    assertFalse(allOptions.equals(new ToDoItem.Builder(2, "Hand in HW9").build()));
    assertFalse(allOptions.equals(new ToDoItem.Builder(1, "Hand in HW9").build()));
    assertFalse(allOptions.equals(new ToDoItem.Builder(1, "Finish HW9").complete().build()));
    assertFalse(allOptions.equals(new ToDoItem.Builder(1, "Finish HW9").setDueDate(LocalDate.of(2019,12,29)).build()));
    assertFalse(allOptions.equals(new ToDoItem.Builder(1, "Finish HW9").setDueDate(LocalDate.of(2020,3,22)).setPriority(2).build()));
    assertFalse(allOptions.equals(new ToDoItem.Builder(1, "Finish HW9").setDueDate(LocalDate.of(2020,3,22)).setPriority(1).build()));
  }

  @Test
  public void testHashCode() {
    assertEquals(allOptions.hashCode(), copy.hashCode());
    assertEquals(allOptions.hashCode(), allOptions.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("\"1\",\"Finish HW9\",\"false\",\"3/22/2020\",\"1\",\"school\"\n", allOptions.toString());
  }
}