package molarivaccari;

import movida.commons.Collaboration;
import movida.commons.Movie;
import movida.commons.Person;

import java.util.ArrayList;
import java.util.List;

public class GraphNode implements Comparable<GraphNode> {
    Person p;
    List<Collaboration> Collabs;

    GraphNode(){
        Collabs = new ArrayList<Collaboration>();
    }

    public Person getPerson() {
        return p;
    }

    public void setPerson(Person p) {
        this.p = p;
    }

    public void addCollaboration(Collaboration c){
        if(!Collabs.contains(c))
            Collabs.add(c);
    }

    public Collaboration[] getAllCollaborations(){
        return Collabs.toArray(new Collaboration[Collabs.size()]);
    }

    public Collaboration getCollaborations(Person p2) {
        for (Collaboration c : Collabs)
            if (c.getActorA().compareTo(p2) == 0 || c.getActorB().compareTo(p2) == 0)
                return c;

        return null;
    }

    public boolean hasCollaborated(Person p2) {
        for (Collaboration c : Collabs)
            if (c.getActorA().compareTo(p2) == 0 || c.getActorB().compareTo(p2) == 0)
                return true;

        return false;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(!(o instanceof GraphNode))
            return false;

        GraphNode g = (GraphNode) o;
        return p.equals(g.getPerson());
    }

    @Override
    public int compareTo(GraphNode o) {
        return p.compareTo(o.getPerson());
    }
}
