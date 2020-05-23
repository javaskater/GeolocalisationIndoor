package fr.cnam.nfa024.jpmena.geolocalisationindoor.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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

    public final static String TAG_INTENT = "NotificationClicked";

    private final static String GEOLOCALISATION_CHANNEL_ID = "GeoLocalisation";

    private NotificationManager mNotificationsManager;

    public ParcoursOptimalService() {
        super("ParcoursOptimalService");
    }

    @TargetApi(Build.VERSION_CODES.O) //a du code pour une api au delà de 26
    @Override
    protected void onHandleIntent(Intent intent) {//methode appelée en arrière plan
        if (intent != null) {

            mNotificationsManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            long idLieuDepart = (Long)intent.getLongExtra(SallesActivity.IDLIEUDEPART, -1);
            long idLieuArrrivee = (Long)intent.getLongExtra(SallesActivity.IDLIEUARRIVEE, -1);
            //Le calcul réalisé en arrière plan par notre
            SerializablePlusCourtChemin serializablePlusCourtChemin = lancerCalcul(idLieuDepart, idLieuArrrivee);
            //création d'une notification cf. https://stackoverflow.com/questions/32345768/cannot-resolve-method-setlatesteventinfo
            // Le premier titre affiché
            CharSequence tickerText = "Parcours disponible";
            //Implicit Intent to acccess the ViewCourseActivity
            Intent intentViewCourse = new Intent(this, ViewCourseActivity.class);
            intentViewCourse.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Pour lui permettre de démarrer une activité
            intentViewCourse.putExtra(CHEMINOPTIMAL, serializablePlusCourtChemin); //vaut null ssi pas de chemin entre le départ et l'arrivée
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intentViewCourse, 0);

            mNotificationsManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = null;
            if (Integer.valueOf(android.os.Build.VERSION.SDK) >= Build.VERSION_CODES.O){ //A partir de l'API 26 on doit définir un channel pour notre notification
                NotificationChannel followersChannel = new NotificationChannel(GEOLOCALISATION_CHANNEL_ID, "Followers", NotificationManager.IMPORTANCE_DEFAULT);
                followersChannel.setDescription("le canal de mes applications");
                followersChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
                mNotificationsManager.createNotificationChannel(followersChannel); // Le notofocationSystemService crée le canal
                builder = new NotificationCompat.Builder(this, GEOLOCALISATION_CHANNEL_ID); //Le notification Builder passera toujours par ce canal
            } else { //cas de laPI 19
                builder = new NotificationCompat.Builder(this);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            }

            builder.setAutoCancel(false);
            builder.setTicker(tickerText);
            builder.setContentTitle("Parcours");
            builder.setContentText("Le Pacours est disponible");
            builder.setSmallIcon(R.drawable.explore);
            Bitmap large_icon_bmp = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.ic_action_map);
            builder.setLargeIcon(large_icon_bmp);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(true);
            builder.setSubText("Accédez au pacrous en cliquant sur cette icône");   //API level 16
            builder.setNumber(5000); //durée d'apparition de l'icone dans la barre de notifications ?
            builder.build();

            Notification notification = builder.getNotification();
            mNotificationsManager.notify(11, notification);

        }
    }


    public SerializablePlusCourtChemin lancerCalcul(long idLieuDepart, long idLieuArrivee) {
        Graph graphe = GraphDAO.getInstance(this).genererGraphe();
        graphe = SearchAlgorithms.getInstance().calculateShortestPathFromSource(graphe, new Integer(new Long(idLieuDepart).intValue()));
        // afficher le chemin optimum de mIdLieuDart à mIdLieuArrivee
        List<Salle> listePlusCourtChemin = GraphDAO.getInstance(this).retournePlusCourtChemin(graphe, new Integer(new Long(idLieuArrivee).intValue()));
        if (listePlusCourtChemin != null) {
            //Création de l'objet Serializable à passer à lactivité de Visualisation
            PlusCourtChemin plusCourtChemin = new PlusCourtChemin(listePlusCourtChemin);
            //visualisation du plus court chemin dans la console Logcat
            for (String parcoursElement : plusCourtChemin.toStringsForLogCat()) {
                Log.i(TAG, parcoursElement);
            }
            /*
             * passage par un objet simplifié de parcours optimal
             * but c'est qu'il soit Serializable
             */
            SerializablePlusCourtChemin serializablePlusCourtChemin = plusCourtChemin.prepareSerialisation();

            return serializablePlusCourtChemin;
        } else { //il n'y a pas de chemin entre le lieu de départ et le lieu d'arrivée
            return null;
        }
    }
}
