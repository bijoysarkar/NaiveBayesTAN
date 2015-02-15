package cs_760.naive_bayes_tan;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Graph {

  private double graph[][];

  public Graph(int n) {
    graph = new double[n][n];
  }

  public void setWeight(int i, int j, double weight) {
    graph[i][j] = weight;
    graph[j][i] = weight;
  }

  public int[] maximumSpanningTree() {
    int no_of_attributes = graph.length;
    int[] mark = new int[no_of_attributes+1];
    mark[mark.length-1] = no_of_attributes;
    for (int i = 0; i < no_of_attributes; i++) {
      mark[i] = -1;
    }

    PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
    queue.add(new Edge(no_of_attributes, 0, 1));

    while (!queue.isEmpty()) {
      Edge edge = queue.poll();
      if (mark[edge.to] == -1) {
        mark[edge.to] = edge.from;
        for (int i = 0; i < no_of_attributes; i++) {
          if (mark[i] == -1)
            queue.add(new Edge(edge.to, i, graph[edge.to][i]));
        }
      }
    }
    return mark;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (double[] array : graph) {
      sb.append(Arrays.toString(array)).append("\n");
    }
    return sb.toString();
  }

  /*
   * public static void main(String[] args) { PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
   * queue.add(new Edge(1, 2, 0.5)); queue.add(new Edge(1, 2, 0.6)); queue.add(new Edge(2, 3, 0.5));
   * queue.add(new Edge(2, 2, 0.5)); queue.add(new Edge(1, 1, 0.5)); queue.add(new Edge(2, 2, 0.5));
   * queue.add(new Edge(3, 2, 0.5));
   * 
   * while (!queue.isEmpty()) System.out.println(queue.poll()); }
   */

}


class Edge implements Comparable<Edge> {
  int from;
  int to;
  double weight;

  public Edge(int from, int to, double weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  @Override
  public int compareTo(Edge o) {
    if (Math.abs(weight - o.weight) < 0.00000001) {
      if (from == o.from) {
        if (to == o.to) {
          return 0;
        } else {
          return ((to < o.to) ? -1 : 1);
        }
      } else {
        return ((from < o.from) ? -1 : 1);
      }
    } else {
      return ((weight > o.weight) ? -1 : 1);
    }
  }

  @Override
  public String toString() {
    return "[" + from + "->" + to + "](" + weight + ")";
  }
}
