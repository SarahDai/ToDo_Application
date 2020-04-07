package todotrackingsystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ToDoList {
  private LinkedHashMap<Integer, ToDoItem> todoList;
  private Integer currentMaxID;
  private Set<String> categoryList;

  public ToDoList() {
    this.todoList = new LinkedHashMap<>();
    this.currentMaxID = 0;
    this.categoryList = new HashSet<>();
  }

  public void updateCurrentMaxID(int id) {
    this.currentMaxID = Math.max(id, this.currentMaxID);
  }

  public Integer getCurrentMaxID() {
    return this.currentMaxID;
  }

  public List<ToDoItem> getTodoList() {
    return new ArrayList<ToDoItem>(this.todoList.values());
  }

  public void addExistingToDo(int id, ToDoItem todo) {
    this.todoList.put(id, todo);
  }

  public void addNewToDo(ToDoItem todo) {
    this.todoList.put(++this.currentMaxID, todo);
  }

  public boolean containsID(Integer id) {
    return this.todoList.containsKey(id);
  }

  public void addCategory(String category) {
    this.categoryList.add(category);
  }

  public boolean containsCategory(String category) {
    return this.categoryList.contains(category);
  }

  public void completeToDo(Integer id) {
    this.todoList.get(id).completeToDo();
  }

  public List<ToDoItem> filterIncomplete(List<ToDoItem> toDoItemList) {
    List<ToDoItem> incompleteList = new ArrayList<>();
    for (ToDoItem todo: toDoItemList) {
      if (!todo.isCompleted()) {
        incompleteList.add(todo);
      }
    }
    return incompleteList;
  }

  public List<ToDoItem> filterCategory(List<ToDoItem> toDoItemList, String category) {
    List<ToDoItem> categoryToDoItemList = new ArrayList<>();
    for (ToDoItem todo: toDoItemList) {
      if (todo.getCategory().equals(category)) {
        categoryToDoItemList.add(todo);
      }
    }
    return categoryToDoItemList;
  }

  public List<ToDoItem> sortByDate(List<ToDoItem> todoList) {
    Collections.sort(todoList, (t1, t2) -> {
      if (t1.getDue() == null) return 1;
      if (t2.getDue() == null) return -1;
      return t1.getDue().compareTo(t2.getDue());
    });
    return todoList;
  }

  public List<ToDoItem> sortByPriority(List<ToDoItem> toDoItemList) {
    Collections.sort(toDoItemList, (t1, t2) -> {
      if (t1.getPriority() == null) return 1;
      if (t2.getPriority() == null) return -1;
      return t1.getPriority() - t2.getPriority();
    });
    return toDoItemList;
  }
}
