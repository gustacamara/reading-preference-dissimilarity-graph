import Controller.DatasetController;
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

    }
}