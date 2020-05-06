
package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Graph;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Mouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.PlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Salle;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializablePlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.dao.GraphDAO;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.dao.LocalisationDatabase;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.InitDatabase;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.SearchAlgorithms;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String CHEMINOPTIMAL = "CheminOptimal";

    private static final String URLFIREBASE = "https://geolocalisation-indoor.firebaseio.com/buildingmaps/cnamacces31.json";

    /*
    * pour l'appel au QRCode scanner
     */
    public static final int REQUEST_SCANNER = 1;

    private LocalisationDatabase mDB;
    private Spinner mLieuDepart;
    private long mIdLieuDepart;
    private Spinner mLieuArrivee;
    private long mIdLieuArrivee;
    private Button mScanner; //le bouton qui lance le scanner de QRCode
    private String mSalleScannee; //le resultat du scan de QRCode

    //le service qui intilise la base de données à partir des données récupérées d'une base Firebase
    private InitDatabase initDatabase;
    /* Graphe orienté des salles et des mouvements possibles entre salles
    * prêt pour l'algorithme de Dijstra en suivant
    * https://www.codeflow.site/fr/article/java-dijkstra
     */
    private Graph mGraphe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///initiliser les données en base à partir de Firebase
        //TODO problème il recharge les donnée à chaque fois que onCreate est appelé typiqurment quand on passe du mode
        //portrait au mode paiysage
        initDatabase = new InitDatabase(this);
        initDatabase.viderBase();
        initDatabase.chargerDonneesEnBase(URLFIREBASE);

        mDB = new LocalisationDatabase(this);
        mLieuDepart = (Spinner) findViewById(R.id.lieuDeDepart);
        mLieuArrivee = (Spinner) findViewById(R.id.lieuDeArrivee);
        Cursor lieuxDepart = mDB.getLieuxDepartList();
        mLieuDepart.setAdapter(new SallesAdapter(this, lieuxDepart, 0));
        mLieuDepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "position:"+ position+ " et id:"+id, Toast.LENGTH_LONG).show();
                mIdLieuDepart = id;
                Cursor lieuxArrivee = mDB.getLieuxArriveeList(id);
                mLieuArrivee.setAdapter(new SallesAdapter(MainActivity.this, lieuxArrivee, 0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mLieuArrivee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mIdLieuArrivee = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mGraphe = GraphDAO.getInstance(this).genererGraphe();

        mScanner = (Button)findViewById(R.id.scanQrCode);
        mScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on lance l'intent de scanning par zxing cf exercice 2 TP 4 de NFA025 en 2019
                Intent photoIntent  = new Intent("com.google.zxing.client.android.SCAN");
                PackageManager pm = getPackageManager();
                List<ResolveInfo> listeActivites = pm.queryIntentActivities(photoIntent,0);
                if(listeActivites.size() > 0){
                    startActivityForResult(photoIntent, REQUEST_SCANNER);
                }else{
                    Toast.makeText(MainActivity.this, "l'application BarCode Scanner by Sxing doit être installée", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void lancerCalcul(View v){
        mGraphe = SearchAlgorithms.getInstance().calculateShortestPathFromSource(mGraphe, new Integer(new Long(mIdLieuDepart).intValue()));
        // afficher le chemin optimum de mIdLieuDart à mIdLieuArrivee
        List<Salle> listePlusCourtChemin = GraphDAO.getInstance(this).retournePlusCourtChemin(mGraphe, new Long(mIdLieuArrivee).intValue());
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
        Intent intentViewCourse = new Intent(this, ViewCourseActivity.class);
        intentViewCourse.putExtra(CHEMINOPTIMAL, serializablePlusCourtChemin);
        startActivity(intentViewCourse);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SCANNER){
            if (resultCode == RESULT_OK) {
                this.mSalleScannee = (String) data.getExtras().get("SCAN_RESULT");
                TextView scan_tv = (TextView)findViewById(R.id.scanResult);
                scan_tv.setText(this.mSalleScannee);
                // on veut mettre le spinner de départ à la valeur retournée par le scanner
                //Le scanner lit une chaine de caractère, on recherche son indice dans le spinner des salles de départ
                mIdLieuDepart = getIndex(mLieuDepart,mSalleScannee); //cf. réponse 140 de https://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position
                if (mIdLieuDepart >= 0){
                    mLieuDepart.setSelection((int)mIdLieuDepart); // on met à jour le spinner du liei de départ
                    Cursor lieuxArrivee = mDB.getLieuxArriveeList(mIdLieuDepart); ///on cherche les leiux qui ne sont pas le lieu de départ
                    SallesAdapter sallesAdapter = new SallesAdapter(MainActivity.this, lieuxArrivee, 0);
                    mLieuArrivee.setAdapter(sallesAdapter); //on met à jour le Soinnerr des lieux d'arrivée...
                    sallesAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity.this, "LA salle "+mSalleScannee+" n'est pas une salle du plan", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    /* cf. réponse 140 de
    * https://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position
    * */
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            Cursor cursor = (Cursor) spinner.getItemAtPosition(i);
            String value = cursor.getString( cursor.getColumnIndex(LocalisationDatabase.FIELD_NUMERO_SALLE));
            if (value.equalsIgnoreCase(myString)){
                return i;
            }
        }

        return -1;
    }
}
