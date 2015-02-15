package cs_760.naive_bayes_tan;

public abstract class Model {

  private NominalAttribute classes;
  private NominalAttribute[] attributeList;
  private Instance[] instanceList;
  private int[] parentList;
  private int trainingCount;
  private int[] classCounts;
  private int[][][] countsPerParent;

  public Model(Data training_data) {

    int no_of_attributes = training_data.getAttributeList().length;

    classes = training_data.getClasses();
    attributeList = new NominalAttribute[no_of_attributes];
    instanceList = training_data.getInstanceList();

    // Since all discrete values
    for (int i = 0; i < no_of_attributes; i++) {
      attributeList[i] = (NominalAttribute) training_data.getAttributeList()[i];
    }

    parentList = getParents(classes, attributeList, instanceList);

    trainingCount = training_data.getInstanceList().length;
    classCounts = calculateClassCounts();
    countsPerParent = calculateCountsPerParent();
  }


  public int[] calculateClassCounts() {
    int[] countsTable = new int[classes.categoryCount()];
    for (Instance instance : instanceList)
      countsTable[instance.getInstanceClass()]++;
    return countsTable;
  }

  public int[][][] calculateCountsPerParent() {
    int no_of_attributes = attributeList.length;
    int[][][] countsTable = new int[no_of_attributes][][];
    for (int i = 0; i < no_of_attributes; i++)
      countsTable[i] = new int[attributeList[i].categoryCount()][classes.categoryCount()];
    for (Instance instance : instanceList) {
      for (int i = 0; i < no_of_attributes; i++) {
        countsTable[i][(int) instance.getAttributeValue(i).value()][instance.getInstanceClass()]++;
      }
    }
    return countsTable;
  }

  public int[][][][][] calculateJointCountsPerParent() {
    int no_of_attributes = attributeList.length;
    int[][][][][] countsTable = new int[no_of_attributes][no_of_attributes][][][];
    for (int i = 0; i < no_of_attributes; i++) {
      for (int j = 0; j < no_of_attributes; j++) {
        countsTable[i][j] =
            new int[attributeList[i].categoryCount()][attributeList[j].categoryCount()][classes
                .categoryCount()];
      }
    }
    for (Instance instance : instanceList) {
      for (int i = 0; i < no_of_attributes; i++) {
        for (int j = 0; j < no_of_attributes; j++) {
          countsTable[i][j][(int) instance.getAttributeValue(i).value()][(int) instance
              .getAttributeValue(j).value()][instance.getInstanceClass()]++;
        }
      }
    }
    return countsTable;
  }

  public abstract int[] getParents(NominalAttribute classes, NominalAttribute[] attributeList,
      Instance[] instanceList);

  public double p_c(int classValue) {
    return ((double) getclassCounts()[classValue] + 1)
        / (getTrainingCount() + getClasses().categoryCount());
  }

  public double p_i_c(int attributeIndex, int classValue, Instance instance) {
    return (double) (getCountsPerParent()[attributeIndex][(int) instance.getAttributeValue(
        attributeIndex).value()][classValue] + 1)
        / (getclassCounts()[classValue] + getAttributeList()[attributeIndex]
            .categoryCount());
  }
  
  
  public NominalAttribute getClasses() {
    return classes;
  }

  public NominalAttribute[] getAttributeList() {
    return attributeList;
  }

  public int[] getParentList() {
    return parentList;
  }

  public int getTrainingCount() {
    return trainingCount;
  }

  public int[][][] getCountsPerParent() {
    return countsPerParent;
  }

  public int[] getclassCounts() {
    return classCounts;
  }

}
