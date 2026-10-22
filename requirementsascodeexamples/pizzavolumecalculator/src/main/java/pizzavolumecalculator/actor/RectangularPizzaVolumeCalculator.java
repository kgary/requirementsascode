package pizzavolumecalculator.actor;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.requirementsascode.AbstractActor;
import org.requirementsascode.Condition;
import org.requirementsascode.Model;

import pizzavolumecalculator.actor.command.CalculateVolume;
import pizzavolumecalculator.actor.command.EnterHeight;
import pizzavolumecalculator.actor.command.EnterLength;
import pizzavolumecalculator.actor.command.EnterWidth;

public class RectangularPizzaVolumeCalculator extends AbstractActor {
  private int length;
  private int width;
  private int height;

  private Consumer<EnterLength> saveLength;
  private Consumer<EnterWidth> saveWidth;
  private Consumer<EnterHeight> saveHeight;
  private Supplier<Double> calculateVolume;
  private Runnable throwIllegalLengthException;
  private Runnable throwIllegalWidthException;
  private Runnable throwIllegalHeightException;
  private Condition isNegativeLength;
  private Condition isNegativeWidth;
  private Condition isNegativeHeight;

  public RectangularPizzaVolumeCalculator() {
    this.saveLength = this::saveLength;
    this.saveWidth = this::saveWidth;
    this.saveHeight = this::saveHeight;
    this.calculateVolume = this::calculateVolume;
    this.throwIllegalLengthException = this::throwIllegalLengthException;
    this.throwIllegalWidthException = this::throwIllegalWidthException;
    this.throwIllegalHeightException = this::throwIllegalHeightException;
    this.isNegativeLength = this::isNegativeLength;
    this.isNegativeWidth = this::isNegativeWidth;
    this.isNegativeHeight = this::isNegativeHeight;
  }

  @Override
  protected Model behavior() {
    Model model = Model.builder()
      .useCase("Calculate Rectangular Pizza Volume").basicFlow()
        .step("S1").user(EnterLength.class).system(saveLength)
        .step("S2").user(EnterWidth.class).system(saveWidth)
        .step("S3").user(EnterHeight.class).system(saveHeight)
        .step("S4").user(CalculateVolume.class).systemPublish(calculateVolume)
        .step("S5").continuesAt("S1")

      .flow("Negative length").after("S1").condition(isNegativeLength)
        .step("S1a_1").system(throwIllegalLengthException)

      .flow("Negative width").after("S2").condition(isNegativeWidth)
        .step("S2a_1").system(throwIllegalWidthException)

      .flow("Negative height").after("S3").condition(isNegativeHeight)
        .step("S3a_1").system(throwIllegalHeightException)

      .build();

    return model;
  }

  // system reactions
  private void saveLength(EnterLength enterLength) {
    this.length = enterLength.getLength();
  }

  private void saveWidth(EnterWidth enterWidth) {
    this.width = enterWidth.getWidth();
  }

  private void saveHeight(EnterHeight enterHeight) {
    this.height = enterHeight.getHeight();
  }

  private Double calculateVolume() {
    return (double) length * width * height;
  }

  private void throwIllegalLengthException() {
    throw new IllegalArgumentException("Please specify a non-negative length!");
  }

  private void throwIllegalWidthException() {
    throw new IllegalArgumentException("Please specify a non-negative width!");
  }

  private void throwIllegalHeightException() {
    throw new IllegalArgumentException("Please specify a non-negative height!");
  }

  // conditions
  private boolean isNegativeLength() {
    return length < 0;
  }

  private boolean isNegativeWidth() {
    return width < 0;
  }

  private boolean isNegativeHeight() {
    return height < 0;
  }
}
