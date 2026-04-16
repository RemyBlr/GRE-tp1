package ch.heig.gre.tp1.groupX;

import ch.heig.gre.tp1.graph.APSPAlgorithm;
import ch.heig.gre.tp1.graph.APSPResult;
import ch.heig.gre.tp1.graph.WeightedDigraph;

import static ch.heig.gre.tp1.graph.APSPResult.UNREACHABLE;

public final class FloydWarshallAlgorithm implements APSPAlgorithm {


  @Override
  public APSPResult compute(WeightedDigraph graph) {

    int[][] distances = {{0}};
    int[][] predecessors = {{UNREACHABLE}};
    return new APSPResult.APShortestPath(distances, predecessors);
  }
}

