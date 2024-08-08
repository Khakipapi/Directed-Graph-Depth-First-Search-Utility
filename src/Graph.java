/*
Jose Reyes
12/10/2022
UMGC
CMSC 350
Project 4
 */
import java.util.*;

public class Graph<V>
{
// Start node
    public V startingNode = null;
// Map all the vertex
    Map<String, V> vertices = new HashMap<>();
// Adjacency list
    Map<V, ArrayList<V>> adjacencyList = new HashMap<>();
    Set<V> visited = new HashSet<>();

    ParenthesizedList hierarchy = new ParenthesizedList();
    Hierarchy parenthesizedList = new Hierarchy();

// keep track if the graph contain a circle
    boolean cycle;
    Set<V> discovered = new HashSet<>();

    public void depthFirstSearch()
    {
        cycle = false;
        search(startingNode);
    }
    private void search(V node) {
// check node
        if (discovered.contains(node))
        {
            cycle = true;
            hierarchy.cycleDetected();
            parenthesizedList.cycleDetected();
            return;
        }

// Add vertex
        hierarchy.processVertex((Vertex) node);
        parenthesizedList.processVertex((Vertex) node);

// Descend Vertex
        hierarchy.descendVertex((Vertex) node);
        parenthesizedList.descendVertex((Vertex) node);
// add node to discovery list
        discovered.add(node);
// mark the node as visited
        visited.add(node);
// find all children
        ArrayList<V> list = adjacencyList.get(node);
        if (list != null)
        {
            for (V a : list)
                search(a);
        }
// Ascend Vertex
        hierarchy.ascendVertex((Vertex) node);
        parenthesizedList.ascendVertex((Vertex) node);
// remove discovered node
        discovered.remove(node);
    }
    private V getVertex(String a) {
        //get string a
        return vertices.get(a);
    }

    public void unreachableClass(){
        for (Map.Entry<V, ArrayList<V>> entry : adjacencyList.entrySet())
        {
            if(entry.getValue().size()>0){
                if(!visited.contains(entry.getKey())){
                    System.out.println(entry.getKey() + " is unreachable");
                    visited.add(entry.getKey());
                }
                for (V vertex : entry.getValue())
                {
                    if(!visited.contains(vertex))
                    {
                        System.out.println(vertex + " is unreachable");
                        visited.add(vertex);
                    }
                }
            }
        }
    }//End unreachableClass
}//End Graph