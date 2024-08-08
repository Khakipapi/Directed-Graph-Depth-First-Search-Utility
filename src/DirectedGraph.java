/*
Jose Reyes
12/10/2022
UMGC
CMSC 350
Project 4
 */
import java.util.ArrayList;

public class DirectedGraph extends Graph<Vertex> {

    public void addEdge(String a, String b) {
// Check if th source node already has some connected edges
        ArrayList<Vertex> list = adjacencyList.get(getVertex(a));
// Map it to a new Vertex and initialize
        if (list == null)
        {
            list = new ArrayList<>();
        }
        list.add(getVertex(b));
// update adjacency list
        adjacencyList.put(getVertex(a), list);
    }
    public Vertex getVertex(String a)
    {
        if (!vertices.containsKey(a))
        {
            vertices.put(a, new Vertex(a));
        }
        return vertices.get(a);
    }
}