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
        if(new File(pajekPath).exists()) {
            System.out.println("Pajek file already exists!");
            return;
        }
        HashMap<String, Double> users = new HashMap<>();
        HashMap<String, ArrayList<String>> books = new HashMap<>();
        BufferedReader bf = new BufferedReader(new FileReader(filteredPath));
        String line;
        String[] fields;

        // Read CSV: userID, BookId, bookTitle, score
        while ((line = bf.readLine()) != null) {
            if (line.isEmpty()) continue;

            fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            String userId = fields[0].trim();
            String bookTitle = fields[2].trim();
            Double score = Double.parseDouble(fields[3].trim());
            if( userId.isEmpty() || bookTitle.isEmpty() ) continue;

            users.merge(userId, score, Double::sum);
            if (!books.containsKey(bookTitle)) {
                books.put(bookTitle, new ArrayList<>());
            }
            books.get(bookTitle).add(userId);
        }
        bf.close();

        System.out.println("Total users: " + users.size());
        System.out.println("Total books: " + books.size());

        System.out.println("Mapping users to IDs...");
        HashMap<String, Integer> userToId = new HashMap<>();
        int userId = 1;
        for (String user : users.keySet()) {
            userToId.put(user, userId++);
        }
        System.out.println("Writing Pajek file...");
        BufferedWriter writer = new BufferedWriter(new FileWriter(pajekPath), 65536);

        writer.write("*Vertices " + userToId.size() + "\n");
        for (Map.Entry<String, Integer> entry : userToId.entrySet()) {
            writer.write(entry.getValue() + " \"" + entry.getKey() + "\"\n");
        }
        writer.write("*Edges\n");

        long edgeCount = 0;
        int bookCount = 0;
        for (Map.Entry<String, ArrayList<String>> entry : books.entrySet()) {
            ArrayList<String> readers = entry.getValue();

            for (int i = 0; i < readers.size(); i++) {
                for (int j = i + 1; j < readers.size(); j++) {
                    String userI = readers.get(i);
                    String userJ = readers.get(j);
                    Double scoreI = users.get(userI);
                    Double scoreJ = users.get(userJ);
                    double dissimilarity = Math.abs(scoreI - scoreJ) / 2.0;
                    int idI = userToId.get(userI);
                    int idJ = userToId.get(userJ);

                    writer.write(idI + " " + idJ + " " + dissimilarity + "\n");
                    edgeCount++;
                }
            }

            bookCount++;
            if (bookCount % 100 == 0) {
                System.out.println("Processed " + bookCount + " books, " + edgeCount + " edges");
                writer.flush();
            }
        }

        writer.close();
        System.out.println("Pajek file created: " + pajekPath);
        System.out.println("Total vertices: " + userToId.size());
        System.out.println("Total edges: " + edgeCount);
    }
}
