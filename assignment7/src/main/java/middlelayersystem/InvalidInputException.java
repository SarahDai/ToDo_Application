package middlelayersystem;

public class InvalidInputException extends Exception {
  /**
   * Constructs a new exception with the specified detail message.  The
   * cause is not initialized, and may subsequently be initialized by
   * a call to {@link #initCause}.
   *
   * @param msg the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public InvalidInputException(String msg){
    super(msg);
  }

}
