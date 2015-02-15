package cs_760.naive_bayes_tan;

import java.util.Arrays;

public class ModelTAN extends Model {

  public ModelTAN(Data training_data) {
    super(training_data);
  }

  public int[] getParents(NominalAttribute classes, NominalAttribute[] attributeList,
      Instance[] instanceList) {
    int[] parentList = new int[attributeList.length];

    int no_of_attributes = attributeList.length - 1;
    int[] classCounts = new int[classes.categoryCount()];
    int[][][] countsPerParent = new int[no_of_attributes][][];
    int[][][][][] jointCountsPerParent = new int[no_of_attributes][no_of_attributes][][][];

    for (int i = 0; i < no_of_attributes; i++) {
      countsPerParent[i] = new int[attributeList[i].categoryCount()][classes.categoryCount()];
      for (int j = 0; j < no_of_attributes; j++) {
        jointCountsPerParent[i][j] =
            new int[attributeList[i].categoryCount()][attributeList[j].categoryCount()][classes
                .categoryCount()];
      }
    }

    for (Instance instance : instanceList) {
      classCounts[instance.getInstanceClass()]++;
      for (int i = 0; i < no_of_attributes; i++) {
        countsPerParent[i][(int) instance.getAttributeValue(i).value()][instance.getInstanceClass()]++;
        for (int j = 0; j < no_of_attributes; j++) {
          jointCountsPerParent[i][j][(int) instance.getAttributeValue(i).value()][(int) instance
              .getAttributeValue(j).value()][instance.getInstanceClass()]++;
        }
      }
    }

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
                        + (((double) jointCountsPerParent[i][j][p][q][r] + 1) / (instanceList.length + nominalAttribute1
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
    System.out.println(Arrays.toString(parentList));
    return parentList;
  }

}
