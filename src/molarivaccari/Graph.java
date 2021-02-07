package molarivaccari;

import movida.commons.Collaboration;
import movida.commons.Person;

import java.util.*;

public class Graph {
    List<GraphNode> nodes;

    public Graph(){
        nodes = new ArrayList<>();
    }

    public void addNode(GraphNode node) {
        if(!nodes.contains(node))
            nodes.add(node);
    }

    public ArrayList<GraphNode> getNodes() {
        return new ArrayList<>(nodes);
    }

    public ArrayList<Collaboration> getCollaborations() {
        Set<Collaboration> Cset = new HashSet<>();
        for(GraphNode node : nodes){
            Cset.addAll(Arrays.asList(node.getAllCollaborations()));
        }
        ArrayList<Collaboration> C = new ArrayList<>(Arrays.asList(Cset.toArray(new Collaboration[Cset.size()])));
        return C;
    }

    public boolean hasNode(GraphNode node) {
        return nodes.contains(node);
    }

    public GraphNode getPersonNode(Person p) {
        for(GraphNode N : nodes){
            if(N.getPerson().compareTo(p) == 0)
                return N;
        }
        return null;
    }

    public GraphNode createPersonNode(Person p){
        GraphNode N = new GraphNode();
        N.setPerson(p);
        nodes.add(N);
        return N;
    }

    public Collaboration[] getCollaborationsOf(Person p){
        GraphNode N = getPersonNode(p);
        if(N != null){
            return N.getAllCollaborations();
        } else
            return null;
    }
}
