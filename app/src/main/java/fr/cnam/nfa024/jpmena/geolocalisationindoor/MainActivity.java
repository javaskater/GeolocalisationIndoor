
package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

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
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.SearchAlgorithms;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String CHEMINOPTIMAL = "CheminOptimal";

    private LocalisationDatabase mDB;
    private Spinner mLieuDepart;
    private long mIdLieuDepart;
    private Spinner mLieuArrivee;
    private long mIdLieuArrivee;
    /* Graphe orienté des salles et des mouvements possibles entre salles
    * prêt pour l'algorithme de Dijstra en suivant
    * https://www.codeflow.site/fr/article/java-dijkstra
     */
    private Graph mGraphe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
