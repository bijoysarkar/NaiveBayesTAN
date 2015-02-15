package cs_760.naive_bayes_tan;

public class NumericalAttributeValue implements AttributeValue<Double> {

  public double value;

  public NumericalAttributeValue(double value) {
    this.value = value;
  }

  @Override
  public Double value() {
    return value;
  }

  @Override
  public String toString() {
    return "" + value;
  }

}
