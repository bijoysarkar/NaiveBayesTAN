package cs_760.naive_bayes_tan;

import java.util.Arrays;

public abstract class Model {

  protected NominalAttribute classes;
  protected NominalAttribute[] attributeList;
  protected int[] classCounts;
  protected int[][][] countsPerParent;
  protected int trainingInstanceCount;
  protected int[] parentList;

  private Data training_data;
  
  public Model(Data training_data) {

    this.training_data = training_data;
    
    int no_of_attributes = training_data.getAttributeList().length;
    classes = training_data.getClasses();

    // Since all discrete values
    attributeList = new NominalAttribute[no_of_attributes];
    for (int i = 0; i < no_of_attributes; i++) {
      attributeList[i] = (NominalAttribute) training_data.getAttributeList()[i];
    }
    trainingInstanceCount = -1;
  }
  
  public void train(){
    train(training_data.getInstanceList());
  }
  
  public void train(int sample_size){
    train(training_data.randomSample(sample_size));
  }

  public void train(Instance[] instanceList){
    trainingInstanceCount = instanceList.length;
    classCounts = calculateClassCounts(instanceList);
    countsPerParent = calculateCountsPerParent(instanceList);
    parentList = getParents(instanceList);
  }
  
  public int[] calculateClassCounts(Instance[] instanceList) {
    int[] countsTable = new int[classes.categoryCount()];
    for (Instance instance : instanceList)
      countsTable[instance.getInstanceClass()]++;
    return countsTable;
  }

  public int[][][] calculateCountsPerParent(Instance[] instanceList) {
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

  public int[][][][][] calculateJointCountsPerParent(Instance[] instanceList) {
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

  public abstract int[] getParents(Instance[] instanceList);

  public double p_c(int classValue) {
    return ((double) classCounts[classValue] + 1)
        / (trainingInstanceCount + getClasses().categoryCount());
  }

  public abstract double p_i_c(int attributeIndex, int classValue, Instance instance);

  public NominalAttribute getClasses() {
    return classes;
  }

  public int getNoOfAttribute() {
    return attributeList.length;
  }

  public String getAttribute(int i) {
    return attributeList[i].getName();
  }

  public String getParent(int i) {
    return ((parentList[i] == attributeList.length) ? null : attributeList[parentList[i]].getName());
  }


}
