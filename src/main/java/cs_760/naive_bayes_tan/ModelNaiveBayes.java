package cs_760.naive_bayes_tan;

public class ModelNaiveBayes extends Model {

  public ModelNaiveBayes(Data training_data) {
    super(training_data);
  }

  @Override
  public int[] getParents(Instance[] instanceList) {
    int[] parentList = new int[attributeList.length];
    for (int i = 0; i < attributeList.length; i++) {
      parentList[i] = attributeList.length;
    }
    return parentList;
  }

  @Override
  public double p_i_c(int attributeIndex, int classValue, Instance instance) {
    return (double) (countsPerParent[attributeIndex][(int) instance.getAttributeValue(
        attributeIndex).value()][classValue] + 1)
        / (classCounts[classValue] + attributeList[attributeIndex].categoryCount());
  }

}
