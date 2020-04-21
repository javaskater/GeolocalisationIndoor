package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableMouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializablePlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableSalle;

public class EtapeActivity extends AppCompatActivity {
    private SerializableSalle mSalleFrom;
    private SerializableSalle mSalleTo;
    private SerializableMouvement mDeplacement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etape);
        Bundle bundle = getIntent().getExtras();
        HashMap<String,Object> etape = (HashMap<String,Object>) bundle.getSerializable(ViewCourseActivity.ETAPE);
        mSalleFrom = (SerializableSalle)etape.get(SerializablePlusCourtChemin.FROM);
        mSalleTo = (SerializableSalle)etape.get(SerializablePlusCourtChemin.TO);
        mDeplacement = (SerializableMouvement)etape.get(SerializablePlusCourtChemin.BY);
        setTitle("De:"+mSalleFrom.getName()+" vers:"+mSalleTo.getName());
        TextView affichage = findViewById(R.id.textMouvement);
        affichage.setText(mDeplacement.getDeplacement());
    }
}
