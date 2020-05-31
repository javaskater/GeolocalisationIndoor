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
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableEtape;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableMouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializablePlusCourtChemin;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableSalle;

public class EtapeActivity extends AppCompatActivity {
    private SerializableSalle mSalleFrom;
    private SerializableSalle mSalleTo;
    private SerializableMouvement mDeplacement;
    private String[] mDeplacementsUnitaires;
    private LinearLayout mEtapeLinearLayout;

    private Button mOkFait;


    public static final String MOUVEMENT = "Mouvement";
    public static final String INDICE = "Indice";


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
        mDeplacementsUnitaires = deplacement.split("\\+");
        mEtapeLinearLayout = (LinearLayout)findViewById(R.id.mainEtapelayout);

        for (int i = 0; i < mDeplacementsUnitaires.length; i++){
            addLine(i);
        }

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
        /*if (deplacementsUnitaires != null && deplacementsUnitaires.length > 0){
            Intent intent = new Intent(EtapeActivity.this,MouvementActivity.class);
            intent.putExtra(MOUVEMENT, mDeplacementsUnitaires[0]);
            startActivity(intent);
        }*/
    }

    private void addLine(Integer indiceMouvement) {
        if (indiceMouvement < mDeplacementsUnitaires.length) {
            final String mouvementUnitaire = mDeplacementsUnitaires[indiceMouvement];
            //On ajoute un LinearLayout avec une orientation HORIZONTALE
            LinearLayout textLinearLayout = new LinearLayout(this);
            textLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            mEtapeLinearLayout.addView(textLinearLayout);

            TextView textView = new TextView(this);
            textView.setText(mouvementUnitaire);
            setTextViewAttributes(textView);
            textLinearLayout.addView(textView);


            Button button = new Button(this);
            button.setText("Detail Mouvement "+(indiceMouvement+1));
            ///button.setId(indiceEtape);
            setButtonAttributes(button);
            //cf réponse 29 dans https://stackoverflow.com/questions/10673628/implementing-onclicklistener-for-dynamically-created-buttons-in-android
            final int index = indiceMouvement;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    Toast.makeText(EtapeActivity.this, "etape :"+ (index + 1) + " sélectionnée", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EtapeActivity.this,MouvementActivity.class);
                    intent.putExtra(MOUVEMENT, mouvementUnitaire);
                    intent.putExtra(INDICE, index);
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
        mEtapeLinearLayout.addView(lineLayout);
    }
}
