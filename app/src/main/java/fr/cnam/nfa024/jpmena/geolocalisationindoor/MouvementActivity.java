package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MouvementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouvement);
        Bundle bundle = getIntent().getExtras();
        String deplacement = bundle.getString(EtapeActivity.MOUVEMENT);
        if(deplacement.equalsIgnoreCase("MONTER") || deplacement.equalsIgnoreCase("DESCENDRE")) {
            setTitle(deplacement+" d'un niveau");
        } else {
            setTitle("se diriger vers le "+ deplacement);
        }
    }
}
