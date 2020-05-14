package fr.cnam.nfa024.jpmena.geolocalisationindoor.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.SallesActivity;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.ViewCourseActivity;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Graph;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.PlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Salle;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializablePlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.dao.GraphDAO;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ParcoursOptimalService extends IntentService {

    private static String TAG = "ParcoursOptimalService";

    public final static String CHEMINOPTIMAL = "CheminOptimal";

    public ParcoursOptimalService() {
        super("ParcoursOptimalService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {//methode appelée en arrière plan
        if (intent != null) {
            long idLieuDepart = (Long)intent.getLongExtra(SallesActivity.IDLIEUDEPART, -1);
            long idLieuArrrivee = (Long)intent.getLongExtra(SallesActivity.IDLIEUARRIVEE, -1);

            SerializablePlusCourtChemin serializablePlusCourtChemin = lancerCalcul(idLieuDepart, idLieuArrrivee);
            //TODO Ajouter une notification une fois ce chemin calculé
            Intent intentViewCourse = new Intent(this, ViewCourseActivity.class);
            intentViewCourse.putExtra(CHEMINOPTIMAL, serializablePlusCourtChemin);
            startActivity(intentViewCourse);
        }
    }


    public SerializablePlusCourtChemin lancerCalcul(long idLieuDepart, long idLieuArrivee){
        Graph graphe = GraphDAO.getInstance(this).genererGraphe();
        graphe = SearchAlgorithms.getInstance().calculateShortestPathFromSource(graphe, new Integer(new Long(idLieuDepart).intValue()));
        // afficher le chemin optimum de mIdLieuDart à mIdLieuArrivee
        List<Salle> listePlusCourtChemin = GraphDAO.getInstance(this).retournePlusCourtChemin(graphe, new Integer(new Long(idLieuArrivee).intValue()));
        //Création de l'objet Serializable à passer à lactivité de Visualisation
        PlusCourtChemin plusCourtChemin = new PlusCourtChemin(listePlusCourtChemin);
        //visualisation du plus court chemin dans la console Logcat
        for(String parcoursElement:plusCourtChemin.toStringsForLogCat()){
            Log.i(TAG, parcoursElement);
        }
        /*
         * passage par un objet simplifié de parcours optimal
         * but c'est qu'il soit Serializable
         */
        SerializablePlusCourtChemin serializablePlusCourtChemin = plusCourtChemin.prepareSerialisation();

        return serializablePlusCourtChemin;

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
