package Model;

import java.util.Random;

public class RandomGraph {

    public void generateEdges (Graph graph, int numVertices, int numEdges, double[] listWeight) {
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

            double weight = listWeight[rand.nextInt(listWeight.length)];

            graph.setAdj(start, target, weight);
            countEdges++;
        }

    }

    public Graph generateUnconnectedGraph (int numVertices, int numEdges, String[] splitWeights) {
        Graph randomGraph = new Graph(numVertices);

        double[] listWeight = new double[splitWeights.length];

        for (int l = 0; l < numVertices; l ++) {
            randomGraph.setLabels(l, String.valueOf(l +1));
        }

        for (int w = 0; w < splitWeights.length; w++) {
            listWeight[w] = Double.parseDouble(splitWeights[w].trim());
        }

        generateEdges(randomGraph, numVertices, numEdges, listWeight);

        return randomGraph;
    }



    public Graph generateConnectedGraph (int numVertices, int numEdges, String[] splitWeights) {
        Graph connectedGraph = new Graph(numVertices);
        Random rand = new Random();

        double[] listWeight = new double[splitWeights.length];

        for (int l = 0; l < numVertices; l ++) {
            connectedGraph.setLabels(l, String.valueOf(l +1));
        }

        for (int w = 0; w < splitWeights.length; w++) {
            listWeight[w] = Double.parseDouble(splitWeights[w].trim());
        }

        for (int i = 1; i < numVertices; i++) {
            int parent = rand.nextInt(i);
            double weight = listWeight[rand.nextInt(listWeight.length)];
            connectedGraph.setAdj(parent, i, weight);
        }

        int remainingEdges = numEdges - (numVertices - 1);

        if (remainingEdges > 0) {
            generateEdges(connectedGraph,numVertices,remainingEdges,listWeight);
        }

        return connectedGraph;
    }
}
