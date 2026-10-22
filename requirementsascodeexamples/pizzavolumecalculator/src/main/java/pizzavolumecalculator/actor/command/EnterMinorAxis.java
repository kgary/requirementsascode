package pizzavolumecalculator.actor.command;

public class EnterMinorAxis {
  private int minorAxis;

  public EnterMinorAxis(int minorAxis) {
    this.minorAxis = minorAxis;
  }

  public int getMinorAxis() {
    return minorAxis;
  }
}