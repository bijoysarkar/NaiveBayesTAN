package cs_760.naive_bayes_tan;

public class Driver {

  public void part1(String training_data, String test_data, String type) {
    Bayes naiveBayes = new Bayes(training_data, test_data, type);
    naiveBayes.train();
    naiveBayes.test(true);
  }

  public void part2(String training_data, String test_data, String type) {
    Bayes naiveBayes = new Bayes(training_data, test_data, type);
    int[] sample_sizes = {25, 50, 100};
    int repeat_count = 4;
    for (int sample_size : sample_sizes) {
      double sum = 0;
      for (int i = 0; i < repeat_count; i++) {
        naiveBayes.train(sample_size);
        sum += naiveBayes.test(false);
      }
      System.out.println(sample_size + "\t" + sum / repeat_count);
    }
  }

  public void part3(String training_data, String test_data, String type) {
    Bayes naiveBayes = new Bayes(training_data, test_data, type);
    naiveBayes.train();
    naiveBayes.roc();
  }

  public static void main(String[] args) {
    Driver driver = new Driver();
    driver.part1(args[0], args[1], args[2]);
  }
}
