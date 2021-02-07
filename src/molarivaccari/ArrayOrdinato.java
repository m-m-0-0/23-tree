package molarivaccari;

import movida.commons.Movie;
import movida.commons.SortingAlgorithm;

public class ArrayOrdinato {
    Node lista;

    OrderType order = OrderType.TITLE;
    SortingAlgorithm sort = SortingAlgorithm.SelectionSort;

    public void setSort(SortingAlgorithm s){
        sort = s;
    }

    public void orderBy(OrderType o){
        order = o;
        switch(sort){
            case SelectionSort:
            default:
                Sorting.selectionSort(this);
                break;
            case HeapSort:
                Sorting.heapSort(this);
                break;
        }
    }

    public int size(){
        if(lista == null)
            return 0;

        int n = 1;
        Node current = lista;
        while(current != null){
            current = current.next;
            n++;
        }
        return n;
    }

    public String toString(){
        Node current;
        StringBuilder s = new StringBuilder();
        for(int i=0; i<size(); i++){
            current = getNode(i);
            s.append("Title: ").append(current.movie.getTitle());
            s.append("\nYear: ").append(current.movie.getYear());
            s.append("\nDirector: ").append(current.movie.getDirector().getName());

            String[] cast = new String[current.movie.getCast().length];
            for(int j=0; j<current.movie.getCast().length; j++)
                cast[j] = current.movie.getCast()[j].getName();

            s.append("\nCast: ").append(String.join(", ", cast));
            s.append("\nVotes: ").append(current.movie.getVotes()).append("\n\n");
        }
        return s.toString();
    }

    public void swapNodes(int i1, int i2){
        Movie m1 = getNode(i1).movie;
        getNode(i1).movie = getNode(i2).movie;
        getNode(i2).movie = m1;
    }

    public void addMovie(Movie m){
        Node new_n = new Node(m);
        if(lista == null)
            lista = new_n;
        else{
            Node current = lista;
            Node previous = null;
            int comp;
            while(current != null){
                comp = current.compareTo(new_n, order);
                if(comp < 0) {
                    previous = current;
                    current = current.next;
                }
                else{
                    new_n.next = current;
                    if(previous == null)
                        lista = new_n;
                    else
                        previous.next = new_n;
                    return;
                }
            }
            if(current == null && size() != 1)
                previous.next = new_n;
        }
    }

    public int deleteMovie(String title){
        int i = searchMovieIndex(title);
        return deleteNode(i);
    }

    protected Node getNode(int index){
        if(index < 0 || index >= size())
            return null;

        Node current = lista;
        for (int i = 0; i != index; i++)
            current = current.next;

        return current;
    }

    public int setNode(int index, Node n){
        if(index < 0 || index >= size())
            return -1;

        getNode(index).movie = n.movie;
        return 0;
    }

    private int deleteNode(int index){
        if(lista == null || index < 0 || index >= size())
            return -1;

        if(index == 0) {
            lista = lista.next;
            return 0;
        }

        if(index == size()-1){
            getNode(index-1).next = null;
            return 0;
        }

        getNode(index-1).next = getNode(index+1);
        return 0;
    }

    public Movie searchMovie(String title) {
        int start = 0;
        int end = size()-1;
        return searchMovieBinary(title, start, end);
    }

    private int searchMovieIndex(String title) {
        int start = 0;
        int end = size()-1;
        return searchMovieBinaryIndex(title, start, end);
    }

    private Movie searchMovieBinary(String title, int start, int end) {
        if(end <= start)
            return null;

        System.out.println(start + " " + end);
        int m = (start + end) / 2 ;
        Movie current = getNode(m).movie;
        int comp = current.getTitle().compareTo(title);

        if (comp == 0)
            return current;
        else {
            if (comp > 0)
                return searchMovieBinary(title, start, m-1);
            else
                return searchMovieBinary(title, m+1, end);
        }
    }

    private int searchMovieBinaryIndex(String title, int start, int end) {
        if(end <= start)
            return -1;

        int m = (start + end) / 2;
        Movie current = getNode(m).movie;
        int comp = current.getTitle().compareTo(title);

        if (comp == 0)
            return m;
        else {
            if (comp > 0)
                return searchMovieBinaryIndex(title, start, m-1);
            else
                return searchMovieBinaryIndex(title, m+1, end);
        }
    }

    public Movie[] getAllMovies() {
        Movie[] M = new Movie[size()];
        for(int i=0; i<size(); i++){
            M[i] = getNode(i).movie;
        }
        return M;
    }
}
