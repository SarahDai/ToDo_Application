package todotrackingsystem.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import todotrackingsystem.model.ToDoList;
import todotrackingsystem.view.Option;

public class RequestFactoryTest {
  private List<Option> options = new ArrayList<>();
  private ToDoList toDoList = new ToDoList();

  @Test
  public void sendRequestUnknown() {
    IRequest unknown = RequestFactory.sendRequest("unkown", options, toDoList);
    assertEquals(null, unknown);
  }
}