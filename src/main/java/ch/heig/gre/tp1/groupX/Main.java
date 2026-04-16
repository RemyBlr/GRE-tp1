package ch.heig.gre.tp1.groupX;

import ch.heig.gre.tp1.graph.APSPResult;
import ch.heig.gre.tp1.graph.WeightedDigraph;
import ch.heig.gre.tp1.graph.WeightedDigraphReader;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    WeightedDigraph graph = WeightedDigraphReader.fromFile("data/reseau1.txt");
    FloydWarshallAlgorithm algo = new FloydWarshallAlgorithm();
    APSPResult result = algo.compute(graph);

    System.out.println(result);
  }

}
