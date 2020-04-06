package todotrackingsystem.view;

import java.util.List;
import todotrackingsystem.model.ToDoItem;

public class DisplayToDoList {
  public static String display(List<ToDoItem> todoList) {
    StringBuilder sb = new StringBuilder();

    for (ToDoItem todo: todoList) {
      sb.append(todo.toString());
    }
    return sb.toString();
  }
}
