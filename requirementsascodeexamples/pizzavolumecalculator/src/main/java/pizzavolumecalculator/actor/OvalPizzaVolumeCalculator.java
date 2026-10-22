package pizzavolumecalculator.actor;

import static java.lang.Math.PI;

import org.requirementsascode.AbstractActor;
import org.requirementsascode.Model;

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

public class OvalPizzaVolumeCalculator extends AbstractActor {
  private int majorRadius;
  private int minorRadius;
  private int majorAxis;
  private int minorAxis;
  private int semiMajorAxis;
  private int semiMinorAxis;
  private int majorDiameter;
  private int minorDiameter;
  private int radius1;
  private int radius2;
  private int length;
  private int width;
  private int height;

  private double dim1;
  private double dim2;
  private boolean fullDimensions;

  public OvalPizzaVolumeCalculator() {
  }
  @Override
  protected Model behavior() {
    Model model = Model.builder()
      .useCase("Calculate Oval Pizza Volume - major/minor radius").basicFlow()
        .step("S1").user(EnterMajorRadius.class).system(this::saveMajorRadius)
        .step("S2").user(EnterMinorRadius.class).system(this::saveMinorRadius)
        .step("S3").user(EnterHeight.class).system(this::saveHeight)
        .step("S4").user(CalculateVolume.class).systemPublish(this::calculateVolume)
        .step("S5").continuesAt("S1")
      .flow("Negative major radius").after("S1").condition(this::isNegativeMajorRadius)
        .step("S1a_1").system(this::throwIllegalMajorRadiusException)
      .flow("Negative minor radius").after("S2").condition(this::isNegativeMinorRadius)
        .step("S2a_1").system(this::throwIllegalMinorRadiusException)
      .flow("Negative height").after("S3").condition(this::isNegativeHeight)
        .step("S3a_1").system(this::throwIllegalHeightException)

      .useCase("Calculate Oval Pizza Volume - major/minor axis").basicFlow()
        .step("S1").user(EnterMajorAxis.class).system(this::saveMajorAxis)
        .step("S2").user(EnterMinorAxis.class).system(this::saveMinorAxis)
        .step("S3").user(EnterHeight.class).system(this::saveHeight)
        .step("S4").user(CalculateVolume.class).systemPublish(this::calculateVolume)
        .step("S5").continuesAt("S1")
      .flow("Negative major axis").after("S1").condition(this::isNegativeMajorAxis)
        .step("S1a_1").system(this::throwIllegalMajorAxisException)
      .flow("Negative minor axis").after("S2").condition(this::isNegativeMinorAxis)
        .step("S2a_1").system(this::throwIllegalMinorAxisException)
      .flow("Negative height").after("S3").condition(this::isNegativeHeight)
        .step("S3a_1").system(this::throwIllegalHeightException)

      .useCase("Calculate Oval Pizza Volume - semi major/minor axis").basicFlow()
        .step("S1").user(EnterSemiMajorAxis.class).system(this::saveSemiMajorAxis)
        .step("S2").user(EnterSemiMinorAxis.class).system(this::saveSemiMinorAxis)
        .step("S3").user(EnterHeight.class).system(this::saveHeight)
        .step("S4").user(CalculateVolume.class).systemPublish(this::calculateVolume)
        .step("S5").continuesAt("S1")
      .flow("Negative semi major axis").after("S1").condition(this::isNegativeSemiMajorAxis)
        .step("S1a_1").system(this::throwIllegalSemiMajorAxisException)
      .flow("Negative semi minor axis").after("S2").condition(this::isNegativeSemiMinorAxis)
        .step("S2a_1").system(this::throwIllegalSemiMinorAxisException)
      .flow("Negative height").after("S3").condition(this::isNegativeHeight)
        .step("S3a_1").system(this::throwIllegalHeightException)

      .useCase("Calculate Oval Pizza Volume - major/minor diameter").basicFlow()
        .step("S1").user(EnterMajorDiameter.class).system(this::saveMajorDiameter)
        .step("S2").user(EnterMinorDiameter.class).system(this::saveMinorDiameter)
        .step("S3").user(EnterHeight.class).system(this::saveHeight)
        .step("S4").user(CalculateVolume.class).systemPublish(this::calculateVolume)
        .step("S5").continuesAt("S1")
      .flow("Negative major diameter").after("S1").condition(this::isNegativeMajorDiameter)
        .step("S1a_1").system(this::throwIllegalMajorDiameterException)
      .flow("Negative minor diameter").after("S2").condition(this::isNegativeMinorDiameter)
        .step("S2a_1").system(this::throwIllegalMinorDiameterException)
      .flow("Negative height").after("S3").condition(this::isNegativeHeight)
        .step("S3a_1").system(this::throwIllegalHeightException)

      .useCase("Calculate Oval Pizza Volume - radius1/radius2").basicFlow()
        .step("S1").user(EnterRadius1.class).system(this::saveRadius1)
        .step("S2").user(EnterRadius2.class).system(this::saveRadius2)
        .step("S3").user(EnterHeight.class).system(this::saveHeight)
        .step("S4").user(CalculateVolume.class).systemPublish(this::calculateVolume)
        .step("S5").continuesAt("S1")
      .flow("Negative radius1").after("S1").condition(this::isNegativeRadius1)
        .step("S1a_1").system(this::throwIllegalRadius1Exception)
      .flow("Negative radius2").after("S2").condition(this::isNegativeRadius2)
        .step("S2a_1").system(this::throwIllegalRadius2Exception)
      .flow("Negative height").after("S3").condition(this::isNegativeHeight)
        .step("S3a_1").system(this::throwIllegalHeightException)

      .useCase("Calculate Oval Pizza Volume - length/width").basicFlow()
        .step("S1").user(EnterLength.class).system(this::saveLength)
        .step("S2").user(EnterWidth.class).system(this::saveWidth)
        .step("S3").user(EnterHeight.class).system(this::saveHeight)
        .step("S4").user(CalculateVolume.class).systemPublish(this::calculateVolume)
        .step("S5").continuesAt("S1")
      .flow("Negative length").after("S1").condition(this::isNegativeLength)
        .step("S1a_1").system(this::throwIllegalLengthException)
      .flow("Negative width").after("S2").condition(this::isNegativeWidth)
        .step("S2a_1").system(this::throwIllegalWidthException)
      .flow("Negative height").after("S3").condition(this::isNegativeHeight)
        .step("S3a_1").system(this::throwIllegalHeightException)

      .build();

    return model;
  }
  // system reactions
  private void saveMajorRadius(EnterMajorRadius e) {
    this.majorRadius = e.getMajorRadius();
    this.dim1 = majorRadius;
    this.fullDimensions = false;
  }
  private void saveMinorRadius(EnterMinorRadius e) {
    this.minorRadius = e.getMinorRadius();
    this.dim2 = minorRadius;
    this.fullDimensions = false;
  }
  private void saveMajorAxis(EnterMajorAxis e) {
    this.majorAxis = e.getMajorAxis();
    this.dim1 = majorAxis;
    this.fullDimensions = true;
  }
  private void saveMinorAxis(EnterMinorAxis e) {
    this.minorAxis = e.getMinorAxis();
    this.dim2 = minorAxis;
    this.fullDimensions = true;
  }
  private void saveSemiMajorAxis(EnterSemiMajorAxis e) {
    this.semiMajorAxis = e.getSemiMajorAxis();
    this.dim1 = semiMajorAxis;
    this.fullDimensions = false;
  }
  private void saveSemiMinorAxis(EnterSemiMinorAxis e) {
    this.semiMinorAxis = e.getSemiMinorAxis();
    this.dim2 = semiMinorAxis;
    this.fullDimensions = false;
  }
  private void saveMajorDiameter(EnterMajorDiameter e) {
    this.majorDiameter = e.getMajorDiameter();
    this.dim1 = majorDiameter;
    this.fullDimensions = true;
  }
  private void saveMinorDiameter(EnterMinorDiameter e) {
    this.minorDiameter = e.getMinorDiameter();
    this.dim2 = minorDiameter;
    this.fullDimensions = true;
  }
  private void saveRadius1(EnterRadius1 e) {
    this.radius1 = e.getRadius1();
    this.dim1 = radius1;
    this.fullDimensions = false;
  }
  private void saveRadius2(EnterRadius2 e) {
    this.radius2 = e.getRadius2();
    this.dim2 = radius2;
    this.fullDimensions = false;
  }
  private void saveLength(EnterLength e) {
    this.length = e.getLength();
    this.dim1 = length;
    this.fullDimensions = true;
  }
  private void saveWidth(EnterWidth e) {
    this.width = e.getWidth();
    this.dim2 = width;
    this.fullDimensions = true;
  }
  private void saveHeight(EnterHeight e) {
    this.height = e.getHeight();
  }

