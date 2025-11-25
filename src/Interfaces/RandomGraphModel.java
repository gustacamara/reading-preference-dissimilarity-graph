package Interfaces;

import Model.Graph;

public interface RandomGraphModel {
    public void generateWeightedEdges (Graph graph, int numVertices, int numEdges, int[] listWeight);
    public void generateUnweightedEdges (Graph graph, int numVertices, int numEdges);
    public Graph generateWeightedGraph (int numVertices, int numEdges, String[] splitWeights);
    public Graph generateUnweightedGraph (int numVertices, int numEdges);
    public Graph connectedWeightedGraph (int numVertices, int numEdges, String[] splitWeights);
    public Graph connectedUnweightedGraph (int numVertices, int numEdges);
}

