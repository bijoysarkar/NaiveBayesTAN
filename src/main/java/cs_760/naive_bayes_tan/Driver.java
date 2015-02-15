package cs_760.naive_bayes_tan;

public class Driver {

  public static void main(String[] args) {

    NaiveBayes naiveBayes =
        new NaiveBayes(
            "/home/bsarkar/Documents/GitHub/NaiveBayesTAN/src/main/resources/lymph_train.arff",
            "/home/bsarkar/Documents/GitHub/NaiveBayesTAN/src/main/resources/lymph_test.arff");
/*
    NaiveBayes naiveBayes =
        new NaiveBayes(
            "/home/bsarkar/Documents/GitHub/NaiveBayesTAN/src/main/resources/testcases1/nursery.arff",
            "/home/bsarkar/Documents/GitHub/NaiveBayesTAN/src/main/resources/testcases1/nursery.arff");

*/
    naiveBayes.train("n");
    naiveBayes.test();
  }
}
