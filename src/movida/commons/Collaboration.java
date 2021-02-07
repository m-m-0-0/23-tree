package movida.commons;

import java.util.ArrayList;
import java.util.Arrays;

public class Collaboration implements Comparable<Collaboration> {

	Person actorA;
	Person actorB;
	ArrayList<Movie> movies;

	public Collaboration(Person actorA, Person actorB, Movie[] M) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = new ArrayList<Movie>();
		movies.addAll(Arrays.asList(M));
	}

	public Collaboration(Person actorA, Person actorB) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = new ArrayList<Movie>();
	}

	public void addMovie(Movie m){
		if(!movies.contains(m))
			movies.add(m);
	}

	public Person getCollaborator(Person p){
		if(actorA.compareTo(p) == 0)
			return actorB;
		if(actorB.compareTo(p) == 0)
			return actorA;

		return null;
	}

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;

		if(!(o instanceof Collaboration))
			return false;

		Collaboration c = (Collaboration) o;
		if(actorA.compareTo(c.getActorA()) == 0 && actorB.compareTo(c.getActorB()) == 0)
			return true;
		return false;
	}

	public int hashCode() {
		return 17 * actorA.hashCode() + actorB.hashCode();
	}

	public int compareTo(Collaboration c){
		return getScore().compareTo(c.getScore());
	}

	public Person getActorA() {
		return actorA;
	}

	public Person getActorB() {
		return actorB;
	}

	public Double getScore(){
		
		Double score = 0.0;
		
		for (Movie m : movies)
			score += m.getVotes();
		
		return score / movies.size();
	}
	
}
