package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.PlusCourtChemin;

public class ViewCourseActivity extends AppCompatActivity {

    private PlusCourtChemin mPlusCourtChemin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        //On récupère le chemin Optimal de la MainActivity
        Bundle bundle = getIntent().getExtras();
        mPlusCourtChemin = (PlusCourtChemin) bundle.getSerializable(MainActivity.CHEMINOPTIMAL);
    }

}
