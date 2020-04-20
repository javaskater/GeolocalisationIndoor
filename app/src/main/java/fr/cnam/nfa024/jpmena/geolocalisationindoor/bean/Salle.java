package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/*
* inspiré sz https://www.codeflow.site/fr/article/java-dijkstra
 */

public class Salle {

    private String name;

    private List<Salle> shortestPath = new LinkedList<>();

    private Integer identifiant;

    private Integer distance = Integer.MAX_VALUE;

    Map<Salle, Mouvement> adjacentSalles = new HashMap<>();

    public void addDestination(Salle destination, Mouvement mouvement) {
        adjacentSalles.put(destination, mouvement);
    }

    public Salle(Integer id, String name) {
        this.name = name;
        this.identifiant = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }

    public List<Salle> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Salle> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Salle, Mouvement> getAdjacentSalles() {
        return adjacentSalles;
    }

    public void setAdjacentSalles(Map<Salle, Mouvement> adjacentSalles) {
        this.adjacentSalles = adjacentSalles;
    }
}
