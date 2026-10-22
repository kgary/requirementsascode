package pizzavolumecalculator.actor.command;

public class EnterMinorRadius {
  private int minorRadius;

  public EnterMinorRadius(int minorRadius) {
    this.minorRadius = minorRadius;
  }

  public int getMinorRadius() {
    return minorRadius;
  }
}