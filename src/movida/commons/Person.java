/* 
 * Copyright (C) 2020 - Angelo Di Iorio
 * 
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 * 
*/
package movida.commons;

/**
 * Classe usata per rappresentare una persona, attore o regista,
 * nell'applicazione Movida.
 * 
 * Una persona � identificata in modo univoco dal nome 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 * 
 * Semplificazione: <code>name</code> � usato per memorizzare il nome completo (nome e cognome)
 * 
 * La classe pu� essere modicata o estesa ma deve implementare il metodo getName().
 * 
 */
public class Person {

	private String name;
	
	public Person(String name) {
		this.name = name;
	}

	public int compareTo(Person p) {
		return name.toLowerCase().compareTo(p.getName().toLowerCase());
	}

	public boolean equals(Object p){
		if(this == p)
			return true;

		if(!(p instanceof Person))
			return false;

		Person p_ = (Person) p;
		return name.toLowerCase().equals(p_.getName().toLowerCase());
	}

	public int hashCode() {
		return 17 * name.toLowerCase().hashCode();
	}
	public String getName(){
		return this.name;
	}
}
