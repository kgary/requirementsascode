package pizzavolumecalculator.actor.command;

public class EnterSemiMajorAxis {
  private int semiMajorAxis;

  public EnterSemiMajorAxis(int semiMajorAxis) {
    this.semiMajorAxis = semiMajorAxis;
  }

  public int getSemiMajorAxis() {
    return semiMajorAxis;
  }
}