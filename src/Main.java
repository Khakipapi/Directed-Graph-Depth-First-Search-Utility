/*
Jose Reyes
12/10/2022
UMGC
CMSC 350
Project 4
 */
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static DirectedGraph directedGraph = new DirectedGraph();
    public void readGraph()
    {
        JFileChooser pick = new JFileChooser(new File("."));

        int selection = pick.showOpenDialog(null);

//check if user selection is valid
        if (selection == JFileChooser.APPROVE_OPTION)
        {
            try {
                Scanner input = new Scanner(pick.getSelectedFile());
// Read in file
                while (input.hasNextLine())
                {
                    String edgeString = input.nextLine();
                    String[] edge = edgeString.split(" ");
// DFS start Node
                    if (directedGraph.startingNode == null)
                        directedGraph.startingNode = directedGraph.getVertex(edge[0]);
                    for (int i = 1; i < edge.length; i++)
                    {
                        directedGraph.addEdge(edge[0], edge[i]);
                    }
                }
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new Main().readGraph();
// Starting Depth First Search Utility to complete the DFS
        directedGraph.depthFirstSearch();
        System.out.println(directedGraph.parenthesizedList.toString());
        System.out.println(directedGraph.hierarchy.toString());
// Display the nodes that are unreachable
        directedGraph.unreachableClass();
    }
}//End Main