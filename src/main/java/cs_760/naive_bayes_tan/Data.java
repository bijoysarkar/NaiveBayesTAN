package cs_760.naive_bayes_tan;

import java.util.List;
import java.util.Random;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Data {

  private NominalAttribute classes;
  private Attribute[] attributeList;
  private Instance[] instanceList;

  public Data(String training_file) {
    read(training_file);
  }

  private void read(String data_file) {
    try {
      DataSource source = new DataSource(data_file);
      Instances data = source.getDataSet();
      if (data.classIndex() == -1)
        data.setClassIndex(data.numAttributes() - 1);
      loadClasses(data);
      loadAttributeList(data.firstInstance());
      loadInstanceList(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  private void loadClasses(Instances data) {
    classes =
        new NominalAttribute(data.attribute(data.classIndex()).name(), data.attribute(
            data.classIndex()).numValues(), data.attribute(data.classIndex()).enumerateValues());
  }

  @SuppressWarnings("unchecked")
  private void loadAttributeList(weka.core.Instance instance) {
    int numAttributes = instance.numAttributes() - 1;
    attributeList = new Attribute[numAttributes];
    for (int i = 0; i < numAttributes; i++) {
      weka.core.Attribute wekaAttribute = instance.attribute(i);
      if (wekaAttribute.isNominal()) {
        attributeList[i] =
            new NominalAttribute(wekaAttribute.name(), wekaAttribute.numValues(),
                wekaAttribute.enumerateValues());
      } else if (wekaAttribute.isNumeric()) {
        attributeList[i] = new NumericalAttribute(wekaAttribute.name());
      }
    }
  }

  private void loadInstanceList(Instances data) {
    int numAttributes = data.numAttributes() - 1;
    instanceList = new Instance[data.numInstances()];
    for (int i = 0; i < data.numInstances(); i++) {
      weka.core.Instance wekaInstance = data.instance(i);
      Instance instance =
          new Instance(numAttributes, classes.getAttribute(wekaInstance.stringValue(numAttributes)));
      for (int j = 0; j < numAttributes; j++) {
        if (wekaInstance.attribute(j).isNominal()) {
          instance.setAttributeValue(j, new NominalAttributeValue(
              ((NominalAttribute) attributeList[j]).getAttribute(wekaInstance.stringValue(j))));
        } else if (wekaInstance.attribute(j).isNumeric()) {
          instance.setAttributeValue(j, new NumericalAttributeValue(wekaInstance.value(j)));
        }
      }
      instanceList[i] = instance;
    }
  }

  public NominalAttribute getClasses() {
    return classes;
  }

  public Attribute[] getAttributeList() {
    return attributeList;
  }

  public Instance[] getInstanceList() {
    return instanceList;
  }

  public Instance[] randomSample(List<Instance> list, int sample_size) {
    int size = list.size();
    if (size < sample_size)
      return null;
    Instance[] sampleList = new Instance[sample_size];
    Random random = new Random();
    for (int i = 0; i < sample_size; i++) {
      // Sample with replacement
      // sampleList[i] = list.get(random.nextInt(size));

      // Sample without replacement
      sampleList[i] = list.remove(random.nextInt(size));
      size--;
    }
    return sampleList;
  }

}
