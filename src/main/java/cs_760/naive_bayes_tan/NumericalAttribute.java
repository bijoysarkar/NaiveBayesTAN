package cs_760.naive_bayes_tan;

public class NumericalAttribute implements Attribute {

  private String name;
  private AttributeType type;

  public NumericalAttribute(String name) {
    this.name = name;
    type = AttributeType.NUMERICAL;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public AttributeType type() {
    return type;
  }

}
