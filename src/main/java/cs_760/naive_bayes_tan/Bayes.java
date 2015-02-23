package cs_760.naive_bayes_tan;

import java.util.Arrays;
import java.util.Comparator;

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
    int count = 0;
    for (Instance instance : test_data.getInstanceList())
      if (classify(instance, print))
        count++;
    if (print)
      System.out.println("\n" + count);
    return (double) count / test_data.getInstanceList().length;
  }


  public boolean classify(Instance instance, boolean print) {
    Result result = new Result(instance, calculatePosteriorProbability(instance));
    if (print)
      System.out.println(model.getClasses().categoryName(result.getPredictedClassIndex()) + " "
          + model.getClasses().categoryName(instance.getInstanceClass()) + " "
          + result.getConfidence(result.getPredictedClassIndex()));
    return (result.getPredictedClassIndex() == instance.getInstanceClass());
  }

  public void roc() {
    Data test_data = new Data(TEST_DATA);
    // Given the class label listed second will be the 'positive' class label
    final int positive_class_index = 1;
    int positive_count = 0;
    int total_count = 0;
    Result[] resultSet = new Result[test_data.getInstanceList().length];
    for (Instance instance : test_data.getInstanceList()) {
      if (instance.getInstanceClass() == positive_class_index)
        positive_count++;
      resultSet[total_count] = new Result(instance, calculatePosteriorProbability(instance));
      total_count++;
    }

    Arrays.sort(resultSet, new Comparator<Result>() {
      @Override
      public int compare(Result result1, Result result2) {
        // In extreme case double precision may influence ordering
        return Double.compare(result2.getConfidence(positive_class_index),
            result1.getConfidence(positive_class_index));
      }
    });

    int tp = (resultSet[0].getInstanceClassIndex() == positive_class_index) ? 1 : 0;
    int fp = 1 - tp;
    for (int i = 1; i < resultSet.length; i++) {
      if (resultSet[i].getInstanceClassIndex() != resultSet[i - 1].getInstanceClassIndex()) {
        System.out.println((double) tp / positive_count + "\t" + (double) fp
            / (total_count - positive_count));
      }
      if (resultSet[i].getInstanceClassIndex() == positive_class_index)
        tp++;
      else
        fp++;
    }
    System.out.println((double) tp / positive_count + "\t" + (double) fp
        / (total_count - positive_count));
  }

  public double[] calculatePosteriorProbability(Instance instance) {
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
    for (int i = 0; i < posterior.length; i++)
      posterior[i] = posterior[i] / sum;
    return posterior;
  }

}
