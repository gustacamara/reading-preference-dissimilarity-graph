package Controller;

import Model.Graph;
import Model.Triple;
import Model.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

    public Graph loadPajekData(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line, label;
        String[] fields;
        boolean verticesRead = false;
        Integer idI, idJ;
        double dissimilary;
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Triple<Integer, Integer, Double>> edges = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if(line.startsWith("*Vertices")){
               continue;
            }
            if(line.startsWith("*Edges")) {
                verticesRead = true;
                continue;
            }
            if (!verticesRead){
                fields = line.split("\"");
                label = fields[1].trim();
                labels.add(label);
                continue;
            }
            fields = line.split(" ");
            idI = Integer.valueOf(fields[0].trim());
            idJ = Integer.valueOf(fields[1].trim());
            dissimilary = Double.parseDouble(fields[2].trim());
            edges.add(new Triple<>(idI, idJ, dissimilary));
        }
        Graph g = new Graph(labels.size());
        for(Triple<Integer, Integer, Double> edge : edges){
            g.setAdj(edge.getFirst(), edge.getSecond(), edge.getThird());
        }
        for(int i = 0; i < labels.size(); i++){
            g.setLabels(i, labels.get(i));
        }

        return g;
    }
}
