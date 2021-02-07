package molarivaccari;

import movida.commons.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static movida.commons.SortingAlgorithm.SelectionSort;

public class MovidaCore implements IMovidaDB, IMovidaConfig, IMovidaSearch, IMovidaCollaborations {

    MapImplementation map = MapImplementation.ArrayOrdinato;
    SortingAlgorithm sort = SelectionSort;
    ArrayOrdinato Movies_AO = new ArrayOrdinato();
    Albero23 Movies_23 = new Albero23(null);
    Graph G = new Graph();

    public void printAll() {
        System.out.print(getMoviesAO().toString());
    }

    private void setMovies(ArrayOrdinato movies) {
        Movies_AO = movies;
    }

    private void setMovies(Albero23 movies) {
        Movies_23 = movies;
    }

    private ArrayOrdinato getMoviesAO() {
        return Movies_AO;
    }

    private Albero23 getMovies23() {
        return Movies_23;
    }

    private void addMovie(Movie m) {
        switch (map) {
            case ArrayOrdinato:
                getMoviesAO().addMovie(m);
                break;
            case Alberi23:
                getMovies23().addMovie(m);
                setMovies(getMovies23().getRoot());
        }
    }

    @Override
    public boolean setSort(SortingAlgorithm s) {
        getMoviesAO().setSort(s);
        sort = s;
        System.out.println("selezionato " + s);
        return true;
    }

    @Override
    public boolean setMap(MapImplementation m) {
        map = m;
        System.out.println("selezionato " + m);
        return true;
    }

    public void setOrder(OrderType o) {
        Movies_AO.orderBy(o);
    }

    @Override
    public void loadFromFile(File f) {
        int n = 0;
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("file not found: " + f.getName());
            System.exit(-1);
        }

