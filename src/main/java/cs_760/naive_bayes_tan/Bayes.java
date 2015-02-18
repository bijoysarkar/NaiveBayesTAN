package cs_760.naive_bayes_tan;

public class Bayes {

  private final String TRAINING_DATA;
  private final String TEST_DATA;
  private final String TYPE;
  private final Model model;

  public Bayes(String trainingData, String testData, String type) {
    this.TRAINING_DATA = trainingData;
    this.TEST_DATA = testData;
    this.TYPE = type;

    Data training_data = new Data(TRAINING_DATA);
    if ("n".equals(TYPE))
      model = new ModelNaiveBayes(training_data);
    else
      model = new ModelTAN(training_data);

  }

  public void train() {
    model.train();
  }

  public void train(int sample_size) {
    model.train(sample_size);
  }

  public double test(boolean print) {

    if (print) {
      for (int i = 0; i < model.getNoOfAttribute(); i++) {
        String parent = model.getParent(i);
        System.out.println(model.getAttribute(i) + " " + ((parent == null) ? "" : parent + " ")
            + "class");
      }
      System.out.println();
    }
    Data test_data = new Data(TEST_DATA);
    // int total_count = test_data.getInstanceList().length;
    int count = 0;
    for (Instance instance : test_data.getInstanceList())
      if (classify(instance, print))
        count++;
    if(print)
      System.out.println("\n" + count);
    return (double)count/test_data.getInstanceList().length;
  }

  public boolean classify(Instance instance, boolean print) {
    double sum = 0;
    double[] posterior = new double[model.getClasses().categoryCount()];
    for (int i = 0; i < posterior.length; i++) {
      posterior[i] = Math.log(model.p_c(i));
      for (int j = 0; j < model.getNoOfAttribute(); j++) {
        posterior[i] = posterior[i] + Math.log(model.p_i_c(j, i, instance));
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

    if (print)
      System.out.println(model.getClasses().categoryName(maxIndex) + " "
          + model.getClasses().categoryName(instance.getInstanceClass()) + " " + max);

    return (maxIndex == instance.getInstanceClass());
  }

}
