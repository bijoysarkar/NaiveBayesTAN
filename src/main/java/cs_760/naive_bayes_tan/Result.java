package cs_760.naive_bayes_tan;

public class Result {

  private Instance instance;
  private double[] posterior_probability;
  private int predicted_class_index;

  public Result(Instance instance, double[] posterior_probability) {
    this.instance = instance;
    this.posterior_probability = posterior_probability;
    double max = -1;
    predicted_class_index = -1;
    for (int i = 0; i < posterior_probability.length; i++) {
      if (posterior_probability[i] > max) {
        max = posterior_probability[i];
        predicted_class_index = i;
      }
    }
  }

  public int getInstanceClassIndex() {
    return instance.getInstanceClass();
  }

  public int getPredictedClassIndex() {
    return predicted_class_index;
  }

  public double getConfidence(int i) {
    return posterior_probability[i];
  }

}
