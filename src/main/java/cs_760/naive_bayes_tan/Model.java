package cs_760.naive_bayes_tan;

public abstract class Model {

  private NominalAttribute classes;
  private NominalAttribute[] attributeList;
  private Instance[] instanceList;
  private int[] parentList;
  private int trainingCount;
  private int[][][] countsPerParent;
  private int[][] counts;

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

    trainingCount = 0;
    countsPerParent = new int[no_of_attributes][][];
    counts = new int[no_of_attributes][];

    for (int i = 0; i < no_of_attributes; i++) {
      countsPerParent[i] =
          new int[attributeList[i].categoryCount()][attributeList[parentList[i]].categoryCount()];
      counts[i] = new int[attributeList[i].categoryCount()];
    }

    for (Instance instance : training_data.getInstanceList()) {
      trainingCount++;
      for (int i = 0; i < no_of_attributes; i++) {
        countsPerParent[i][(int) instance.getAttributeValue(i).value()][(int) instance
            .getAttributeValue(parentList[i]).value()]++;
        counts[i][(int) instance.getAttributeValue(i).value()]++;
      }
    }
  }

  public abstract int[] getParents(NominalAttribute classes, NominalAttribute[] attributeList,
      Instance[] instanceList);

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

  public int[][] getCounts() {
    return counts;
  }

}
