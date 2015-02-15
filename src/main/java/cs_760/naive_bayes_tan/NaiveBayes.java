package cs_760.naive_bayes_tan;

public class NaiveBayes {

  private final String TRAINING_DATA;
  private final String TEST_DATA;
  private Model model;

  public NaiveBayes(String trainingData, String testData) {
    this.TRAINING_DATA = trainingData;
    this.TEST_DATA = testData;
  }

  public void train(String type) {
    Data training_data = new Data(TRAINING_DATA);
    if ("n".equals(type))
      model = new ModelNaiveBayes(training_data);
    else
      model = new ModelTAN(training_data);
  }

  public double p_c(int parentIndex, int parentValue) {
    return ((double) model.getCounts()[parentIndex][parentValue] + 1)
        / (model.getTrainingCount() + 2);
  }

  public double p_i_c(int attributeIndex, int attributeValue, int parentIndex, int parentValue) {
    return (double) (model.getCountsPerParent()[attributeIndex][attributeValue][parentValue] + 1)
        / (model.getCounts()[parentIndex][parentValue] + model.getAttributeList()[attributeIndex]
            .categoryCount());
  }

  public void classify(Instance instance) {
    double sum = 0;
    double[] posterior = new double[model.getClasses().categoryCount()];
    for (int i = 0; i < posterior.length; i++) {
      posterior[i] = Math.log(p_c(model.getAttributeList().length - 1, i));
      for (int j = 0; j < model.getAttributeList().length - 1; j++) {
        posterior[i] =
            posterior[i]
                + Math.log(p_i_c(j, (int) instance.getAttributeValue(j).value(),
                    model.getParentList()[j], i));
      }
      posterior[i] = Math.exp(posterior[i]);
      sum = sum + posterior[i];
    }

    double max = -1;
    int maxIndex = -1;
    for (int i = 0; i < posterior.length; i++) {
      posterior[i] = posterior[i] / sum;
      if (posterior[i] > max) {
        max = posterior[i];
        maxIndex = i;
      }
    }

    System.out.println(model.getClasses().categoryName(maxIndex) + " "
        + model.getClasses().categoryName(instance.getInstanceClass()) + " " + max);
  }

  public void test() {
    Data test_data = new Data(TEST_DATA);
    for (Instance instance : test_data.getInstanceList())
      classify(instance);
  }
}
