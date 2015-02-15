package cs_760.naive_bayes_tan;

public class ModelNaiveBayes extends Model {

  public ModelNaiveBayes(Data training_data) {
    super(training_data);
  }

  public int[] getParents(NominalAttribute classes, NominalAttribute[] attributeList,
      Instance[] instanceList) {
    int[] parentList = new int[attributeList.length];
    for (int i = 0; i < attributeList.length; i++) {
      parentList[i] = parentList.length - 1;
    }
    return parentList;
  }

}
