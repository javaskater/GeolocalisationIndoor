package fr.cnam.nfa024.jpmena.geolocalisationindoor.service;

import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Graph;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Mouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Salle;

public class SearchAlgorithms {
    private static final SearchAlgorithms ourInstance = new SearchAlgorithms();

    public final static String TAG_DIJKSTRA = "DIJKSTRA_ALGORITHM";

    public static SearchAlgorithms getInstance() {
        return ourInstance;
    }

    private SearchAlgorithms() {
    }

    /*
    * algorithme tiré du livre OReilly
    * Algorithms in a Nutshell et de https://www.codeflow.site/fr/article/java-dijkstra
    * ou https://www.baeldung.com/java-dijkstra
    *
     */
    public Graph calculateShortestPathFromSource(Graph graph, Integer idSource) {


        Salle source = null;

        for (Salle salle : graph.getSalles()) {
            if (salle.getIdentifiant().intValue() == idSource.intValue()){
                source = salle;
                Log.i(SearchAlgorithms.TAG_DIJKSTRA, "on démarre de la salle:"+source.getName());
                break;
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
                        Log.i(SearchAlgorithms.TAG_DIJKSTRA, "on ajoute à la queue unsettled la salle:"+adjacentSalle.getName());
                    }
                }
                Log.i(SearchAlgorithms.TAG_DIJKSTRA, "on a fini de traiter la salle:"+currentSalle.getName()+ " on l'ajoute à la liste settled");
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
        Log.i(SearchAlgorithms.TAG_DIJKSTRA, "[getLowestDistanceSalle]La salle de la queue unsettled de distance la moins grande du départ:"+ lowestDistanceSalle.getName());
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
            Log.i(SearchAlgorithms.TAG_DIJKSTRA, "[calculateMinimumDistance]La somme de :"+sourceSalle.getName()+ " avec: "+ edgeWeigh+ "est inférireure à :"+ evaluationSalle.getName());
        } else {
            Log.i(SearchAlgorithms.TAG_DIJKSTRA, "[calculateMinimumDistance]La somme de :"+sourceSalle.getName()+ " avec: "+ edgeWeigh+ "est supérieure à :"+ evaluationSalle.getName());
        }
    }


}
