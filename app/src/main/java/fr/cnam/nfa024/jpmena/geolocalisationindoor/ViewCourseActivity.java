package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableEtape;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializablePlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableSalle;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.ParcoursOptimalService;

public class ViewCourseActivity extends AppCompatActivity {

    private final static String TAG = "ViewCourseActivity";
    public final static String ETAPE = "SerializableEtape";
    public static String INDICE_ETAPE = "indiceEtape";
    public static Integer REQUEST_CODE = new Integer(2);
    private SerializablePlusCourtChemin mPlusCourtChemin;
    private RelativeLayout mMainRelativeLayout;
    private Integer mNumeroEtapeRealise;
    private CustomEtapeAdapter mEtapeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        mMainRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativelayout);
        //On récupère le chemin Optimal de la SallesActivity
        Bundle bundle = getIntent().getExtras();
        mPlusCourtChemin = (SerializablePlusCourtChemin) bundle.getSerializable(ParcoursOptimalService.CHEMINOPTIMAL);
        if (mPlusCourtChemin != null) {
            //visualisation du plus court chemin dans la console Logcat
            for (String parcoursElement : mPlusCourtChemin.toLogcat()) {
                Log.i(TAG, parcoursElement);
            }
            /*
            * Mise en place d'une ListView avecc un custom Adpater pour afficher les étapes du parcours
            * cf. https://github.com/javaskater/SuiviPArcours
            */

            mEtapeAdapter = new CustomEtapeAdapter(this, mPlusCourtChemin.extractEtapes());
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lvEtapes);
            listView.setAdapter(mEtapeAdapter);
        } else { //on n'a pas de chemin entre le départ et l'arrivée en raison des travaux
            addMessage("Pas de chemin entre le départ et l'arrivée");
        }
    }


    private void setTextViewAttributes(TextView textView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                0, 0
        );

        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(params);
    }

    //This function to convert DPs to pixels
    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    /*
    * Le message que l'on affiche quand iil n'y a pas de chemin entre
    * le départ et l'arrivée
     */
    private void addMessage(String texteMessage){
        TextView textViewMessage = new TextView(this);
        textViewMessage.setText(texteMessage);
        setTextViewAttributes(textViewMessage);
        textViewMessage.setTextColor(Color.RED);
        mMainRelativeLayout.addView(textViewMessage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE ) {
                SerializableEtape returnedEtape = (SerializableEtape)data.getSerializableExtra(ETAPE);
                Toast.makeText(this, "Etape de " +returnedEtape.getmSalleFrom().getName()+ " à " +returnedEtape.getmSalleTo().getName()+ " réalisée avec succcès", Toast.LENGTH_LONG).show();
                for (SerializableEtape uneEtape:mPlusCourtChemin.extractEtapes()){
                    if (uneEtape.equals(returnedEtape)){
                        uneEtape.setmEtapeTerminee(new Boolean(true));
                        mEtapeAdapter.notifyDataSetChanged();
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
