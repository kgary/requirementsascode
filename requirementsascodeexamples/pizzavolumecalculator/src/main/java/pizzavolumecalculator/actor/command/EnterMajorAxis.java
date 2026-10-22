package pizzavolumecalculator.actor.command;

public class EnterMajorAxis {
  private int majorAxis;

  public EnterMajorAxis(int majorAxis) {
    this.majorAxis = majorAxis;
  }

  public int getMajorAxis() {
    return majorAxis;
  }
}