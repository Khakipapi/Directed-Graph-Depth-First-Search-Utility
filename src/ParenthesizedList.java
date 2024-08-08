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

// This class maintain a Queue to trace orders of the nodes and their dependent nodes with proper spacing format
    Queue<String> stringQueue = new LinkedList<>();
    @Override
    public void processVertex(Vertex vertex)
    {
        stringQueue.add(vertex.toString());
    }
    @Override
    public void descendVertex(Vertex vertex)
    {
        stringQueue.add("(");
    }

    @Override
    public void ascendVertex(Vertex vertex)
    {
        stringQueue.add(")");
    }
    @Override
    public void cycleDetected()
    {
        stringQueue.add("*");
    }

    @Override
    public String toString()
    {
        String ans = "";
        ans += "( ";
        while (stringQueue.size() > 0) {
            String c = stringQueue.peek();
            stringQueue.remove();
            if (c == "(")
            {
                if (stringQueue.peek() == ")") {
                    stringQueue.remove();
                    continue;
                }
                else if (stringQueue.peek() == "*")
                {
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
}//end ParenthesizedList