        String title, year, director, votes;
        String[] cast;
        Movie m;
        while (s.hasNextLine()) {
            if (n != 0)
                s.nextLine();
            title = s.nextLine().split("Title: ")[1];
            year = s.nextLine().split("Year: ")[1];
            director = s.nextLine().split("Director: ")[1];
            cast = s.nextLine().split("Cast: ")[1].split(", ");
            votes = s.nextLine().split("Votes: ")[1];

            m = new Movie(title,
                    Integer.parseInt(year),
                    Integer.parseInt(votes),
                    new Person[cast.length],
                    new Person(director)
            );
            Person[] m_cast = m.getCast();
            for (int i = 0; i < m_cast.length; i++) {
                m_cast[i] = new Person(cast[i]);
                if(G.getPersonNode(m_cast[i]) == null)
                    G.createPersonNode(m_cast[i]);
            }

            for(Person p1 : m_cast){
                for(Person p2 : m_cast){
                    if(p1.compareTo(p2) == 0)
                        continue;
                    GraphNode Pa = G.getPersonNode(p1);
                    GraphNode Pb = G.getPersonNode(p2);
                    Collaboration c = Pa.getCollaborations(p2);
                    if(c != null){
                        c.addMovie(m);
                    } else {
                        c = new Collaboration(p1, p2);
                        c.addMovie(m);
                        Pa.addCollaboration(c);
                        Pb.addCollaboration(c);
                    }
                }
            }
            addMovie(m);
            n++;
        }
    }

    @Override
    public void saveToFile(File f) {

    }

    @Override
    public void clear() {
        setMovies(new ArrayOrdinato());
        setMovies(new Albero23(null));
    }

    @Override
    public int countMovies() {
        switch (map) {
            case ArrayOrdinato:
                return getMoviesAO().size();
            case Alberi23:
                return getMovies23().size();
        }
        return -1;
    }

    @Override
    public int countPeople() {
        Set<Person> people = new HashSet<>();
        for(Movie m : getAllMovies()) {
            people.addAll(Arrays.asList(m.getCast()));
        }
        return people.size();
    }

    @Override
    public boolean deleteMovieByTitle(String title) {
        switch (map) {
            case ArrayOrdinato:
                return getMoviesAO().deleteMovie(title) >= 0;
            case Alberi23:
                return getMovies23().deleteMovie(title) >= 0;
        }
        return true;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        switch (map) {
            case ArrayOrdinato:
                return getMoviesAO().searchMovie(title);
        }
        return null;
    }

    @Override
    public Person getPersonByName(String name) {
        Movie[] M = null;
        Person[] P;
        switch (map) {
            case ArrayOrdinato:
                M = getMoviesAO().getAllMovies();
                break;
            case Alberi23:
                M = getMovies23().getAllMovies();
                break;
        }
        for (int i = 0; i < M.length; i++) {
            P = M[i].getCast();
            for (int j = 0; j < P.length; j++) {
                if (P[i].getName().equals(name))
                    return P[i];
            }
        }
        return null;
    }

    @Override
    public Movie[] getAllMovies() {
        switch (map) {
            case ArrayOrdinato:
                return getMoviesAO().getAllMovies();
            case Alberi23:
                return getMovies23().getAllMovies();
        }
        return null;
    }

    @Override
    public Person[] getAllPeople() {
        Movie[] M = getAllMovies();
        Set<Person> P = new HashSet<>();
        for (Movie movie : M)
            P.addAll(Arrays.asList(movie.getCast()));

        return (Person[]) P.toArray();
    }

    @Override
    public Movie[] searchMoviesByTitle(String title) {
        Movie[] M = getAllMovies();
        ArrayOrdinato A = new ArrayOrdinato();
        for (Movie movie : M)
            if (movie.getTitle().contains(title))
                A.addMovie(movie);

        return A.getAllMovies();
    }

    @Override
    public Movie[] searchMoviesInYear(Integer year) {
        Movie[] M = getAllMovies();
        ArrayOrdinato A = new ArrayOrdinato();
        for (Movie movie : M)
            if (movie.getYear().equals(year))
                A.addMovie(movie);

        return A.getAllMovies();
    }

    @Override
    public Movie[] searchMoviesDirectedBy(String name) {
        Movie[] M = getAllMovies();
        ArrayOrdinato A = new ArrayOrdinato();
        for (Movie movie : M)
            if (movie.getDirector().getName().equals(name))
                A.addMovie(movie);

        return A.getAllMovies();
    }

    @Override
    public Movie[] searchMoviesStarredBy(String name) {
        Movie[] M = getAllMovies();
        ArrayOrdinato A = new ArrayOrdinato();
        for (Movie movie : M) {
            for (int j = 0; j < movie.getCast().length; j++)
                if (movie.getCast()[j].getName().equals(name))
                    A.addMovie(movie);
        }
        return A.getAllMovies();
    }

    @Override
    public Movie[] searchMostVotedMovies(Integer N) {
        Movie[] M;
        ArrayOrdinato A = new ArrayOrdinato();
        switch (map) {
            case ArrayOrdinato:
                getMoviesAO().orderBy(OrderType.VOTES);
                M = getAllMovies();
                for (int i = 0; i < N && i < M.length; i++)
                    A.addMovie(M[i]);
        }
        return A.getAllMovies();
    }

    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        Movie[] M;
        ArrayOrdinato A = new ArrayOrdinato();
        switch (map) {
            case ArrayOrdinato:
                getMoviesAO().orderBy(OrderType.YEAR);
                M = getAllMovies();
                for (int i = 0; i < N && i < M.length; i++)
                    A.addMovie(M[i]);
        }
        return A.getAllMovies();
    }

    @Override
    public Person[] searchMostActiveActors(Integer N) {
        Movie M[] = getAllMovies();
        Map<Person, Integer> C = new HashMap<>();
        for (Movie movie : M)
            for (Person p : movie.getCast())
                if (!C.containsKey(p))
                    C.put(p, 1);
                else
                    C.replace(p, C.get(p) + 1);

        Person[] keys = (Person[]) C.keySet().toArray(new Person[C.size()]);
        Integer[] values = (Integer[]) C.values().toArray(new Integer[C.size()]);
        ArrayList<Pair<Person, Integer>> A = new ArrayList<>(C.size());
        for (int i = 0; i < keys.length; i++) {
            Person p1 = keys[i];
            int n = values[i];
            A.add(i, new Pair<Person, Integer>(p1, n));
        }

        switch(sort){
            case SelectionSort:
                Sorting.selectionSort(A);
                break;
            case HeapSort:
                Sorting.heapSort(A);
                break;
        }
        Person[] active = new Person[N];
        int j=0;
        for (int i = C.size()-1; i >= A.size()-N && i >= 0; i--)
            active[j++] = A.get(i).key;

        return active;
    }

    @Override
    public Person[] getDirectCollaboratorsOf(Person actor) {
        GraphNode N = G.getPersonNode(actor);
        Collaboration[] C = N.getAllCollaborations();
        Person[] P = new Person[C.length];
        for(int i=0; i<C.length; i++)
            P[i] = C[i].getCollaborator(actor);

        return P;
    }

    @Override
    public Person[] getTeamOf(Person actor) {
        Set<Person> P = new HashSet<Person>();
        Set<Person> visited = new HashSet<Person>();
        Stack<Person> toVisit = new Stack<Person>();
        toVisit.add(actor);
        visited.add(actor);
        while(!toVisit.empty()) {
            GraphNode N = G.getPersonNode(toVisit.pop());
            for (Collaboration c : N.getAllCollaborations()) {
                Person p = c.getCollaborator(N.getPerson());
                P.add(p);
                if(!visited.contains(p)) {
                    toVisit.add(p);
                    visited.add(p);
                }
            }
        }
        return P.toArray(new Person[P.size()]);
    }

    @Override
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {
        Person[] Team = getTeamOf(actor);
        Graph TeamGraph = new Graph();
        Graph Tree = new Graph();

        for (Person p : Team) {
            GraphNode N = G.getPersonNode(p);
            ;
            if (N != null)
                TeamGraph.addNode(N);
        }

        UnionFind<GraphNode> UF = new UnionFind<GraphNode>();
        for (GraphNode N : TeamGraph.getNodes()) {
            UF.makeSet(N);
        }

        ArrayList<Collaboration> C = TeamGraph.getCollaborations();
        switch (sort) {
            case SelectionSort:
                Sorting.selectionSort(C);
                break;
            case HeapSort:
                Sorting.heapSort(C);
        }
        Collections.reverse(C);
        for(Collaboration c : C){
            GraphNode u = TeamGraph.getPersonNode(c.getActorA());
            GraphNode v = TeamGraph.getPersonNode(c.getActorB());
            GraphNode Tu = UF.find(u);
            GraphNode Tv = UF.find(v);
            if(!(Tu.equals(Tv))){
                GraphNode newu = new GraphNode();
                newu.setPerson(u.getPerson());
                GraphNode newv = new GraphNode();
                newv.setPerson(v.getPerson());
                if(!Tree.hasNode(newu))
                    Tree.addNode(newu);
                if(!Tree.hasNode(newv))
                    Tree.addNode(newv);
                newu.addCollaboration(c);
                newv.addCollaboration(c);
                UF.union(Tu, Tv);
            }
        }
        ArrayList<Collaboration> Clist = Tree.getCollaborations();
        return Clist.toArray(new Collaboration[Clist.size()]);
    }
}
