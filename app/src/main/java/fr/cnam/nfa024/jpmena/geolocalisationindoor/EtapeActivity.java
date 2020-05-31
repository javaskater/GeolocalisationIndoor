package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableDeplacement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableEtape;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableMouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializablePlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableSalle;

public class EtapeActivity extends AppCompatActivity {
    private SerializableSalle mSalleFrom;
    private SerializableSalle mSalleTo;
    private SerializableMouvement mDeplacement;
    private ArrayList<SerializableDeplacement> mDeplacementsUnitaires;

    private Button mOkFait;

    private CustomDeplacementAdapter mAdapter;


    public static final String MOUVEMENT = "Mouvement";
    public static final int REQUEST_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final SerializableEtape etape = (SerializableEtape) bundle.getSerializable(ViewCourseActivity.ETAPE);
        mSalleFrom = (SerializableSalle) etape.getmSalleFrom();
        mSalleTo = (SerializableSalle) etape.getmSalleTo();
        setContentView(R.layout.activity_etape);
        mDeplacement = (SerializableMouvement) etape.getmMouvement();
        setTitle("De:"+mSalleFrom.getName()+" vers:"+mSalleTo.getName());
        String deplacement = mDeplacement.getDeplacement();
        //affichage.setText(deplacement);
        String[] deplacementsUnitaires = deplacement.split("\\+");
        mDeplacementsUnitaires = new ArrayList<SerializableDeplacement>();
        for (int i = 0; i < deplacementsUnitaires.length; i++){
            SerializableDeplacement unDeplacement = new SerializableDeplacement();
            unDeplacement.setmPosition(i+1);
            unDeplacement.setmFait(new Boolean(false));
            unDeplacement.setmDeplacement(deplacementsUnitaires[i]);
            mDeplacementsUnitaires.add(unDeplacement);
        }

        mAdapter = new CustomDeplacementAdapter(this, mDeplacementsUnitaires);

        ListView listView = (ListView) findViewById(R.id.lvDeplacements);
        listView.setAdapter(mAdapter);

        mOkFait = (Button) findViewById(R.id.boutonConfirmation);
        mOkFait.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(ViewCourseActivity.ETAPE, etape);
                EtapeActivity.this.setResult(ViewCourseActivity.REQUEST_CODE,i);
                EtapeActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE ) {
                SerializableDeplacement returnedDeplacment = (SerializableDeplacement)data.getSerializableExtra(MOUVEMENT);
                Toast.makeText(this, "Deplacement unitaire no:" +returnedDeplacment.getmPosition()+ " faisant: " +returnedDeplacment.getmDeplacement()+ " realisé avec succès", Toast.LENGTH_LONG).show();
                for (SerializableDeplacement unMouvement:mDeplacementsUnitaires){
                    if (unMouvement.equals(returnedDeplacment)){
                        unMouvement.setmFait(new Boolean(true));
                        mAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
