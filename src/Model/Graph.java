package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Vertex [] vertices;
    private String[] labels;
    private int numberOfVertices;

    public Graph(int numberOfVertices) {
        this.vertices = new Vertex[numberOfVertices];
        this.labels = new String[numberOfVertices];
        this.numberOfVertices = numberOfVertices;
    }

    public Graph(int numberOfVertices, String[] labels) {
        this.labels = labels;
        this.numberOfVertices = numberOfVertices;
        this.vertices = new Vertex[numberOfVertices];
    }

    public Graph(String[] labels) {
        this.vertices = new Vertex[numberOfVertices];
        this.labels = labels;
        this.numberOfVertices = labels.length;
    }

    public void setLabels(int id, String label) {
        if (id >= 0 && id < numberOfVertices) {
            labels[id] = label;
        }
    }

    public void setAdj(int origin, int target, int weight) {
        if (origin < 0 || origin >= numberOfVertices || target < 0 || target >= numberOfVertices) {
            return;
        }

        Vertex newVertex = new Vertex(target, weight);
        if (vertices[origin] == null) {
            vertices[origin] = newVertex;
        } else {
            Vertex current = vertices[origin];
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newVertex);
        }

        Vertex newVertexReverse = new Vertex(origin, weight);
        if (vertices[target] == null) {
            vertices[target] = newVertexReverse;
        } else {
            Vertex current = vertices[target];
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newVertexReverse);
        }
    }

    public void searchComponents(int start, List<Integer> components, List<Integer> visited) {
        visited.add(start);
        components.add(start);

        Vertex actual = vertices[start];

        while(actual != null) {
            int target = actual.getId();

            if(!visited.contains(target)) {
                searchComponents(target, components, visited);
            }

            actual = actual.getNext();
        }
    }

    public int components() {
        List<Integer> visited = new ArrayList<>();
        int countComponents = 0;

        for(int i = 0; i < numberOfVertices; i++) {
            if(!visited.contains(i)) {

                List<Integer> components = new ArrayList<>();
                searchComponents(i, components, visited);

                System.out.print("\nComponents " + countComponents + ": ");

                for (int j = 0; j < components.size(); j++){
                    System.out.print(labels[components.get(j)]);
                    if(j < components.size() - 1) System.out.print(", ");
                }
                System.out.println();
                countComponents++;
            }
        }

        System.out.println("Total components: " + countComponents);
        System.out.println();

        return countComponents;
    }

    public boolean isConnected() {
        List<Integer> visited = new ArrayList<>();
        int countComponents = 0;

        for(int i= 0; i < numberOfVertices; i++) {
            if(!visited.contains(i)) {
                List<Integer> components = new ArrayList<>();
                searchComponents(i, components, visited);

                countComponents++;
            }
        }

        if (countComponents != 1) {
            return false;
        }

        return true;
    }

    public void calculateDegree(Map<String, Integer> countDegree) {
        for(int i = 0; i < numberOfVertices; i++) {
            Vertex origin = vertices[i];
            int count = 0;

            Vertex actual = origin;

            while(actual != null) {
                count++;
                actual = actual.getNext();
            }

            countDegree.put(labels[i], count);
        }
    }

    public boolean isEulerian () {
        Map<String, Integer> countDegree = new HashMap<>();
        int count = 0;

        if(!isConnected()) {
            return false;
        }

        calculateDegree(countDegree);

        for (Map.Entry<String, Integer> entry : countDegree.entrySet()) {
            if(entry.getValue() % 2 != 0) {
                count++;
            }
        }

        if (count == 2 || count == 0) {
            return true;
        }

        return false;
    }

    public boolean dfsCycle(int start, List<Integer> visited) {
        visited.add(start);

        Vertex origin = vertices[start];
        Vertex actual = origin.getNext();

        while (actual != null) {
            int target = actual.getId();

            if(visited.contains(target) && visited.size() > 1) {
                int lastVertex = visited.get(visited.size() - 2);
                if (target != lastVertex) {
                    return true;
                }
            }

            if (!visited.contains(target)) {
                boolean existsCycle = dfsCycle(target, visited);
                if (existsCycle) {
                    return true;
                }
            }

            actual = actual.getNext();
        }

        visited.remove(visited.size() - 1);

        return false;
    }

    public void isCyclic() {
        for (int i = 0; i < numberOfVertices; i++) {
            if (vertices[i] != null) {
                List<Integer> visited = new ArrayList<>();

                boolean cycle = dfsCycle(i, visited);

                if(cycle) {
                    System.out.println("The graph is cyclical!");
                    return;
                }
            }
        }

        System.out.println("The graph is acyclic!");
    }

    public int[] dijkstra(int start, double[] distanceValues) {
        double[] distance = new double[numberOfVertices];
        boolean[] permanentDistance = new boolean[numberOfVertices];
        int[] path = new int[numberOfVertices];
        int currentVertex;
        double minDistance;
        double newDistance;

        for (int i = 0; i < numberOfVertices; i++) {
            distance[i] = Double.POSITIVE_INFINITY;
            permanentDistance[i] = false;
            path[i] = -1;
        }

        distance[start] = 0;

        for (int i = 0; i < numberOfVertices - 1; i++) {
            minDistance = Double.POSITIVE_INFINITY;
            currentVertex = -1;

            for (int j = 0; j < numberOfVertices; j++) {
                if(!permanentDistance[j] && distance[j] < minDistance) {
                    minDistance = distance[j];
                    currentVertex = j;
                }
            }

            if (currentVertex == -1) {
                break;
            }

            permanentDistance[currentVertex] = true;

            Vertex actual = vertices[currentVertex];

            while (actual != null) {
                int adj = actual.getId();
                int weight = actual.getWeight();

                if (!permanentDistance[adj]) {
                    newDistance = distance[currentVertex] + weight;

                    if (newDistance < distance[adj]) {
                        distance[adj] = newDistance;
                        path[adj] = currentVertex;
                    }
                }

                actual = actual.getNext();
            }
        }

        for (int d = 0; d < numberOfVertices; d++) {
            distanceValues[d] = distance[d];
        }

        return path;
    }

    public double sumDistances(int start) {
        double[] distances = new double[numberOfVertices];

        dijkstra(start, distances);

        double sum = 0;


        for(int i = 0; i < numberOfVertices; i++) {
            if (i != start && distances[i] != Double.POSITIVE_INFINITY) {
                sum += distances[i];
            }
        }

        if(sum == 0) {
            return 0;
        }


        return 1/sum;
    }

    public void closenessCentrality () {
        System.out.println(" ");

        HashMap<Integer, Double> mapCloseness = new HashMap<>();

        for (int i = 0; i < numberOfVertices; i++) {
            double closeness = sumDistances(i);
            mapCloseness.put(i, closeness);

        }

        mapCloseness.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(20)
                .forEach(entry ->
                        System.out.printf("Vertex closeness %s: %.4f\n", labels[entry.getKey()], entry.getValue()));

        System.out.println(" ");
    }

    public void betweennessCentrality() {
        double[] betwenness = new double[numberOfVertices];

        for (int i = 0; i < numberOfVertices; i++) {
            betwenness[i] = 0;
        }

        for (int origin = 0; origin < numberOfVertices; origin++) {
            if (vertices[origin] == null) {
                continue;
            }
            double[] distances = new double[numberOfVertices];

            int[] paths = dijkstra(origin, distances);

            for (int target = 0; target < numberOfVertices; target++) {
                if (origin == target || vertices[target] == null) {
                    continue;
                }

                int actualPath = paths[target];

                while (actualPath != -1 && actualPath != origin) {
                    betwenness[actualPath]++;
                    actualPath = paths[actualPath];
                }
            }
        }

        System.out.println(" ");

        HashMap<Integer, Double> mapBetwennes = new HashMap<>();

        for (int j = 0; j < numberOfVertices; j++) {
            if (vertices[j] != null) {

                double calc = 2 * (betwenness[j] / ((numberOfVertices - 1) * (numberOfVertices - 2)));

                mapBetwennes.put(j,calc);

            }
        }

        mapBetwennes.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(20)
                .forEach(entry ->
                        System.out.printf("Vertex betweenness %s: %.4f\n", labels[entry.getKey()], entry.getValue()));

        System.out.println(" ");
    }

    public void printGraph() {
        for (int i = 0; i < numberOfVertices; i++) {
            System.out.print(labels[i] + " -> ");
            Vertex actual = vertices[i];

            while (actual != null) {
                System.out.print(labels[actual.getId()] + "(" + actual.getWeight() + ")");
                actual = actual.getNext();
                if (actual != null) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
        System.out.println(" ");
    }


}
