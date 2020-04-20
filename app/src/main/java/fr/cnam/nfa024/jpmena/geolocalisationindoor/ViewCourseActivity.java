package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.PlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializablePlusCourtChemin;

public class ViewCourseActivity extends AppCompatActivity {

    private static String TAG = "ViewCourseActivity";
    private SerializablePlusCourtChemin mPlusCourtChemin;
    private Integer mNumeroEatapeRealise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        //On récupère le chemin Optimal de la MainActivity
        Bundle bundle = getIntent().getExtras();
        mPlusCourtChemin = (SerializablePlusCourtChemin) bundle.getSerializable(MainActivity.CHEMINOPTIMAL);
        //visualisation du plus court chemin dans la console Logcat
        for(String parcoursElement:mPlusCourtChemin.toLogcat()){
            Log.i(TAG, parcoursElement);
        }
    }

}
