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

  public double p_c(int classValue) {
    return model.p_c(classValue);
  }

  public double p_i_c(int attributeIndex, int classValue, Instance instance) {
    return model.p_i_c(attributeIndex, classValue, instance);
  }

  public void classify(Instance instance) {
    double sum = 0;
    double[] posterior = new double[model.getClasses().categoryCount()];
    for (int i = 0; i < posterior.length; i++) {
      posterior[i] = Math.log(p_c(i));
      for (int j = 0; j < model.getAttributeList().length; j++) {
        posterior[i] = posterior[i] + Math.log(p_i_c(j, i, instance));
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
