package todotrackingsystem.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ToDoListTest {
  private ToDoList toDoList;
  private ToDoList empty;
  private ToDoItem hw9;

  @Before
  public void setUp() throws Exception {
    toDoList = new ToDoList();
    empty = new ToDoList();
    hw9 = new ToDoItem.Builder(1, "Finish hw 9").setCategory("school").build();
    toDoList.addExistingToDo(1, hw9);
  }

  @Test
  public void getCurrentMaxID() {
    assertEquals(1, toDoList.getCurrentMaxID().intValue());
  }

  @Test
  public void getTodoList() {
    List<ToDoItem> list = new ArrayList<ToDoItem>() {{
      add(hw9);
    }};
    assertEquals(list, toDoList.getTodoList());
  }

  @Test
  public void addExistingToDo() {
    toDoList.addExistingToDo(2, new ToDoItem.Builder(2, "Mail passport").complete().build());
    assertEquals(2, toDoList.getTodoList().size());
  }

  @Test
  public void addNewToDo() {
    ToDoItem newToDo = new ToDoItem.Builder(toDoList.getCurrentMaxID()+1, "Call friend").build();
    toDoList.addNewToDo(newToDo);
    assertEquals(2, toDoList.getCurrentMaxID().intValue());
    assertEquals(2, toDoList.getTodoList().size());

    ToDoItem another = new ToDoItem.Builder(toDoList.getCurrentMaxID()+1, "Review 5800").setCategory("review").build();
    toDoList.addNewToDo(another);
    assertEquals(3, toDoList.getCurrentMaxID().intValue());
  }

  @Test
  public void containsID() {
    assertEquals(true, toDoList.containsID(1));
    assertEquals(false, toDoList.containsID(2));
  }

  @Test
  public void containsCategory() {
    assertEquals(true, toDoList.containsCategory("school"));
    assertEquals(false, toDoList.containsCategory("work"));
  }

  @Test
  public void completeToDo() {
    toDoList.completeToDo(1);
    assertEquals(true, toDoList.getToDoItem(1).isCompleted());
  }

  @Test
  public void filterIncomplete() {
    List<ToDoItem> list = new ArrayList<ToDoItem>() {{
      add(new ToDoItem.Builder(3, "Clean the kitchen").complete().build());
      add(hw9);
    }};
    List<ToDoItem> incompleteList = new ArrayList<ToDoItem>(){{
      add(hw9);
    }};

    assertEquals(incompleteList, toDoList.filterIncomplete(list));
  }

  @Test
  public void filterCategory() {
    List<ToDoItem> list = new ArrayList<ToDoItem>() {{
      add(new ToDoItem.Builder(3, "Clean the kitchen").complete().build());
      add(hw9);
      add(new ToDoItem.Builder(4, "One on one").setCategory("work").build());
      add(new ToDoItem.Builder(2, "Review Algorithms").setCategory("school").build());
    }};
    List<ToDoItem> schoolList = new ArrayList<ToDoItem>(){{
      add(hw9);
      add(new ToDoItem.Builder(2, "Review Algorithms").setCategory("school").build());
    }};

    assertEquals(schoolList, toDoList.filterCategory(list, "school"));
  }

  @Test
  public void sortByDate() {
    ToDoItem clean = new ToDoItem.Builder(3, "Clean the kitchen").complete().setDueDate(LocalDate.of(2020,3,2)).build();
    ToDoItem review = new ToDoItem.Builder(2, "Review Algorithms").setCategory("school").setDueDate(LocalDate.of(2020,3,30)).build();
    List<ToDoItem> list = new ArrayList<ToDoItem>() {{
      add(clean);
      add(hw9);
      add(review);
    }};
    List<ToDoItem> sortByDate = new ArrayList<ToDoItem>() {{
      add(clean);
      add(review);
      add(hw9);
    }};

    assertEquals(sortByDate, toDoList.sortByDate(list));
  }

  @Test
  public void sortByPriority() {
    ToDoItem clean = new ToDoItem.Builder(3, "Clean the kitchen").setPriority(3).build();
    ToDoItem review = new ToDoItem.Builder(2, "Review Algorithms").setPriority(1).build();
    List<ToDoItem> list = new ArrayList<ToDoItem>() {{
      add(clean);
      add(hw9);
      add(review);
    }};
    List<ToDoItem> sortByPriority = new ArrayList<ToDoItem>() {{
      add(review);
      add(clean);
      add(hw9);
    }};

    assertEquals(sortByPriority, toDoList.sortByPriority(list));
  }

  @Test
  public void testEquals() {
    assertTrue(toDoList.equals(toDoList));
    assertFalse(toDoList.equals(null));
    assertFalse(toDoList.equals("monday"));

    assertFalse(toDoList.equals(empty));
    ToDoList copy = new ToDoList();
    copy.addExistingToDo(1, hw9);
    assertTrue(toDoList.equals(copy));
  }

  @Test
  public void testHashCode() {
    assertEquals(toDoList.hashCode(), toDoList.hashCode());

    ToDoList copy = new ToDoList();
    copy.addExistingToDo(1, hw9);
    assertEquals(toDoList.hashCode(), copy.hashCode());
  }

  @Test
  public void testToString() {
    ToDoList copy = new ToDoList();
    copy.addExistingToDo(1, hw9);
    assertEquals(toDoList.toString(), copy.toString());
  }
}