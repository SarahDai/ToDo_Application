package todotrackingsystem.view;

import java.util.List;
import todotrackingsystem.model.ToDoItem;

/**
 * The type Display to do list.
 */
public class DisplayToDoList {

  /**
   * Display the todolist to the users.
   *
   * @param todoList the todo list to display
   * @return the string to be displayed
   */
  public static String display(List<ToDoItem> todoList) {
    StringBuilder sb = new StringBuilder();

    for (ToDoItem todo: todoList) {
      sb.append(todo.toString());
    }
    return sb.toString();
  }
}
