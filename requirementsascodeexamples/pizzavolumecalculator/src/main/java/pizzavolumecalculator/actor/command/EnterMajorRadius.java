package pizzavolumecalculator.actor.command;

public class EnterMajorRadius {
  private int majorRadius;

  public EnterMajorRadius(int majorRadius) {
    this.majorRadius = majorRadius;
  }

  public int getMajorRadius() {
    return majorRadius;
  }
}