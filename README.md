# Directed Graph Depth First Search Utility

## Introduction
This project implements a utility for reading and processing directed graphs using Depth First Search (DFS). It reads graph data from a file, constructs the graph, performs DFS, and displays the results in both parenthesized and hierarchical formats. Additionally, it identifies and displays nodes that are unreachable within the graph.

## Table of Contents
- [Introduction](#introduction)
- [Usage](#usage)
- [Features](#features)
- [Dependencies](#dependencies)
- [Documentation](#documentation)
- [Examples](#examples)
- [Troubleshooting](#troubleshooting)
- [Contributors](#contributors)
- [License](#license)

## Usage
1. Launch the application:
    ```sh
    java -cp bin Main
    ```
2. Use the file chooser to select a text file containing the graph data, formatted with each line representing an edge in the graph, with nodes separated by spaces.
3. The program reads the graph data, performs a DFS, and outputs the results in both parenthesized and hierarchical formats.
4. The program also displays any unreachable nodes.

## Features
- Read graph data from a file using a file chooser.
- Construct a directed graph from the input data.
- Perform Depth First Search (DFS) on the graph.
- Display the results of the DFS in both parenthesized and hierarchical formats.
- Identify and display unreachable nodes in the graph.
- Provide an interface for DFS actions to customize vertex processing.

## Dependencies
- Java Development Kit (JDK) 8 or higher

## Documentation
The code includes comments explaining the functionality of each method and class. Refer to the source code for detailed documentation.

## Examples
Consider a text file `graph.txt` with the following content:
```
A B C
B D
C E
```
This represents a graph with edges A->B, A->C, B->D, and C->E. When processed by the utility, the output displays the DFS traversal in both parenthesized and hierarchical formats, along with any unreachable nodes.

## Troubleshooting
- Ensure the input file is correctly formatted with each line representing an edge.
- If the file is not being read, check the file path and ensure it is accessible.
- Ensure Java is installed and the `javac` and `java` commands are available in your system's PATH.

## Contributors
- Jose Reyes

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

### Additional Information

#### Interface: `DFSActions`
```java
/*
Jose Reyes
12/10/2022
UMGC
CMSC 350
Project 4
 */
public interface DFSActions<V> {
    void processVertex(V vertex);
    void descendVertex(V vertex);
    void ascendVertex(V vertex);
    void cycleDetected();
}
```

#### Class: `DirectedGraph`
```java
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
        ArrayList<Vertex> list = adjacencyList.get(getVertex(a));
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(getVertex(b));
        adjacencyList.put(getVertex(a), list);
    }

    public Vertex getVertex(String a) {
        if (!vertices.containsKey(a)) {
            vertices.put(a, new Vertex(a));
        }
        return vertices.get(a);
    }
}
```

#### Class: `Graph`
```java
/*
Jose Reyes
12/10/2022
UMGC
CMSC 350
Project 4
 */
import java.util.*;

public class Graph<V> {
    public V startingNode = null;
    Map<String, V> vertices = new HashMap<>();
    Map<V, ArrayList<V>> adjacencyList = new HashMap<>();
    Set<V> visited = new HashSet<>();
    ParenthesizedList hierarchy = new ParenthesizedList();
    Hierarchy parenthesizedList = new Hierarchy();
    boolean cycle;
    Set<V> discovered = new HashSet<>();

    public void depthFirstSearch() {
        cycle = false;
        search(startingNode);
    }

    private void search(V node) {
        if (discovered.contains(node)) {
            cycle = true;
            hierarchy.cycleDetected();
            parenthesizedList.cycleDetected();
            return;
        }
        hierarchy.processVertex((Vertex) node);
        parenthesizedList.processVertex((Vertex) node);
        hierarchy.descendVertex((Vertex) node);
        parenthesizedList.descendVertex((Vertex) node);
        discovered.add(node);
        visited.add(node);
        ArrayList<V> list = adjacencyList.get(node);
        if (list != null) {
            for (V a : list) search(a);
        }
        hierarchy.ascendVertex((Vertex) node);
        parenthesizedList.ascendVertex((Vertex) node);
        discovered.remove(node);
    }

    public void unreachableClass() {
        for (Map.Entry<V, ArrayList<V>> entry : adjacencyList.entrySet()) {
            if (entry.getValue().size() > 0) {
                if (!visited.contains(entry.getKey())) {
                    System.out.println(entry.getKey() + " is unreachable");
                    visited.add(entry.getKey());
                }
                for (V vertex : entry.getValue()) {
                    if (!visited.contains(vertex)) {
                        System.out.println(vertex + " is unreachable");
                        visited.add(vertex);
                    }
                }
            }
        }
    }
}
```

#### Class: `Hierarchy`
```java
/*
Jose Reyes
12/10/2022
UMGC
CMSC 350
Project 4
 */
import java.util.LinkedList;
import java.util.Queue;

public class Hierarchy implements DFSActions<Vertex> {
    Queue<String> stringQueue = new LinkedList<>();

    @Override
    public void processVertex(Vertex vertex) {
        stringQueue.add(vertex.toString());
    }

    @Override
    public void descendVertex(Vertex vertex) {
        stringQueue.add("(");
    }

    @Override
    public void ascendVertex(Vertex vertex) {
        stringQueue.add(")");
    }

    @Override
    public void cycleDetected() {
        stringQueue.add("*");
    }

    @Override
    public String toString() {
        String ans = "";
        int size2 = 0;
        while (stringQueue.size() > 0) {
            String string2 = stringQueue.peek();
            stringQueue.remove();
            if (string2.equals("(")) {
                if (stringQueue.peek().equals(")")) {
                    stringQueue.remove();
                    continue;
                } else if (stringQueue.peek().equals("*")) {
                    ans += stringQueue.peek() + " ";
                    stringQueue.remove();
                    stringQueue.remove();
                    continue;
                }
            }
            if (string2.equals("(")) size2++;
            else if (string2.equals(")")) size2--;
            if (string2.equals("(") || string2.equals(")")) continue;
            if (!string2.equals("*")) ans += "\n";
            for (int i = 0; i < size2; i++) ans += "\t";
            ans += string2 + " ";
        }
        ans += "\n";
        return ans;
    }
}
```

#### Class: `ParenthesizedList`
```java
/*
Jose Reyes
12/10/2022
UMGC
CMSC 350
Project 4
 */
import java.util.LinkedList;
import java.util.Queue;

public class ParenthesizedList implements DFSActions<Vertex> {
    Queue<String> stringQueue = new LinkedList<>();

    @Override
    public void processVertex(Vertex vertex) {
        stringQueue.add(vertex.toString());
    }

    @Override
    public void descendVertex(Vertex vertex) {
        stringQueue.add("(");
    }

    @Override
    public void ascendVertex(Vertex vertex) {
        stringQueue.add(")");
    }

    @Override
    public void cycleDetected() {
        stringQueue.add("*");
    }

    @Override
    public String toString() {
        String ans = "( ";
        while (stringQueue.size() > 0) {
            String c = stringQueue.peek();
            stringQueue.remove();
            if (c.equals("(")) {
                if (stringQueue.peek().equals(")")) {
                    stringQueue.remove();
                    continue;
                } else if (stringQueue.peek().equals("*")) {
                    ans += stringQueue.peek() + " ";
                    stringQueue.remove();
                    stringQueue.remove();
                    continue;
                }
            }
            ans += c + " ";
        }
        ans += ")\n";
        return ans;
    }
}
```

#### Class: `Vertex`
```java
/*
Jose Reyes
12/10/2022
UMGC
CMSC 350
Project 4
 */
public class Vertex {
    private String name;

    public Vertex(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
```
