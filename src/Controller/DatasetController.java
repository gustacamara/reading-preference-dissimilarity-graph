package Controller;


import Model.Vertex;
import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatasetController {
    private final String path;
    private final String filteredPath;

    public DatasetController(String path, String filteredPath) {
        this.path = path;
        this.filteredPath = filteredPath;
    }

    public void filter() throws IOException {
        if( new File(filteredPath).exists()) {
            System.out.println("Dataset already filtered!");
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(path));
        FileWriter writer = new FileWriter(filteredPath);
        String line, filteredLine;
        String[] fields;
        try {
//            id = 0
//            Title = 1
//            user_id = 3
//            profileName = 4
//            review/score = 6
//            header.lenght() = 0-9
            while((line = reader.readLine()) != null){
                if(line.startsWith("Id,Title,Price")) continue;
                fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if(fields[3] == null) continue;
                try{
                    filteredLine = String.join(",", fields[3], fields[0], fields[1],  fields[6]);
                    filteredLine += "\n";
                    writer.write(filteredLine);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAdj() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(filteredPath));
        HashMap<String, Double> users = new HashMap<>();
        HashMap<String, String> books = new HashMap<>();
        String line;
        String[] fields;
//        userID, BookId, bookTitle,score
        while ((line = bf.readLine()) != null){
            if(line.isEmpty()) continue;
            fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if(fields.length < 3) continue;
            Double score = Double.parseDouble(fields[3].trim());
            users.merge(fields[0], score, Double::sum);
            books.put(fields[2], fields[0]);
        }


    }
}
