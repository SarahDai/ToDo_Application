package todotrackingsystem.controller;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.model.ToDoItem;
import todotrackingsystem.model.ToDoList;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.Option;

public class DisplayRequestTest {
  private IRequest displayRequest;
  private List<Option> displayOptions;
  private ToDoList toDoList = new ToDoList();
  private ToDoItem hw9;
  private ToDoItem algorithm;
  private List<Option> sortDateOptions;
  private List<Option> sortPriorityOptions;

  @Before
  public void setUp() throws Exception {
    hw9 = new ToDoItem.Builder(1, "Finish hw 9").complete().setCategory("school").setPriority(1).build();
    algorithm = new ToDoItem.Builder(2, "Review Algorithms").setCategory("review").build();
    ToDoItem clean = new ToDoItem.Builder(3, "Clean the kitchen").complete().setDueDate(
        LocalDate.of(2020,3,2)).setCategory("home").build();
    ToDoItem review = new ToDoItem.Builder(4, "Preview Java").setCategory("school").setDueDate(LocalDate.of(2020,3,30)).build();

    toDoList.addExistingToDo(1, hw9);
    toDoList.addExistingToDo(2, algorithm);
    toDoList.addExistingToDo(3, clean);
    toDoList.addExistingToDo(4, review);

    Option display = new Option.Builder(Rules.DISPLAY_REQUEST, "display").build();
    Option showIncomplete = new Option.Builder(Rules.SHOW_INCOMPLETE, "show incomplete").build();
    Option showCategory = new Option.Builder(Rules.SHOW_INCOMPLETE, "show category").setHasArg().build();
    showCategory.setArgValue("school");
    Option sortDue = new Option.Builder(Rules.SORT_BY_DATE, "sort by due").build();
    Option sortPriority = new Option.Builder(Rules.SORT_BY_DATE, "sort by priority").build();
    displayOptions = new ArrayList<Option>() {{
      add(display);
    }};
    sortDateOptions = new ArrayList<Option>() {{
      add(display);
      add(showIncomplete);
      add(sortDue);
    }};
    sortPriorityOptions = new ArrayList<Option>() {{
      add(display);
      add(showIncomplete);
      add(showCategory);
      add(sortPriority);
    }};

    displayRequest = RequestFactory.sendRequest(Rules.DISPLAY_REQUEST, displayOptions, toDoList);
  }

  @Test
  public void process() {
    displayRequest.process();

    IRequest displaySortDate = RequestFactory.sendRequest(Rules.DISPLAY_REQUEST, sortDateOptions,toDoList);
    displaySortDate.process();

    IRequest displaySortPriority = RequestFactory.sendRequest(Rules.DISPLAY_REQUEST, sortPriorityOptions, toDoList);
    displaySortPriority.process();
  }

  @Test
  public void processNotExistingCategory() {
    Option display = new Option.Builder(Rules.DISPLAY_REQUEST, "display").build();
    Option showCategory = new Option.Builder(Rules.SHOW_INCOMPLETE, "show category").setHasArg().build();
    showCategory.setArgValue("work");
    List<Option> notExisting = new ArrayList<Option>() {{
      add(display);
      add(showCategory);
    }};
    IRequest notExistingCategory = RequestFactory.sendRequest(Rules.DISPLAY_REQUEST, notExisting, toDoList);
    notExistingCategory.process();
  }

  @Test
  public void testEquals() {
    assertTrue(displayRequest.equals(displayRequest));
    assertFalse(displayRequest.equals(null));
    assertFalse(displayRequest.equals(12));
    assertEquals(displayRequest, RequestFactory.sendRequest(Rules.DISPLAY_REQUEST, displayOptions, toDoList));

    assertFalse(displayRequest.equals(RequestFactory.sendRequest(Rules.DISPLAY_REQUEST, sortPriorityOptions, toDoList)));
    assertFalse(displayRequest.equals(RequestFactory.sendRequest(Rules.DISPLAY_REQUEST, displayOptions, new ToDoList())));
  }

  @Test
  public void testHashCode() {
    IRequest copy = RequestFactory.sendRequest(Rules.DISPLAY_REQUEST, displayOptions, toDoList);
    assertEquals(copy.hashCode(), displayRequest.hashCode());
    assertEquals(displayRequest.hashCode(), displayRequest.hashCode());
  }

  @Test
  public void testToString() {
    IRequest copy = RequestFactory.sendRequest(Rules.DISPLAY_REQUEST, displayOptions, toDoList);
    assertEquals(copy.toString(), displayRequest.toString());
  }
}