package Controller;
import Model.Graph;
import Model.RandomGraph;

import java.util.Scanner;

public class RandomGraphController {
    private int numVertices;
    private int numEdges;
    private String weights;
    String[] splitWeights;

    public void input() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of vertices: ");
        numVertices = sc.nextInt();

        System.out.println("Enter the number of edges: ");
        numEdges = sc.nextInt();

        sc.nextLine();

        System.out.println("Enter the weights (comma-separated): ");
        weights = sc.nextLine();

    }

    public void randomGraph(RandomGraph randomGraph) {
        input();

        int vertices = this.numVertices;
        int edges = this.numEdges;
        String weights = this.weights;

        splitWeights = weights.split("\\s*,\\s*");

        System.out.println("\nConnected weighted graph: ");

        Graph connectedWeightedGraph = randomGraph.connectedWeightedGraph(vertices, edges, splitWeights);
        connectedWeightedGraph.printGraph();

        boolean isConnected = connectedWeightedGraph.isConnected();

        if (isConnected) {
            System.out.println("The graph is connected!");
        } else {
            System.out.println("The graph is not connected!");
        }

        System.out.println("\nUnconnected weighted graph: ");

        Graph unconnectedWeightedGraph = randomGraph.generateWeightedGraph(vertices, edges, splitWeights);
        unconnectedWeightedGraph.printGraph();
    }
}
