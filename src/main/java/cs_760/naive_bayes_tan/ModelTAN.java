package cs_760.naive_bayes_tan;

public class ModelTAN extends Model {

  private int[][][][][] jointCountsPerParent;

  public ModelTAN(Data training_data) {
    super(training_data);
  }

  @Override
  public int[] getParents() {

    jointCountsPerParent = calculateJointCountsPerParent();

    int no_of_attributes = jointCountsPerParent.length;
    int[] parentList = new int[no_of_attributes];


    Graph graph = new Graph(no_of_attributes);
    for (int i = 0; i < no_of_attributes; i++) {
      NominalAttribute nominalAttribute1 = attributeList[i];
      for (int j = i; j < no_of_attributes; j++) {
        if (i != j) {
          NominalAttribute nominalAttribute2 = attributeList[j];

          double sum = 0;
          for (int p = 0; p < nominalAttribute1.categoryCount(); p++) {
            for (int q = 0; q < nominalAttribute2.categoryCount(); q++) {
              for (int r = 0; r < classes.categoryCount(); r++) {
                sum =
                    sum
                        + (((double) jointCountsPerParent[i][j][p][q][r] + 1) / (trainingCount + nominalAttribute1
                            .categoryCount()
                            * nominalAttribute2.categoryCount()
                            * classes.categoryCount()))
                        * Math
                            .log((((double) jointCountsPerParent[i][j][p][q][r] + 1) / (classCounts[r] + nominalAttribute1
                                .categoryCount() * nominalAttribute2.categoryCount()))
                                / ((((double) countsPerParent[i][p][r] + 1) / (classCounts[r] + nominalAttribute1
                                    .categoryCount())) * (((double) countsPerParent[j][q][r] + 1) / (classCounts[r] + nominalAttribute2
                                    .categoryCount())))) / Math.log(2);
              }
            }

          }
          graph.setWeight(i, j, sum);
        } else {
          graph.setWeight(i, j, -1);
        }
      }
    }

    parentList = graph.maximumSpanningTree();
    //System.out.println(Arrays.toString(parentList));
    //System.out.println(graph);
    return parentList;
  }

  @Override
  public double p_i_c(int attributeIndex, int classValue, Instance instance) {
    int parentIndex = parentList[attributeIndex];
    if (parentIndex == attributeList.length) {
      //Only class variable is parent
      return (double) (countsPerParent[attributeIndex][(int) instance.getAttributeValue(
          attributeIndex).value()][classValue] + 1)
          / (classCounts[classValue] + attributeList[attributeIndex].categoryCount());
    } else {
      //Two parent case
      return (double) (jointCountsPerParent[attributeIndex][parentIndex][(int) instance
          .getAttributeValue(attributeIndex).value()][(int) instance.getAttributeValue(parentIndex)
          .value()][classValue] + 1)
          / (countsPerParent[parentIndex][(int) instance.getAttributeValue(parentIndex).value()][classValue] + attributeList[attributeIndex]
              .categoryCount());
    }
  }

}
