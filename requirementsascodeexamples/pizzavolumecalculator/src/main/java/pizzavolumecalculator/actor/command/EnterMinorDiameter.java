package pizzavolumecalculator.actor.command;

public class EnterMinorDiameter {
  private int minorDiameter;

  public EnterMinorDiameter(int minorDiameter) {
    this.minorDiameter = minorDiameter;
  }

  public int getMinorDiameter() {
    return minorDiameter;
  }
}