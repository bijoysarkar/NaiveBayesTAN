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
    for (int sample_size : sample_sizes) {
      for (int i = 0; i < 4; i++) {
        naiveBayes.train(sample_size);
        System.out.println(sample_size+"->"+naiveBayes.test(false));
      }
    }

  }

  public static void main(String[] args) {
    Driver driver = new Driver();
    driver.part2(args[0], args[1], args[2]);
  }
}
