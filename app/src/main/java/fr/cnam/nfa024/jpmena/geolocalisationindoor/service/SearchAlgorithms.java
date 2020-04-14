package fr.cnam.nfa024.jpmena.geolocalisationindoor.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Graph;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Mouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Salle;

public class SearchAlgorithms {
    private static final SearchAlgorithms ourInstance = new SearchAlgorithms();

    public static SearchAlgorithms getInstance() {
        return ourInstance;
    }

    private SearchAlgorithms() {
    }

    /*
    * algorithme tiré du livre OReilly
    * Algorithms in a Nutshell et de https://www.codeflow.site/fr/article/java-dijkstra
    *
     */
    public Graph calculateShortestPathFromSource(Graph graph, Integer idSource) {


        Salle source = null;

        for (Salle salle : graph.getSalles()) {
            if (salle.getIdentifiant() == idSource){
                source = salle;
            }
        }
        if (source != null) {
            source.setDistance(0);

            Set<Salle> settledSalles = new HashSet<>();
            Set<Salle> unsettledSalles = new HashSet<>();

            unsettledSalles.add(source);

            while (unsettledSalles.size() != 0) {
                Salle currentSalle = getLowestDistanceSalle(unsettledSalles);
                unsettledSalles.remove(currentSalle);
                for (Map.Entry<Salle, Mouvement> adjacencyPair :
                        currentSalle.getAdjacentSalles().entrySet()) {
                    Salle adjacentSalle = adjacencyPair.getKey();
                    Integer edgeWeight = ((Mouvement) adjacencyPair.getValue()).getWeight();
                    if (!settledSalles.contains(adjacentSalle)) {
                        calculateMinimumDistance(adjacentSalle, edgeWeight, currentSalle);
                        unsettledSalles.add(adjacentSalle);
                    }
                }
                settledSalles.add(currentSalle);
            }
        }
        return graph;
    }

    private Salle getLowestDistanceSalle(Set < Salle > unsettledSalles) {
        Salle lowestDistanceSalle = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Salle salle: unsettledSalles) {
            int salleDistance = salle.getDistance();
            if (salleDistance < lowestDistance) {
                lowestDistance = salleDistance;
                lowestDistanceSalle = salle;
            }
        }
        return lowestDistanceSalle;
    }

    private void calculateMinimumDistance(Salle evaluationSalle,
                                                 Integer edgeWeigh, Salle sourceSalle) {
        Integer sourceDistance = sourceSalle.getDistance();
        if (sourceDistance + edgeWeigh < evaluationSalle.getDistance()) {
            evaluationSalle.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Salle> shortestPath = new LinkedList<>(sourceSalle.getShortestPath());
            shortestPath.add(sourceSalle);
            evaluationSalle.setShortestPath(shortestPath);
        }
    }

}
