package Model;

import java.util.Random;

public class RandomGraph {

    public void generateWeightedEdges (Graph graph, int numVertices, int numEdges, int[] listWeight) {
        Random rand = new Random();
        int countEdges = 0;

        while (countEdges < numEdges) {
            int start = rand.nextInt(numVertices);
            int target = rand.nextInt(numVertices);

            if (start == target) {
                continue;
            }

            if (graph.existEdge(start, target)) {
                continue;
            }

            int weight = listWeight[rand.nextInt(listWeight.length)];

            graph.setAdj(start, target, weight);
            countEdges++;
        }

    }

    public void generateUnweightedEdges (Graph graph, int numVertices, int numEdges) {
        Random rand = new Random();
        int countEdges = 0;

        while (countEdges < numEdges) {
            int start = rand.nextInt(numVertices);
            int target = rand.nextInt(numVertices);

            if (start == target) {
                continue;
            }

            if (graph.existEdge(start, target)) {
                continue;
            }

            graph.setAdj(start, target, 0);
            countEdges++;
        }
    }

    public Graph generateWeightedGraph (int numVertices, int numEdges, String[] splitLabels, String[] splitWeights) {
        Graph randomGraph = new Graph(numVertices);

        int[] listWeight = new int[splitWeights.length];

        for (int l = 0; l < splitLabels.length; l ++) {
            randomGraph.setLabels(l, splitLabels[l]);
        }

        for (int w = 0; w < splitWeights.length; w++) {
            listWeight[w] = Integer.parseInt(splitWeights[w].trim());
        }

        generateWeightedEdges(randomGraph, numVertices, numEdges, listWeight);

        return randomGraph;
    }

    public Graph generateUnweightedGraph (int numVertices, int numEdges, String[] splitLabels) {
        Graph randomGraph = new Graph(numVertices);

        for (int l = 0; l < splitLabels.length; l ++) {
            randomGraph.setLabels(l, splitLabels[l]);
        }

        generateUnweightedEdges(randomGraph, numVertices, numEdges);

        return randomGraph;
    }

    public Graph connectedWeightedGraph (int numVertices, int numEdges, String[] splitLabels, String[] splitWeights) {
        Graph connectedGraph = new Graph(numVertices);
        Random rand = new Random();

        int[] listWeight = new int[splitWeights.length];

        for (int l = 0; l < splitLabels.length; l ++) {
            connectedGraph.setLabels(l, splitLabels[l]);
        }

        for (int w = 0; w < splitWeights.length; w++) {
            listWeight[w] = Integer.parseInt(splitWeights[w].trim());
        }

        for (int i = 1; i < numVertices; i++) {
            int parent = rand.nextInt(i);
            int weight = listWeight[rand.nextInt(listWeight.length)];
            connectedGraph.setAdj(parent, i, weight);
        }

        int remainingEdges = numEdges - (numVertices - 1);

        if (remainingEdges > 0) {
            generateWeightedEdges(connectedGraph,numVertices,remainingEdges,listWeight);
        }

        return connectedGraph;
    }

    public Graph connectedUnweightedGraph (int numVertices, int numEdges, String[] splitLabels) {
        Graph connectedGraph = new Graph(numVertices);
        Random rand = new Random();


        for (int l = 0; l < splitLabels.length; l ++) {
            connectedGraph.setLabels(l, splitLabels[l]);
        }


        for (int i = 1; i < numVertices; i++) {
            int parent = rand.nextInt(i);
            connectedGraph.setAdj(parent, i, 0);
        }

        int remainingEdges = numEdges - (numVertices - 1);

        if (remainingEdges > 0) {
            generateUnweightedEdges(connectedGraph,numVertices,remainingEdges);
        }

        return connectedGraph;
    }
}
