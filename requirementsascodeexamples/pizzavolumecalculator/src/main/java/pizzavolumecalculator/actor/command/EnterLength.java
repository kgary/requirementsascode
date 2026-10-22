package pizzavolumecalculator.actor.command;

public class EnterLength {
  private final int length;

  public EnterLength(int length) {
    this.length = length;
  }

  public int getLength() {
    return length;
  }
}
