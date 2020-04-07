package todotrackingsystem.controller;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.model.CSVFile;
import todotrackingsystem.model.ToDoList;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.Option;

public class AddRequestTest {
  List<Option> options;
  ToDoList toDoList;
  AddRequest addRequest;

@Before
  public void setUp() throws Exception {
  options = new ArrayList<>();
  toDoList = new ToDoList();
  addRequest = new AddRequest(options, toDoList);

  }
//
//  @After
//  public void TearDown() throws Exception {
//    tempFile.deleteOnExit();
//  }

  @Test
  public void processTodoTextTest()  {
    Option option = new Option.Builder(Rules.TODO_TEXT, "description").build();
    option.setArgValue("value");
    options.add(option);
    AddRequest req = new AddRequest(options, toDoList);
    req.process();
    assertEquals(toDoList.getTodoList().size(), 1);
    assertEquals(toDoList.getTodoList().get(0).getText(), "value");
  }

  @Test
  public void testEquals() {
  }

  @Test
  public void testHashCode() {
  }

  @Test
  public void testToString() {
  }
//
//  @Test
//  public void processToDoDueOptionsTest() {
//    List<Option> options = new ArrayList<Option>();
//    Option option = new Option.Builder(Rules.SET_NEW_TODO_DUE, "description").build();
//    option.setArgValue("value");
//    options.add(option);
//    AddRequest req = new AddRequest(options, toDoList);
//    req.process();
//    assertEquals(toDoList.getTodoList().size(), 1);
//    assertEquals(toDoList.getTodoList().get(0).getText(), "value");
//  }
//
//  @Test
//  public void processToDoPriorityOptionsTest() {
//    List<Option> options = new ArrayList<Option>();
//    Option option = new Option.Builder(Rules.SET_NEW_TODO_PRIORITY, "description").build();
//    option.setArgValue("value");
//    options.add(option);
//    AddRequest req = new AddRequest(options, toDoList);
//    req.process();
//    assertEquals(toDoList.getTodoList().size(), 1);
//    assertEquals(toDoList.getTodoList().get(0).getText(), "value");
//  }
//
//  @Test
//  public void processToDoCategoryOptionsTest() {
//    List<Option> options = new ArrayList<Option>();
//    Option option = new Option.Builder(Rules.SET_NEW_TODO_CATEGORY, "description").build();
//    option.setArgValue("value");
//    options.add(option);
//    AddRequest req = new AddRequest(options, toDoList);
//    req.process();
//    assertEquals(toDoList.getTodoList().size(), 1);
//    assertEquals(toDoList.getTodoList().get(0).getText(), "value");
//  }

}
