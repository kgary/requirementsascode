package pizzavolumecalculator.actor.command;

public class EnterSemiMinorAxis {
  private int semiMinorAxis;

  public EnterSemiMinorAxis(int semiMinorAxis) {
    this.semiMinorAxis = semiMinorAxis;
  }

  public int getSemiMinorAxis() {
    return semiMinorAxis;
  }
}