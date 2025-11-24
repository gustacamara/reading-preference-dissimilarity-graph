package Interfaces;

import Model.Vertex;

import java.util.List;
import java.util.Map;

public interface GraphModel {
    public void setVertices(Vertex[] vertices);
    public void setLabels(String[] labels);
    void setAdj(int origin, int target, int weight);
    void searchComponents(int start, List<Integer> components, List<Integer> visited);
    int components();
    boolean isConnected();
    void calculateDegree(Map<String, Integer> countDegree);
    boolean isEulerian ();
    boolean dfsCycle(int start, List<Integer> visited);
    void isCyclic();
    int[] dijkstra(int start, double[] distanceValues);
    double sumDistances(int start);
    void closenessCentrality ();
    void betweennessCentrality();
    void printGraph();
}
