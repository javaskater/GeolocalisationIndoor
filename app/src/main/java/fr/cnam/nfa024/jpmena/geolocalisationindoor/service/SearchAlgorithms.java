package fr.cnam.nfa024.jpmena.geolocalisationindoor.service;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.LocalisationDatabase;
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
    * Algorithms in a Nutshell
     */
    public void breadthFirstSearch(LocalisationDatabase db, long idSsalleDepart){
        List<Integer> toutesSallesIds = db.getAllSalles();
        LinkedList<Salle> = new LinkedList<Salle>();
        // On initialise notre graphe
        for (Integer salleId:toutesSallesIds) {
            Salle salle = db.getSalleFromID(salleId);
            salle.setCouleur(Color.WHITE);
            salle.setDistanceNoeudDepart(Integer.MAX_VALUE);
            salle.setIdSallePred(-1);
            if(salleId == idSsalleDepart){
                salle.setDistanceNoeudDepart(0);
                salle.setCouleur(Color.GRAY);
            }
        }
        
    }
}
