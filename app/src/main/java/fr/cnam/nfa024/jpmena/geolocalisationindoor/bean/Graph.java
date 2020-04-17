package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Graph implements Serializable {

    private Set<Salle> salles = new HashSet<>();

    public void addNode(Salle salle) {
        salles.add(salle);
    }

    public Set<Salle> getSalles() {
        return salles;
    }

    public void setSalles(Set<Salle> salles) {
        this.salles = salles;
    }
}
