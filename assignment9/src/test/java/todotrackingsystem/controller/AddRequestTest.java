package todotrackingsystem.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.model.ToDoItem;
import todotrackingsystem.model.ToDoList;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.Option;

public class AddRequestTest {
  private IRequest addRequest;
  private List<Option> addOptions;
  private ToDoList toDoList;
  private ToDoItem hw9;
  private List<Option> allOptions;

  @Before
  public void setUp() throws Exception {
    toDoList = new ToDoList();
    hw9 = new ToDoItem.Builder(1, "Finish hw 9").setCategory("school").build();
    toDoList.addExistingToDo(1, hw9);
    Option add = new Option.Builder(Rules.ADD_REQUEST, "add new todo").build();
    Option todoText = new Option.Builder(Rules.TODO_TEXT, "new todo text").build();
    todoText.setArgValue("Review");
    Option due = new Option.Builder(Rules.SET_NEW_TODO_DUE, "due").setHasArg().build();
    due.setArgValue("03/22/2018");
    Option priority = new Option.Builder(Rules.SET_NEW_TODO_PRIORITY, "priority").setHasArg().build();
    priority.setArgValue("2");
    Option category = new Option.Builder(Rules.SET_NEW_TODO_CATEGORY, "category").setHasArg().build();
    category.setArgValue("school");
    Option complete = new Option.Builder(Rules.SET_NEW_TODO_COMPLETED, "complete new todo").build();

    todoText.setArgValue("Review 5800");
    addOptions = new ArrayList<Option>() {{
      add(add);
      add(todoText);
    }};
    addRequest = RequestFactory.sendRequest(Rules.ADD_REQUEST, addOptions, toDoList);
    allOptions = new ArrayList<Option>() {{
      add(add);
      add(todoText);
      add(due);
      add(priority);
      add(category);
      add(complete);
    }};
  }

  @Test
  public void process() {
    assertEquals(1, toDoList.getCurrentMaxID().intValue());

    addRequest.process();
    assertEquals(2, toDoList.getCurrentMaxID().intValue());
    assertEquals(2, toDoList.getTodoList().size());

    ToDoList emptyList = new ToDoList();
    IRequest allOptionAddRequest = RequestFactory.sendRequest(Rules.ADD_REQUEST, allOptions, emptyList);
    allOptionAddRequest.process();
    assertEquals(1, emptyList.getTodoList().size());
  }

  @Test
  public void testEquals() {
    assertTrue(addRequest.equals(addRequest));
    assertFalse(addRequest.equals(null));
    assertFalse(addRequest.equals("Tuesday"));

    List<Option> another = new ArrayList<Option>() {{
      add(new Option.Builder(Rules.ADD_REQUEST,"add new todo").build());
      add(new Option.Builder(Rules.TODO_TEXT, "new todo text").build());
      add(new Option.Builder(Rules.SET_NEW_TODO_COMPLETED, "set completed new todo").build());
    }};
    assertFalse(addRequest.equals(RequestFactory.sendRequest(Rules.ADD_REQUEST, another, toDoList)));
    assertFalse(addRequest.equals(RequestFactory.sendRequest(Rules.ADD_REQUEST, addOptions, new ToDoList())));
    assertTrue(addRequest.equals(RequestFactory.sendRequest(Rules.ADD_REQUEST, addOptions, toDoList)));
  }

  @Test
  public void testHashCode() {
    assertEquals(addRequest.hashCode(), addRequest.hashCode());

    IRequest addCopy = RequestFactory.sendRequest(Rules.ADD_REQUEST, addOptions, toDoList);
    assertEquals(addRequest.hashCode(), addCopy.hashCode());
  }

  @Test
  public void testToString() {
    IRequest addCopy = RequestFactory.sendRequest(Rules.ADD_REQUEST, addOptions, toDoList);
    assertEquals(addCopy.toString(), addRequest.toString());
  }
}
