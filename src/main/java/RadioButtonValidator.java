public class RadioButton implements IValidator<Boolean>{

  @Override
  public boolean isValid(Boolean input) {
    return input != null;
  }
}
