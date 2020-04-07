package todotrackingsystem.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.model.ToDoList;
import todotrackingsystem.utils.Rules;
import todotrackingsystem.view.Option;

public class CompleteRequestTest {
  List<Option> options;
  ToDoList toDoList;
  CompleteRequest completeRequest;


  @Before
  public void setUp() throws Exception {
    options = new ArrayList<>();
    toDoList = new ToDoList();
    completeRequest = new CompleteRequest(options, toDoList);
//    tempFile = File.createTempFile("temp", ".csv");
//    csvFile = CSVFile.readCSV(tempFile.getAbsolutePath());
  }

  @Test
  public void process() {
    Option option = new Option.Builder(Rules.TODO_TEXT, "description").build();
    option.setArgValue("id");
    options.add(option);

    this.toDoList.containsID(Integer.valueOf(option.getArgValue()));



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
}