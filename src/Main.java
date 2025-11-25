import Controller.DatasetController;
import Controller.GraphController;
import Model.Graph;
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



        GraphController graphController = new GraphController();
        Graph graph = graphController.loadPajekData("./src/Data/Filtered/dissimilarity_graph.net");
        graphController.graph(graph);
//        Graph g = new Graph(7);
//        String[] labels = new String[]{"A","B","C","D","E","F","G"};
//
//        for (int i = 0; i < labels.length; i++){
//            g.setLabels(i, labels[i]);
//        }
//        g.setAdj(0, 1, 5);
//        g.setAdj(0, 2, 2);
//        g.setAdj(1, 3, 7);
//        g.setAdj(2, 3, 6);
//        g.setAdj(1, 2, 7);
//        g.setAdj(0, 3, 20);
//        g.setAdj(4, 5, 6);
//        g.setAdj(5, 6, 7);
//        g.setAdj(6, 4, 20);
//        g.setAdj(0, 6, 10);
//        g.setAdj(1, 6, 40);
//        g.setAdj(2, 4, 10);
//        g.setAdj(3, 5, 10);
//        g.setAdj(6, 4, 7);
//        g.setAdj(3, 4, 10);
//        g.setAdj(3, 6, 8);
//
//
//        graphController.graph(g);
    }
}