package Controller;
import Model.Graph;
import Model.RandomGraph;

import java.util.Scanner;

public class RandomGraphController {

    private int numVertices;
    int numEdges;
    private String weights;
    String[] splitWeights;

    public void randomGraph(RandomGraph randomGraph) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of vertices: ");
        numVertices = sc.nextInt();

        String option;

        while(true) {
            System.out.println("Enter the number of edges: ");numEdges = sc.nextInt();

            System.out.println("Do you want generate a connected graph? (Y - yes, N - no ): ");

            sc.nextLine();

            option = sc.nextLine().trim().toUpperCase();

            if (option.equals("Y") && numEdges < numVertices - 1) {
                System.out.println("Error: For a connected graph, the number of edges must be at least " + (numVertices - 1) + ".");
                continue;
            }

            break;
        }

        System.out.println("Enter the weights (comma-separated): ");

        weights = sc.nextLine();

        splitWeights = weights.split("\\s*,\\s*");

        Graph graph = null;

        switch (option) {
            case "Y":
                System.out.println("\nConnected weighted graph: ");
                graph = randomGraph.generateConnectedGraph(numVertices, numEdges, splitWeights);
                break;

            case "N":
                System.out.println("\nUnconnected weighted graph: ");
                graph = randomGraph.generateUnconnectedGraph(numVertices, numEdges, splitWeights);
                break;

            default:
                System.out.println("Invalid option!");
                break;
        }

        graph.printGraph();

        if (graph.isConnected()) {
            System.out.println("The graph is connected!");
        } else {
            System.out.println("The graph is not connected!");
        }
    }
}
