package ch.heig.gre.tp1.groupX;

import ch.heig.gre.tp1.graph.APSPAlgorithm;
import ch.heig.gre.tp1.graph.APSPResult;
import ch.heig.gre.tp1.graph.WeightedDigraph;

import java.util.ArrayList;
import java.util.List;

import static ch.heig.gre.tp1.graph.APSPResult.UNREACHABLE;

public final class FloydWarshallAlgorithm implements APSPAlgorithm {


  @Override
  public APSPResult compute(WeightedDigraph graph) {
    // get number of vertices in graph
    int n = graph.getNVertices();

    // initialize distance and predecessor matrices
    int[][] distances = new int[n][n];
    int[][] predecessors = new int[n][n];

    // fill distance and predecessor matrices with initial values
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        distances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
        predecessors[i][j] = UNREACHABLE;
      }
    }

    // fill with arc existant
    for (int k = 0; k < n; ++k) {
      for (WeightedDigraph.Edge edge : graph.getOutgoingEdges(k)) {
        int to = edge.to();
        int weight = edge.weight();

        // if multiple edges exist, keep the one with the smallest weight
        if (weight < distances[k][to] || distances[k][to] == Integer.MAX_VALUE) {
          distances[k][to] = weight;
          predecessors[k][to] = k;
        }
      }
    }

    // Floyd-Warshall
    for (int l = 0; l < n; ++l) {
      for (int m = 0; m < n; ++m) {
        // stop if m is unreachable from l
        if (distances[m][l] == Integer.MAX_VALUE) continue;

        for (int p = 0; p < n; ++p) {
          // stop if p is unreachable from m
          if (distances[l][p] == Integer.MAX_VALUE) continue;

          long newDistance = (long) distances[m][l] + (long) distances[l][p];
          // check if new distance is smaller than current one from l to p and update
          if (newDistance < distances[m][p]) {
            distances[m][p] = (int) newDistance;
            predecessors[m][p] = predecessors[l][p];
          }
        }
      }
    }

    // Check for negative cycles
    for (int q = 0; q < n; ++q) {
      if (distances[q][q] < 0) {
        return new APSPResult.NegativeCycle(extractNegativeCycle(predecessors, q, n), (int) distances[q][q]);
      }
    }

    return new APSPResult.APShortestPath(distances, predecessors);
  }

  private List<Integer> extractNegativeCycle(int[][] predecessors, int start, int n) {
    // Follow predecessors in the shortest-path tree rooted at start.
    int s = start;
    for (int r = 0; r < n; ++r) {
      s = predecessors[start][s];
      if (s == UNREACHABLE) {
        return List.of(start, start);
      }
    }

    // Collect one cycle by walking predecessors until we come back to s.
    List<Integer> reverseCycle = new ArrayList<>();
    int current = s;
    do {
      reverseCycle.add(current);
      current = predecessors[start][current];
      if (current == UNREACHABLE) {
        return List.of(start, start);
      }
    } while (current != s && reverseCycle.size() <= n + 1);

    // Rebuild in forward edge order and close the cycle.
    List<Integer> cycle = new ArrayList<>(reverseCycle.size() + 1);
    for (int i = reverseCycle.size() - 1; i >= 0; --i) {
      cycle.add(reverseCycle.get(i));
    }
    cycle.add(cycle.get(0));

    return cycle;
  }
}