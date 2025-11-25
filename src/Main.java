import Controller.DatasetController;
import Controller.GraphController;
import Controller.RandomGraphController;
import Model.Graph;
import Model.RandomGraph;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DatasetController dc = new DatasetController(
                "./src/Data/Books_rating.csv",
                "./src/Data/Filtered/Books_rating_filtered.csv",
                "./src/Data/Filtered/dissimilarity_graph.net"
        );
        dc.filter();
        dc.createPajekFile();

        Graph graph = new Graph(7);
        String[] labels = new String[]{"A","B","C","D","E","F","G"};

        for (int i = 0; i < labels.length; i++){
            graph.setLabels(i, labels[i]);
        }

        GraphController graphController = new GraphController();
        RandomGraphController randomGraphController = new RandomGraphController();
        RandomGraph randomGraph = new RandomGraph();


        graph.setAdj(0, 1, 5);
        graph.setAdj(0, 2, 2);
        graph.setAdj(1, 3, 7);
        graph.setAdj(2, 3, 6);
        graph.setAdj(1, 2, 7);
        graph.setAdj(0, 3, 20);
        graph.setAdj(4, 5, 6);
        graph.setAdj(5, 6, 7);
        graph.setAdj(6, 4, 20);
        graph.setAdj(0, 6, 10);
        graph.setAdj(1, 6, 40);
        graph.setAdj(2, 4, 10);
        graph.setAdj(3, 5, 10);
        graph.setAdj(6, 4, 7);
        graph.setAdj(3, 4, 10);
        graph.setAdj(3, 6, 8);


        graphController.graph(graph);

        randomGraphController.randomGraph(randomGraph);
    }
