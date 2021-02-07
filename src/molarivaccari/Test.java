package molarivaccari;

import movida.commons.*;

import java.util.Random;
import java.io.File;

public class Test {
    public static void main(String[] args){/*
        MovidaCore DB = new MovidaCore();
        DB.setMap(MapImplementation.ArrayOrdinato);
        DB.setSort(SortingAlgorithm.SelectionSort);
        DB.loadFromFile(new File("C:\\Users\\molar\\IdeaProjects\\Movida\\src\\molarivaccari\\esempio-formato-dati.txt"));
        System.out.println(DB.getMovieByTitle("Pulp Fiction").getTitle());
        System.out.println("--------------DELETING Pulp Fiction--------------");
        DB.deleteMovieByTitle("Pulp Fiction");
        Movie m = DB.getMovieByTitle("Pulp Fiction");
        if(m == null)
            System.out.println("success");
        DB.printAll();
        Person[] active = DB.searchMostActiveActors(3);
        for(Person p : active){
            System.out.println(p.getName());
        }*/
        MovidaCore DB = new MovidaCore();
        DB.setMap(MapImplementation.Alberi23);
        DB.setSort(SortingAlgorithm.SelectionSort);
        DB.loadFromFile(new File("C:\\Users\\molar\\IdeaProjects\\Movida\\src\\molarivaccari\\esempio-formato-dati.txt"));
        /*for(Person p : DB.getDirectCollaboratorsOf(new Person("Michelle Pfeiffer"))){
            System.out.println(p.getName());
        }*/

        Random R = new Random();
        Movie[] M;
        M = DB.getAllMovies();
        Movie m;
        while(DB.countMovies() != 0){
            m = M[R.nextInt(M.length)];
            System.out.println("DELETING "+m.getTitle()+"-----------------");
            DB.deleteMovieByTitle(m.getTitle());
            M = DB.getAllMovies();
            for(Movie _m : M){
                System.out.println(_m.getTitle());
            }
        }

        for(Person p: DB.getTeamOf(new Person("Albert Brooks")))
            System.out.println(p.getName());

        System.out.println("\n\n");

        for(Collaboration c : DB.maximizeCollaborationsInTheTeamOf(new Person("Toni Collette"))){
            System.out.println("A="+c.getActorA().getName()+"||B="+c.getActorB().getName()+"||score: "+c.getScore());
        }
    }
}
