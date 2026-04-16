package ch.heig.gre.tp1.graph;

import java.util.Arrays;
import java.util.List;

/**
 * Result type for APSP (All-Pairs Shortest Path) algorithms.
 * <p>
 * A result is either:
 * <ul>
 *   <li>a {@link APShortestPath}, when the graph does not contain a negative cycle;</li>
 *   <li>a {@link NegativeCycle}, when the graph contains a negative cycle.</li>
 * </ul>
 */
public sealed interface APSPResult {
  /**
   * Value used to represent an unreachable vertex in the shortest path tree.
   */
  int UNREACHABLE = -1;

  /**
   * @return {@code true} if the result is a {@link NegativeCycle}, {@code false} otherwise.
   */
  boolean isNegativeCycle();

  /**
   * @return The {@link APShortestPath} if the result is a {@link APShortestPath}, {@code null} otherwise.
   */
  default APShortestPath getAPShortestPath() {
    return null;
  }

  /**
   * @return The {@link NegativeCycle} if the result is a {@link NegativeCycle}, {@code null} otherwise.
   */
  default NegativeCycle getNegativeCycle() {
    return null;
  }

  /**
   * APSP algorithm result as a pair of matrices storing all shortest path trees of the graph.
   *
   * @param distances    Distance matrix from each source vertex (row index) to each reachable vertex (column index).
   *                     {@link Integer#MAX_VALUE} if the vertex is unreachable.
   * @param predecessors Predecessor matrix of each reachable vertex in each shortest path tree.
   *                     {@link APSPResult#UNREACHABLE} if the vertex is unreachable.
   */
  record APShortestPath(int[][] distances, int[][] predecessors) implements APSPResult {
    @Override
    public boolean isNegativeCycle() {
      return false;
    }

    @Override
    public APShortestPath getAPShortestPath() {
      return this;
    }

    @Override
    public String toString() {
      StringBuilder result = new StringBuilder("Distance Matrix: \n[ ");
      for (int[] distance : distances) {
        result.append(Arrays.toString(distance)).append("\n  ");
      }
      result.append("]\nPredecessor Matrix: \n[ ");
      for (int[] predecessor : predecessors) {
        result.append(Arrays.toString(predecessor)).append("\n  ");
      }
      result.append("]\n");
      return result.toString();
    }
  }

  /**
   * APSP algorithm result as a negative cycle and its length.
   * <p>
   * There are no guarantees on the implementation type of the {@code values} List.
   *
   * @param values List of vertices in a negative cycle.
   * @param length Total length of the negative cycle.
   */
  record NegativeCycle(List<Integer> values, int length) implements APSPResult {
    @Override
    public boolean isNegativeCycle() {
      return true;
    }

    @Override
    public NegativeCycle getNegativeCycle() {
      return this;
    }

    @Override
    public String toString() {
      return "NegativeCycle of length " + length + " found: " + values;
    }
  }
}
