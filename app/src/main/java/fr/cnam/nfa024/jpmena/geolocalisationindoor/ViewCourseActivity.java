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
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializablePlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableSalle;

public class ViewCourseActivity extends AppCompatActivity {

    private static String TAG = "ViewCourseActivity";
    public static String ETAPE = "Etape";
    private SerializablePlusCourtChemin mPlusCourtChemin;
    private LinearLayout mMainLinearLayout;
    private Integer mNumeroEatapeRealise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        //On récupère le chemin Optimal de la SallesActivity
        Bundle bundle = getIntent().getExtras();
        mPlusCourtChemin = (SerializablePlusCourtChemin) bundle.getSerializable(SallesActivity.CHEMINOPTIMAL);
        //visualisation du plus court chemin dans la console Logcat
        for(String parcoursElement:mPlusCourtChemin.toLogcat()){
            Log.i(TAG, parcoursElement);
        }
        /*Création dynamique des étapes du parcours
        * en s'appuyant sur https://github.com/uddish/DynamicLayouts/blob/master/app/src/main/java/com/example/uddishverma/dynamiclayoutsexample/MainActivity.java%C2%B2
         */
        mMainLinearLayout = (LinearLayout)findViewById(R.id.mainlinearlayout);

        for (int i = 0; i < mPlusCourtChemin.getNombreEtapes(); i++){
            addLine(i);
        }
    }


    private void addLine(Integer indiceEtape) {
        if (indiceEtape < mPlusCourtChemin.getNombreEtapes()) {
            final HashMap<String, Object> etape = mPlusCourtChemin.extractEtape(indiceEtape);
            //On ajoute un LinearLayout avec une orientation HORIZONTALE
            LinearLayout textLinearLayout = new LinearLayout(this);
            textLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            mMainLinearLayout.addView(textLinearLayout);

            SerializableSalle serializableSalleFrom = (SerializableSalle)etape.get(SerializablePlusCourtChemin.FROM);
            TextView textViewFrom = new TextView(this);
            textViewFrom.setText(serializableSalleFrom.getName());
            setTextViewAttributes(textViewFrom);
            textLinearLayout.addView(textViewFrom);

            SerializableSalle serializableSalleTo = (SerializableSalle)etape.get(SerializablePlusCourtChemin.TO);
            TextView textViewTo = new TextView(this);
            textViewTo.setText(serializableSalleTo.getName());
            setTextViewAttributes(textViewTo);
            textLinearLayout.addView(textViewTo);

            Button button = new Button(this);
            button.setText("Go Etape "+(indiceEtape+1));
            ///button.setId(indiceEtape);
            setButtonAttributes(button);
            //cf réponse 29 dans https://stackoverflow.com/questions/10673628/implementing-onclicklistener-for-dynamically-created-buttons-in-android
            final int index = indiceEtape;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    Toast.makeText(ViewCourseActivity.this, "etape :"+ (index + 1) + " sélectionnée", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ViewCourseActivity.this,EtapeActivity.class);
                    intent.putExtra(ETAPE, etape);
                    startActivity(intent);
                }
            });
            textLinearLayout.addView(button);
        }
            addLineSeperator();
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

    private void setButtonAttributes(Button button) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                0, 0
        );

        button.setTextColor(Color.WHITE);
        button.setLayoutParams(params);
    }

    //This function to convert DPs to pixels
    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    private void addLineSeperator() {
        LinearLayout lineLayout = new LinearLayout(this);
        lineLayout.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2);
        params.setMargins(0, convertDpToPixel(10), 0, convertDpToPixel(10));
        lineLayout.setLayoutParams(params);
        mMainLinearLayout.addView(lineLayout);
    }

}
