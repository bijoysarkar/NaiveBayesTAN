package cs_760.naive_bayes_tan;

public class ModelTAN extends Model {

  public ModelTAN(Data training_data) {
    super(training_data);
  }

  public int[] getParents(NominalAttribute classes, NominalAttribute[] attributeList,
      Instance[] instanceList) {
    int[] parentList = new int[attributeList.length];
    //TODO
    return parentList;
  }

}
