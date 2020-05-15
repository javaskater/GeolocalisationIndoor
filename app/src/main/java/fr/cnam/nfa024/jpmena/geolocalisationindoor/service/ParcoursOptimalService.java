package fr.cnam.nfa024.jpmena.geolocalisationindoor.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.R;
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

    private NotificationManager mNotificationsManager;

    public ParcoursOptimalService() {
        super("ParcoursOptimalService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {//methode appelée en arrière plan
        if (intent != null) {

            mNotificationsManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            long idLieuDepart = (Long)intent.getLongExtra(SallesActivity.IDLIEUDEPART, -1);
            long idLieuArrrivee = (Long)intent.getLongExtra(SallesActivity.IDLIEUARRIVEE, -1);

            SerializablePlusCourtChemin serializablePlusCourtChemin = lancerCalcul(idLieuDepart, idLieuArrrivee);
            //création d'une notification cf. https://stackoverflow.com/questions/32345768/cannot-resolve-method-setlatesteventinfo
            int icon = R.drawable.ic_action_map;
            // Le premier titre affiché
            CharSequence tickerText = "Parcours disponible";

            Intent intentViewCourse = new Intent(this, ViewCourseActivity.class);
            intentViewCourse.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Pour lui permettre de démarrer une activité
            intentViewCourse.putExtra(CHEMINOPTIMAL, serializablePlusCourtChemin);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intentViewCourse, 0);

            Notification.Builder builder = new Notification.Builder(this);

            builder.setAutoCancel(false);
            builder.setTicker(tickerText);
            builder.setContentTitle("Parcours");
            builder.setContentText("Le Pacours est disponible");
            builder.setSmallIcon(R.drawable.ic_action_map);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(true);
            builder.setSubText("Accédez au pacrous en cliquant sur cette icône");   //API level 16
            builder.setNumber(100);
            builder.build();

            Notification notification = builder.getNotification();
            mNotificationsManager.notify(11, notification);

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