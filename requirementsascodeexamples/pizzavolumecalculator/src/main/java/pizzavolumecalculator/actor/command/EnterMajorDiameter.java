package pizzavolumecalculator.actor.command;

public class EnterMajorDiameter {
  private int majorDiameter;

  public EnterMajorDiameter(int majorDiameter) {
    this.majorDiameter = majorDiameter;
  }

  public int getMajorDiameter() {
    return majorDiameter;
  }
}