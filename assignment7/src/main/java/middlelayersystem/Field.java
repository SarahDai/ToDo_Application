package middlelayersystem;

import java.util.Objects;

/**
 * A generic Fields class accepting different fields from users
 * @param <T> a generic type
 */
public class Field<T>{
  private String label;
  private T value;
  private boolean required;
  private Validator validator;

  /**
   * Constructor of the class
   * @param label The String label associated with the form fields
   * @param value The input captured by the GUI.It’s data type will be either String for text fields
   * or Boolean for fields
   * @param required A boolean indicating whether a particular field must be completed before
   * the form can be submitted
   * @param validator a​ Validator​ type that will perform input validation
   */
  public Field(String label, String value, boolean required, Validator validator) {
    this.label = label;
    this.value = null;
    this.required = required;
    this.validator = validator;
  }

  /**
   * Getter for the label associated with the form fields
   * @return the label associated with the form fields
   */
  public String getLabel() {
    return this.label;
  }

  /**
   * Getter for Value input captured by the GUI. It’s data type will be either String for text
   * fields or Boolean for fields
   * @return the value input captured by the GUI. It’s data type will be either String for text
   * fields or Boolean for fields
   */
  public T getValue() {
    return this.value;
  }

  /**
   * Getter for isRequired indicating whether a particular field must be completed before
   * the form can be submitted
   * @return isRequired indicating whether a particular field must be completed before
   * the form can be submitted
   */
  public boolean isRequired() {
    return this.required;
  }

  /**
   * Getter for validator that will perform input validation
   * @return validator that will perform input validation
   */
  public Validator getValidator() {
    return this.validator;
  }

  /**
   * Helper method that update the fields' input value. If it's not valid, it'll throw an exception
   * and will not update the value
   * @param input input value
   * @throws InvalidInputException if the input is not valid
   */
  public void updateValue(T input)throws InvalidInputException {
    if(this.validator.isValid(input)){
      this.value = input;
    }
    throw new InvalidInputException("Input is invalid!");
  }

  /**
   * Helper method that for client code to determine if a form has been filled out and is ready to
   * submit
   * @return true if it's filled and valid, false otherwise
   */
  public boolean isFilled(){
    return (this.required && this.validator.isValid(this.value)) || !this.required;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Field)) {
      return false;
    }
    Field<?> field = (Field<?>) o;
    return required == field.required &&
        Objects.equals(label, field.label) &&
        Objects.equals(value, field.value) &&
        Objects.equals(validator, field.validator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, value, required, validator);
  }

  @Override
  public String toString() {
    return "Fields{" +
        "label='" + label + '\'' +
        ", value=" + value +
        ", required=" + required +
        ", validator=" + validator +
        '}';
  }
}
