package todotrackingsystem.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import todotrackingsystem.view.Option;

public class RequestFactoryTest {
  RequestFactory requestFactory;
  List<Option> options;

  @Before
  public void setUp() throws Exception {
    requestFactory = new RequestFactory();
    options = new ArrayList<>();
  }

  @Test
  public void sendRequest() {
//    options.add();
//    requestFactory.sendRequest("--complete--todo",  csvFile)

  }
}