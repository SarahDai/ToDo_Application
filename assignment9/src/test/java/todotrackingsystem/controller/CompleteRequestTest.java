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

public class CompleteRequestTest {
  private IRequest completeRequest;
  private List<Option> completeOptions;
  private ToDoList toDoList = new ToDoList();
  private ToDoItem hw9;
  private ToDoItem algorithm;

  @Before
  public void setUp() throws Exception {
    hw9 = new ToDoItem.Builder(1, "Finish hw 9").setCategory("school").build();
    algorithm = new ToDoItem.Builder(2, "Review Algorithms").setCategory("school").build();
    toDoList.addExistingToDo(1, hw9);
    toDoList.addExistingToDo(2, algorithm);
    Option complete = new Option.Builder(Rules.COMPLETE_REQUEST, "complete todo").setHasArg().build();
    complete.setArgValue("1");
    Option anotherOption = new Option.Builder(Rules.COMPLETE_REQUEST, "complete todo").setHasArg().build();
    anotherOption.setArgValue("2");
    completeOptions = new ArrayList<Option>() {{
      add(complete);
      add(anotherOption);
    }};
    completeRequest = RequestFactory.sendRequest(Rules.COMPLETE_REQUEST, completeOptions, toDoList);
  }

  @Test
  public void process() {
    assertEquals(2, toDoList.getTodoList().size());
    assertEquals(false, toDoList.getToDoItem(1).isCompleted());

    completeRequest.process();
    assertEquals(2, toDoList.getTodoList().size());
    assertEquals(true, toDoList.getToDoItem(1).isCompleted());
    assertEquals(true, toDoList.getToDoItem(2).isCompleted());
  }

  @Test
  public void processNonExistingID() {
    Option completeNotExisting = new Option.Builder(Rules.COMPLETE_REQUEST, "complete todo").setHasArg().build();
    completeNotExisting.setArgValue("3");
    List<Option> notExistingID = new ArrayList<Option>() {{
      add(completeNotExisting);
    }};

    assertEquals(false, toDoList.getToDoItem(1).isCompleted());
    assertEquals(false, toDoList.getToDoItem(2).isCompleted());
    IRequest anotherRequest = RequestFactory.sendRequest(Rules.COMPLETE_REQUEST, notExistingID, toDoList);
    anotherRequest.process();
    assertEquals(false, toDoList.getToDoItem(1).isCompleted());
    assertEquals(false, toDoList.getToDoItem(2).isCompleted());
  }

  @Test
  public void processIncorrectIntegerFormat() {
    Option completeWrongFormat = new Option.Builder(Rules.COMPLETE_REQUEST, "complete todo").setHasArg().build();
    completeWrongFormat.setArgValue("one");
    List<Option> wrongFormat = new ArrayList<Option>() {{
      add(completeWrongFormat);
    }};

    assertEquals(false, toDoList.getToDoItem(1).isCompleted());
    assertEquals(false, toDoList.getToDoItem(2).isCompleted());
    IRequest anotherRequest = RequestFactory.sendRequest(Rules.COMPLETE_REQUEST, wrongFormat, toDoList);
    anotherRequest.process();
    assertEquals(false, toDoList.getToDoItem(1).isCompleted());
    assertEquals(false, toDoList.getToDoItem(2).isCompleted());
  }

  @Test
  public void testEquals() {
    assertTrue(completeRequest.equals(completeRequest));
    assertFalse(completeRequest.equals(null));
    assertFalse(completeRequest.equals("12"));

    assertEquals(completeRequest, RequestFactory.sendRequest(Rules.COMPLETE_REQUEST, completeOptions, toDoList));

    Option opt1 = new Option.Builder(Rules.COMPLETE_REQUEST, "complete todo").setHasArg().build();
    opt1.setArgValue("3");
    Option opt2 = new Option.Builder(Rules.COMPLETE_REQUEST, "complete todo").setHasArg().build();
    opt2.setArgValue("4");
    List<Option> another = new ArrayList<Option>() {{
      add(opt1);
      add(opt2);
    }};
    assertFalse(completeRequest.equals(RequestFactory.sendRequest(Rules.COMPLETE_REQUEST, another, toDoList)));
    assertFalse(completeRequest.equals(RequestFactory.sendRequest(Rules.COMPLETE_REQUEST, completeOptions, new ToDoList())));
  }

  @Test
  public void testHashCode() {
    IRequest copy = RequestFactory.sendRequest(Rules.COMPLETE_REQUEST, completeOptions, toDoList);
    assertEquals(copy.hashCode(), completeRequest.hashCode());
    assertEquals(completeRequest.hashCode(), completeRequest.hashCode());
  }

  @Test
  public void testToString() {
    IRequest copy = RequestFactory.sendRequest(Rules.COMPLETE_REQUEST, completeOptions, toDoList);
    assertEquals(copy.toString(), completeRequest.toString());
  }
}