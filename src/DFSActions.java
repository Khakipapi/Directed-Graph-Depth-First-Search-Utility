/*
Jose Reyes
12/10/2022
UMGC
CMSC 350
Project 4
 */
public interface DFSActions<V>
{
    public void processVertex(V vertex);
    public void descendVertex(V vertex);
    public void ascendVertex(V vertex);
    public void cycleDetected();
}