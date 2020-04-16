
package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;
import java.util.Map;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Graph;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Mouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Salle;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.dao.GraphDAO;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.dao.LocalisationDatabase;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.SearchAlgorithms;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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
        List<Salle> plusCourtChemin = GraphDAO.getInstance(this).retournePlusCourtChemin(mGraphe, new Long(mIdLieuArrivee).intValue());
        for(int i =0; i < plusCourtChemin.size(); i++){
            Salle salleEnCours = plusCourtChemin.get(i);
            if (i < plusCourtChemin.size() - 1){
                Salle sallesuivante =  plusCourtChemin.get(i+1);
                Mouvement mouvement = null;
                for( Salle salleAdjacente: salleEnCours.getAdjacentSalles().keySet()){
                    if(salleAdjacente == sallesuivante){
                        mouvement = salleEnCours.getAdjacentSalles().get(salleAdjacente);
                        Log.i(TAG, "déplacement de :"+salleEnCours.getName()+ " à :"+sallesuivante.getName()+"en passant par:"+ mouvement.getDeplacement());
                    }
                }
            }
        }
    }
}
