
package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.SearchAlgorithms;

public class MainActivity extends AppCompatActivity {

    private LocalisationDatabase mDB;
    private Spinner mLieuDepart;
    private long mIdLieuDepart;
    private Spinner mLieuArrivee;
    private long mIdLieuArrivee;

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
    }
    public void lancerCalcul(View v){
        SearchAlgorithms.getInstance().breadthFirstSearch(mDB, mIdLieuDepart);
    }
}
