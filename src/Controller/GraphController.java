package Controller;

import Model.Graph;

public class GraphController {
    public void graph(Graph graph) {
        boolean isConnected = graph.isConnected();
        boolean isEulerian = graph.isEulerian();

        graph.printGraph();

        if (isConnected) {
            System.out.println("The graph is connected!");
        } else {
            System.out.println("The graph is not connected!");
        }

        if (isEulerian){
            System.out.println("The graph is Eulerian!");
        } else {
            System.out.println("The graph is not Eulerian!");
        }

        graph.isCyclic();

        graph.betweennessCentrality();
        graph.closenessCentrality();
    }
}
