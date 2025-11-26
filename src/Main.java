import Controller.DatasetController;
import Controller.GraphController;
import Controller.RandomGraphController;
import Model.Graph;
import Model.RandomGraph;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DatasetController dc = new DatasetController(
                "./src/Data/Books_rating_100000.csv",
                "./src/Data/Filtered/Books_rating_filtered.csv",
                "./src/Data/Filtered/dissimilarity_graph.net"
        );
        dc.filter();
        dc.createPajekFile();
        System.out.println("""
                Students : {\s
                \tLuis Gustavo Câmara Martins
                \tCarlos Eduardo Nogueira Morciani
                }""");
        System.out.println("databse : {https://www.kaggle.com/datasets/mohamedbakhet/amazon-books-reviews}");


        GraphController graphController = new GraphController();
        RandomGraphController randomGraphController = new RandomGraphController();
        Graph graph = graphController.loadPajekData("./src/Data/Filtered/dissimilarity_graph.net");
        Graph graphRand;
        RandomGraph randomGraph = new RandomGraph();
        graphController.graph(graph);


        System.out.println("\nRandom graph: ");

        String[] weights = new String[]{"325", "912", "47", "588", "731", "189", "456", "873", "624", "98",
                "712", "543", "281", "907", "165", "391", "842", "77", "669", "254",
                "138", "995", "430", "208", "604", "317", "889", "521", "760", "33",
                "476", "590", "841", "263", "902", "144", "777", "615", "82", "449",
                "370", "693", "951", "214", "508", "802", "167", "285", "941", "122"};


        graphRand = randomGraph.generateUnconnectedGraph(50,100, weights);

        graphRand.printGraph();

        graphRand.components();

        graphRand.isCyclic();

        boolean randIsConnected = graphRand.isConnected();
        boolean randIsEulerian = graphRand.isEulerian();

        if (randIsConnected) {
            System.out.println("The graph is connected!");
        } else {
            System.out.println("The graph is not connected!");
        }

        if (randIsEulerian){
            System.out.println("The graph is Eulerian!");
        } else {
            System.out.println("The graph is not Eulerian!");
        }

        graphRand.closenessCentrality();
        graphRand.betweennessCentrality();

//        randomGraphController.randomGraph(randomGraph);
    }
}