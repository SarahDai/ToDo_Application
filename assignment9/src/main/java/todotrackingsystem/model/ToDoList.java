package todotrackingsystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The type ToDoList, stores a collection of ToDoItems.
 */
public class ToDoList {
  private LinkedHashMap<Integer, ToDoItem> todoList;
  private Integer currentMaxID;
  private Set<String> categoryList;

  /**
   * Instantiates a new ToDoList.
   */
  public ToDoList() {
    this.todoList = new LinkedHashMap<>();
    this.currentMaxID = 0;
    this.categoryList = new HashSet<>();
  }

  /**
   * Update current max id of the todoList.
   *
   * @param id the id to be checked
   */
  private void updateCurrentMaxID(int id) {
    this.currentMaxID = Math.max(id, this.currentMaxID);
  }

  /**
   * Gets current max id of the todoList.
   *
   * @return the current max id
   */
  public Integer getCurrentMaxID() {
    return this.currentMaxID;
  }

  /**
   * Gets whole todolist, representing in List<ToDoItem>.
   *
   * @return the todo list storing in the CSVFile
   */
  public List<ToDoItem> getTodoList() {
    return new ArrayList<ToDoItem>(this.todoList.values());
  }

  /**
   * Gives the ToDoItem based on the specified id.
   *
   * @param id the ToDoItem with the id to search for
   * @return the ToDoItem with the id
   */
  public ToDoItem getToDoItem(Integer id) {
    return this.todoList.get(id);
  }

  /**
   * Add existing todoitem to the todoList.
   *
   * @param id   the id
   * @param todo the todo
   */
  public void addExistingToDo(int id, ToDoItem todo) {
    this.todoList.put(id, todo);
    this.updateCurrentMaxID(id);
    if (todo.getCategory() != null) {
      this.addCategory(todo.getCategory());
    }
  }

  /**
   * Add new todo item to the todoList. The id is autogenerated by the currentMaxID + 1.
   *
   * @param todo the todo to be added into the todoList
   */
  public void addNewToDo(ToDoItem todo) {
    this.todoList.put(++this.currentMaxID, todo);
    if (todo.getCategory() != null) {
      this.addCategory(todo.getCategory());
    }
  }

  /**
   * Checks whether the todoList contains the input id.
   *
   * @param id the id to search for
   * @return true if the id is in the todoList, false otherwise
   */
  public boolean containsID(Integer id) {
    return this.todoList.containsKey(id);
  }

  /**
   * Add category into the categoryList for future checking.
   *
   * @param category the category to be added
   */
  private void addCategory(String category) {
    this.categoryList.add(category);
  }

  /**
   * Checks whether the todoList contains the input category.
   *
   * @param category the category to search for
   * @return true if the category is in the todoList, false otherwise
   */
  public boolean containsCategory(String category) {
    return this.categoryList.contains(category);
  }

  /**
   * Complete to ToDoItem with the id. Inform the user if the specified ToDoItem is already completed.
   *
   * @param id the id associated with the ToDoItem to complete
   */
  public void completeToDo(Integer id) {
    this.todoList.get(id).completeToDo();
  }

  /**
   * Filter the todoList by incomplete todos.
   *
   * @param toDoItemList the to do item list
   * @return the list containing only incomplete todos
   */
  public List<ToDoItem> filterIncomplete(List<ToDoItem> toDoItemList) {
    List<ToDoItem> incompleteList = new ArrayList<>();
    for (ToDoItem todo: toDoItemList) {
      if (!todo.isCompleted()) {
        incompleteList.add(todo);
      }
    }
    return incompleteList;
  }

  /**
   * Filter the todoList by the specified category.
   *
   * @param toDoItemList the toDoList to filter
   * @param category     the category to search for
   * @return the list containing only the specified category todos
   */
  public List<ToDoItem> filterCategory(List<ToDoItem> toDoItemList, String category) {
    List<ToDoItem> categoryToDoItemList = new ArrayList<>();
    for (ToDoItem todo: toDoItemList) {
      if (todo.getCategory() == null) continue;
      if (todo.getCategory().equals(category)) {
        categoryToDoItemList.add(todo);
      }
    }
    return categoryToDoItemList;
  }

  /**
   * Sort the todoList by due date.
   * Sort in ascending order. For ToDoItems with no due date, list at the end of the list.
   *
   * @param todoList the todoList to sort
   * @return the list sorted by the due date
   */
  public List<ToDoItem> sortByDate(List<ToDoItem> todoList) {
    Collections.sort(todoList, (t1, t2) -> {
      if (t1.getDue() == null) return 1;
      if (t2.getDue() == null) return -1;
      return t1.getDue().compareTo(t2.getDue());
    });
    return todoList;
  }

  /**
   * Sort the todoList by priority. With 1 being the highest priority, 3 being the lowest priority.
   * Sort in ascending order. For ToDoItem with no priority, treated as lowest priority
   * and list at the end of the list.
   *
   * @param toDoItemList the to do item list
   * @return the list sorted by the priority
   */
  public List<ToDoItem> sortByPriority(List<ToDoItem> toDoItemList) {
    Collections.sort(toDoItemList, (t1, t2) -> {
      if (t1.getPriority() == null) return 1;
      if (t2.getPriority() == null) return -1;
      return t1.getPriority() - t2.getPriority();
    });
    return toDoItemList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToDoList toDoList = (ToDoList) o;
    return Objects.equals(todoList, toDoList.todoList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(todoList);
  }

  @Override
  public String toString() {
    return "ToDoList{" +
        "todoList=" + todoList +
        '}';
  }
}
