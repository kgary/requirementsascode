package pizzavolumecalculator.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import pizzavolumecalculator.actor.OvalPizzaVolumeCalculator;
import pizzavolumecalculator.actor.command.CalculateVolume;
import pizzavolumecalculator.actor.command.EnterHeight;
import pizzavolumecalculator.actor.command.EnterLength;
import pizzavolumecalculator.actor.command.EnterMajorAxis;
import pizzavolumecalculator.actor.command.EnterMajorDiameter;
import pizzavolumecalculator.actor.command.EnterMajorRadius;
import pizzavolumecalculator.actor.command.EnterMinorAxis;
import pizzavolumecalculator.actor.command.EnterMinorDiameter;
import pizzavolumecalculator.actor.command.EnterMinorRadius;
import pizzavolumecalculator.actor.command.EnterRadius1;
import pizzavolumecalculator.actor.command.EnterRadius2;
import pizzavolumecalculator.actor.command.EnterSemiMajorAxis;
import pizzavolumecalculator.actor.command.EnterSemiMinorAxis;
import pizzavolumecalculator.actor.command.EnterWidth;

public class OvalPizzaVolumeCalculatorConsole {
  private Scanner scanner;
  private final String format;

  private OvalPizzaVolumeCalculatorConsole(String format) {
    this.scanner = new Scanner(System.in);
    this.format = format;
  }

  private void start(Integer d1, Integer d2, Integer h) {
    OvalPizzaVolumeCalculator calculator = new OvalPizzaVolumeCalculator();

    int first = (d1 != null) ? d1 : promptInt(promptForFirst());
    dispatchFirst(calculator, first);

    int second = (d2 != null) ? d2 : promptInt(promptForSecond());
    dispatchSecond(calculator, second);

    int height = (h != null) ? h : promptInt("Please enter the height: ");
    calculator.reactTo(new EnterHeight(height));

    Optional<Double> volume = calculator.reactTo(new CalculateVolume());

    System.out.println("The volume is:  " + volume.get());
  }

  public static void main(String[] args) {
    String format = "radius";
    List<Integer> nums = new ArrayList<>();
    for (String a : args) {
      if (a.startsWith("--format=")) {
        format = a.substring("--format=".length());
      } else {
        try {
          nums.add(Integer.parseInt(a));
        } catch (NumberFormatException e) {
          format = a;
        }
      }
    }
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals("--format")) {
        format = args[i + 1];
      }
    }

    Integer d1 = nums.size() > 0 ? nums.get(0) : null;
    Integer d2 = nums.size() > 1 ? nums.get(1) : null;
    Integer hh = nums.size() > 2 ? nums.get(2) : null;

    OvalPizzaVolumeCalculatorConsole console = new OvalPizzaVolumeCalculatorConsole(normalize(format));
    console.start(d1, d2, hh);
  }

  private static String normalize(String f) {
    f = f.toLowerCase();
    if (f.equals("r") || f.equals("rad") || f.equals("radius")) return "radius";
    if (f.equals("axis") || f.equals("axes")) return "axis";
    if (f.equals("semi") || f.equals("semiaxis") || f.equals("semi-axis")) return "semi";
    if (f.equals("diam") || f.equals("diameter")) return "diameter";
    if (f.equals("r1r2") || f.equals("radius12") || f.equals("radii")) return "radius12";
    if (f.equals("len") || f.equals("length") || f.equals("rect") || f.equals("lw") || f.equals("width")) return "lengthwidth";
    return f;
  }

  private int promptInt(String prompt) {
    System.out.print(prompt);
    return scanner.nextInt();
  }

  private String promptForFirst() {
    switch (format) {
      case "axis": return "Please enter the major axis: ";
      case "semi": return "Please enter the semi major axis: ";
      case "diameter": return "Please enter the major diameter: ";
      case "radius12": return "Please enter radius1: ";
      case "lengthwidth": return "Please enter the length: ";
      default: return "Please enter the major radius: ";
    }
  }

  private String promptForSecond() {
    switch (format) {
      case "axis": return "Please enter the minor axis: ";
      case "semi": return "Please enter the semi minor axis: ";
      case "diameter": return "Please enter the minor diameter: ";
      case "radius12": return "Please enter radius2: ";
      case "lengthwidth": return "Please enter the width: ";
      default: return "Please enter the minor radius: ";
    }
  }

  private void dispatchFirst(OvalPizzaVolumeCalculator c, int v) {
    switch (format) {
      case "axis": c.reactTo(new EnterMajorAxis(v)); break;
      case "semi": c.reactTo(new EnterSemiMajorAxis(v)); break;
      case "diameter": c.reactTo(new EnterMajorDiameter(v)); break;
      case "radius12": c.reactTo(new EnterRadius1(v)); break;
      case "lengthwidth": c.reactTo(new EnterLength(v)); break;
      default: c.reactTo(new EnterMajorRadius(v)); break;
    }
  }

  private void dispatchSecond(OvalPizzaVolumeCalculator c, int v) {
    switch (format) {
      case "axis": c.reactTo(new EnterMinorAxis(v)); break;
      case "semi": c.reactTo(new EnterSemiMinorAxis(v)); break;
      case "diameter": c.reactTo(new EnterMinorDiameter(v)); break;
      case "radius12": c.reactTo(new EnterRadius2(v)); break;
      case "lengthwidth": c.reactTo(new EnterWidth(v)); break;
      default: c.reactTo(new EnterMinorRadius(v)); break;
    }
  }
}