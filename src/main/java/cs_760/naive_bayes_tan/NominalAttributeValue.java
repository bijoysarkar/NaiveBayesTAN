package cs_760.naive_bayes_tan;

public class NominalAttributeValue implements AttributeValue<Integer> {

  public int value;

  public NominalAttributeValue(int value) {
    this.value = value;
  }

  @Override
  public Integer value() {
    return value;
  }

  @Override
  public String toString() {
    return "" + value;
  }

}
