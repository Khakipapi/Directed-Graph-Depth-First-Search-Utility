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
        int size2 = 0;
        while (stringQueue.size() > 0)
        {
            String string2 = stringQueue.peek();
            stringQueue.remove();
            if (string2 == "(") {
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
            if(string2=="(")
                size2++;
            else if(string2==")")
                --size2;
            if(string2=="(" || string2==")")
                continue;
            if(string2!="*")
                ans += "\n";
            for (int i = 0; i < size2; i++) {
                ans += "\t";
            }
            ans += string2 + " " ;
        }
        ans += "\n";
        return ans;
    }
    private boolean isAlpha(String string)
    {
        return string != "(" && string != ")";
    }
}//End Hierarchy


