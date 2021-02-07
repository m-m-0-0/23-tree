package molarivaccari;

import molarivaccari.OrderType;
import movida.commons.Movie;

public class Node{
    Node next;
    Movie movie;
    public Node(Movie m){
        movie = m;
        next = null;
    }

    public int compareTo(Node n1) {
        return compareTo(n1, OrderType.TITLE);
    }

    public int compareTo(Node n1, OrderType order){
        switch(order){
            case TITLE:
            default:
                return movie.compareTo(n1.movie);
            case YEAR:
                return movie.getYear().compareTo(n1.movie.getYear());
            case DIRECTOR:
                return movie.getDirector().getName().compareTo(n1.movie.getDirector().getName());
            case VOTES:
                return movie.getVotes().compareTo(n1.movie.getVotes());
        }
    }
}