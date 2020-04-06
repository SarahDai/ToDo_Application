package todotrackingsystem.model;

import java.time.LocalDate;
import todotrackingsystem.utils.InvalidArgumentException;

/**
 * The type ToDoItem, contains all information about a ToDoItem data structure.
 */
public class ToDoItem {
  private static final int MIN_PRIORITY = 1;
  private static final int MAX_PRIORITY = 3;

  private Integer id;
  private String text;
  private boolean completed;
  private LocalDate due;
  private Integer priority;
  private String category;

  /**
   * Instantiates a new ToDoItem.
   *
   * @param id        the id assigned to the ToDoItem
   * @param text      a description of the task to be done, represented as a String
   * @param completed indicates whether the task is completed or incomplete, represented as a boolean
   * @param due       the due date, represented as LocalDate
   * @param priority  the priority of the ToDoItem, must be between 1 and 3, represented as Integer
   * @param category  the category of the ToDoItem, represented as String
   */
  public ToDoItem(Integer id, String text, boolean completed, LocalDate due, Integer priority,
      String category) {
    this.id = id;
    this.text = text;
    this.completed = completed;
    this.due = due;
    this.priority = validatePriority(priority);
    this.category = category;
  }

  /**
   * Validate the given priority based on the requirements of the field.
   *
   * @param priority the input priority
   * @return the validated priority
   */
  private Integer validatePriority(Integer priority) {
    if (priority == null || (priority >= MIN_PRIORITY && priority <= MAX_PRIORITY)) {
      return priority;
    } else {
      throw new IndexOutOfBoundsException("The provided priority is not valid!");
    }
  }

  /**
   * Gets id of the ToDoItem.
   *
   * @return the id of ToDoItem
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * Gets the description of the task to be done,
   *
   * @return the text
   */
  public String getText() {
    return this.text;
  }

  /**
   * Gets whether the task is completed or incomplete.
   *
   * @return true if the task is completed, false otherwise.
   */
  public boolean isCompleted() {
    return this.completed;
  }

  /**
   * Gets the due date of the ToDoItem.
   *
   * @return the due date of the ToDoItem.
   */
  public LocalDate getDue() {
    return this.due;
  }

  /**
   * Gets the priority of the ToDoItem, must be between 1 and 3,
   *
   * @return the priority
   */
  public Integer getPriority() {
    return this.priority;
  }

  /**
   * Gets category.
   *
   * @return the category
   */
  public String getCategory() {
    return this.category;
  }

  /**
   * Updates the completion status of an individual ToDoItem.
   */
  public void completeToDo() {
    if (this.completed) {
      System.out.println(String.format("The todo item with id %s is already completed.", this.id));
      return;
    }
    this.completed = true;
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
