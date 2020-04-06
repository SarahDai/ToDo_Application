package todotrackingsystem.model;

import java.time.LocalDate;

public class ToDoItem {

  private Integer id;
  private String text;
  private boolean completed;
  private LocalDate due;
  private Integer priority;
  private String category;

  public ToDoItem(Integer id, String text, boolean completed, LocalDate due, Integer priority,
      String category) {
    this.id = id;
    this.text = text;
    this.completed = completed;
    this.due = due;
    this.priority = priority;
    this.category = category;
  }

  public Integer getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public boolean isCompleted() {
    return completed;
  }

  public LocalDate getDue() {
    return due;
  }

  public Integer getPriority() {
    return priority;
  }

  public String getCategory() {
    return category;
  }

  // TODO: change parameter
  public void completeToDo(boolean completed) {
    this.completed = completed;
  }

  @Override
  public String toString() {
    return "\"" + id +
        "\",\"" + text + "\",\"" +
        completed +
        "\",\"" + (due == null? "?" : String.format("%s/%s/%s", due.getMonthValue(), due.getDayOfMonth(), due.getYear())) +
        "\",\"" + (priority == null? "?" : priority) +
        "\",\"" + (category == null? "?" : category)
        + "\"\n";
  }
}
