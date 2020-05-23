package fr.cnam.nfa024.jpmena.geolocalisationindoor.dao;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Graph;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Mouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Salle;

public class GraphDAO {
    private static GraphDAO ourInstance;

    private Context activityContext;

    private LocalisationDatabase mLocalisationDatabase;

    public static GraphDAO getInstance(Context context) {
        ourInstance = new GraphDAO(context);
        return ourInstance;
    }

    private GraphDAO(Context context) {
        this.activityContext = context;
        this.mLocalisationDatabase = new LocalisationDatabase(this.activityContext);
    }

    public Graph genererGraphe(){
        Graph graph = new Graph();
        List<Integer> sallesIds = this.mLocalisationDatabase.getAllSalles();
        for(int i = 0; i < sallesIds.size(); i++){
            Integer idSalle = sallesIds.get(i);
            Salle salle = this.mLocalisationDatabase.getSalleFromID(idSalle);
            if (null != salle){
                graph.addNode(salle);
            }
        }
        for(Salle salleSource: graph.getSalles()){
            Integer idSalle = salleSource.getIdentifiant();
            HashMap<Salle, Mouvement> destinations = new HashMap<Salle, Mouvement>();
            List <Mouvement> mouvements = this.mLocalisationDatabase.getNextElemants(idSalle);
            for (Mouvement mouvement: mouvements){
                for(Salle salledestination: graph.getSalles()){
                    if (mouvement.getIdTo() == salledestination.getIdentifiant()){
                        destinations.put(salledestination, mouvement);
                        break;
                    }
                }
                salleSource.setAdjacentSalles(destinations);
            }

        }
        return graph;
    }

    /*
     * à partir de l'ID de destination on veut retrouver le chemin des salles du fichier source
     * cette méthode est à appeler une fois l'algorithme de Dijkstra passé
     */
    public List<Salle> retournePlusCourtChemin(Graph graphe, Integer idDestination){
        Salle destination = null;
        for (Salle salle:graphe.getSalles()){
            if (salle.getIdentifiant().intValue() == idDestination.intValue()){
                destination = salle;
                break;
            }
        }
        if (destination != null) {
            List<Salle> plusCourtChemin = destination.getShortestPath();
            if (plusCourtChemin != null && plusCourtChemin.size() > 0) {
                plusCourtChemin.add(destination);
                return plusCourtChemin;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