  private Double calculateVolume() {
    double factor = fullDimensions ? 4.0 : 1.0;
    return PI * dim1 * dim2 * height / factor;
  }

  private void throwIllegalMajorRadiusException() {
    throw new IllegalArgumentException("Please specify a non-negative major radius!");
  }
  private void throwIllegalMinorRadiusException() {
    throw new IllegalArgumentException("Please specify a non-negative minor radius!");
  }
  private void throwIllegalMajorAxisException() {
    throw new IllegalArgumentException("Please specify a non-negative major axis!");
  }
  private void throwIllegalMinorAxisException() {
    throw new IllegalArgumentException("Please specify a non-negative minor axis!");
  }
  private void throwIllegalSemiMajorAxisException() {
    throw new IllegalArgumentException("Please specify a non-negative semi major axis!");
  }
  private void throwIllegalSemiMinorAxisException() {
    throw new IllegalArgumentException("Please specify a non-negative semi minor axis!");
  }
  private void throwIllegalMajorDiameterException() {
    throw new IllegalArgumentException("Please specify a non-negative major diameter!");
  }
  private void throwIllegalMinorDiameterException() {
    throw new IllegalArgumentException("Please specify a non-negative minor diameter!");
  }
  private void throwIllegalRadius1Exception() {
    throw new IllegalArgumentException("Please specify a non-negative radius1!");
  }
  private void throwIllegalRadius2Exception() {
    throw new IllegalArgumentException("Please specify a non-negative radius2!");
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

  private boolean isNegativeMajorRadius() { return majorRadius < 0; }
  private boolean isNegativeMinorRadius() { return minorRadius < 0; }
  private boolean isNegativeMajorAxis() { return majorAxis < 0; }
  private boolean isNegativeMinorAxis() { return minorAxis < 0; }
  private boolean isNegativeSemiMajorAxis() { return semiMajorAxis < 0; }
  private boolean isNegativeSemiMinorAxis() { return semiMinorAxis < 0; }
  private boolean isNegativeMajorDiameter() { return majorDiameter < 0; }
  private boolean isNegativeMinorDiameter() { return minorDiameter < 0; }
  private boolean isNegativeRadius1() { return radius1 < 0; }
  private boolean isNegativeRadius2() { return radius2 < 0; }
  private boolean isNegativeLength() { return length < 0; }
  private boolean isNegativeWidth() { return width < 0; }
  private boolean isNegativeHeight() { return height < 0; }
}