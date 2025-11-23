package Controller;


import java.io.*;
import java.util.*;

public class DatasetController {
    private final String path;
    private final String filteredPath;
    private final String pajekPath;

    public DatasetController(String path, String filteredPath, String pajekPath) {
        this.path = path;
        this.filteredPath = filteredPath;
        this.pajekPath = pajekPath;
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

    public void createPajekFile() throws IOException {
        if( new File(pajekPath).exists()) {
            System.out.println("Pajek file already exists!");
            return;
        }
        BufferedReader bf = new BufferedReader(new FileReader(filteredPath));
        HashMap<String, HashMap<String, Double>> bookRatings = new HashMap<>();
        String line, userJ, userI, edgeKey;
        double ratingI, ratingJ, dissimilarity;
        String[] fields;
        int idI, idJ;

        // Read CSV: userID, BookId, bookTitle, score
        while ((line = bf.readLine()) != null) {
            if (line.isEmpty()) continue;

            fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if (fields.length < 4) continue;

            String userId = fields[0].trim();
            String bookTitle = fields[2].trim();
            Double score = Double.parseDouble(fields[3].trim());

            if (!bookRatings.containsKey(bookTitle)) {
                bookRatings.put(bookTitle, new HashMap<>());
            }
            bookRatings.get(bookTitle).put(userId, score);
        }
        bf.close();
        HashMap<String, Integer> userToId = new HashMap<>();
        int userId = 1;

        for (HashMap<String, Double> ratingsForBook : bookRatings.values()) {
            for (String user : ratingsForBook.keySet()) {
                if (!userToId.containsKey(user)) {
                    userToId.put(user, userId++);
                }
            }
        }
        HashMap<String, Double> edges = new HashMap<>();

        for (String book : bookRatings.keySet()) {
            HashMap<String, Double> ratingsForBook = bookRatings.get(book);
            ArrayList<String> usersList = new ArrayList<>(ratingsForBook.keySet());

            for (int i = 0; i < usersList.size(); i++) {
                for (int j = i + 1; j < usersList.size(); j++) {
                    userI = usersList.get(i);
                    userJ = usersList.get(j);
                    ratingI = ratingsForBook.get(userI);
                    ratingJ = ratingsForBook.get(userJ);
                    dissimilarity = Math.abs(ratingI - ratingJ) / 2.0;
                    idI = userToId.get(userI);
                    idJ = userToId.get(userJ);
                    edgeKey = Math.min(idI, idJ) + " " + Math.max(idI, idJ);

                    edges.merge(edgeKey, dissimilarity, Double::sum);
                }
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(pajekPath));

        writer.write("*Vertices " + userToId.size() + "\n");
        for (Map.Entry<String, Integer> entry : userToId.entrySet()) {
            writer.write(entry.getValue() + " \"" + entry.getKey() + "\"\n");
        }

        writer.write("*Edges\n");
        for (Map.Entry<String, Double> entry : edges.entrySet()) {
            writer.write(entry.getKey() + " " + entry.getValue() + "\n");
        }

        writer.close();
        System.out.println("Pajek file created: dissimilarity_graph.net");
        System.out.println("Total vertices: " + userToId.size());
        System.out.println("Total edges: " + edges.size());
    }
}
