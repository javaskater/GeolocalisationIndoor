package fr.cnam.nfa024.jpmena.geolocalisationindoor.service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.SallesAdapter;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.dao.FireBaseDAO;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.firebase.AsyncResponse;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.firebase.BackgroundOperation;

public class InitDatabaseAndScreen {

    private static final String TAG = "SERVICEFirebase";
    private Context mContext;
    private FireBaseDAO mFireBaseDAO;
    private long mIdLieuDepart;
    private long mIdLieuArrivee;


    public InitDatabaseAndScreen(Context context) {

        this.mContext = context;
    }


    public void viderBase(){
        mFireBaseDAO = FireBaseDAO.getInstance(this.mContext);
        mFireBaseDAO.initialiserDatabase();
    }

    public void chargerDonneesEnBase(final String url, Spinner lieuDepart, Spinner lieuArrivee){
        BackgroundOperation backgroundOperation= new BackgroundOperation(
                new AsyncResponse() {
                    @Override
                    public void processFinish(JSONObject jsonObject, Spinner lieuDepart, final Spinner lieuArrivee) {
                        JSONArray sallesArray = null;
                        JSONArray mouvementsArray = null;
                        try {
                            sallesArray = jsonObject.getJSONArray("salles");

                            for (int i = 0; i < sallesArray.length(); i++) {
                                try {
                                    JSONObject obj = sallesArray.getJSONObject(i);
                                    Integer idSalle = obj.getInt("_id");
                                    String nomSalle = obj.getString("numero_salle");
                                    Boolean accessible = obj.getBoolean("accessible");
                                    Log.i(TAG, "on insère salle de _id:" + idSalle + " et de nom:" + nomSalle);
                                    mFireBaseDAO.insererSalle(idSalle, nomSalle, accessible);

                                } catch (JSONException e) {
                                    Log.w(TAG, "erreur de parsing de l'élément salle " + i + " cause:" + e.getMessage());
                                    continue;
                                }
                            }
                        } catch (JSONException e1) {
                            Log.e(TAG, "erreur de parsing JSON des salles:" + e1.getMessage());
                        }
                        try {
                            mouvementsArray = jsonObject.getJSONArray("mouvements");

                            for (int i = 0; i < mouvementsArray.length(); i++) {
                                try {
                                    JSONObject obj = mouvementsArray.getJSONObject(i);
                                    Integer from = obj.getInt("de");
                                    Integer to = obj.getInt("a");
                                    String mouvement = obj.getString("mouvement");
                                    Boolean accessible = obj.getBoolean("accessible");
                                    Log.i(TAG, "Insertion du mouvement: On va de (id):" + from + " vers (id):" + to + " via le déplacement:" + mouvement);
                                    mFireBaseDAO.insererMouvement(from, to, mouvement,accessible);
                                } catch (JSONException e) {
                                    Log.w(TAG, "erreur de parsing de l'élément mouvement " + i + " cause:" + e.getMessage());
                                    continue;
                                }
                            }
                        } catch (JSONException e1) {
                            Log.e(TAG, "erreur de parsing JSON des mouvements:" + e1.getMessage());
                        }

                        Cursor lieuxDepart = mFireBaseDAO.getLieuxDepartList();

                        lieuDepart.setAdapter(new SallesAdapter(InitDatabaseAndScreen.this.mContext, lieuxDepart, 0));
                        lieuDepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                //Toast.makeText(SallesActivity.this, "position:"+ position+ " et id:"+id, Toast.LENGTH_LONG).show();
                                mIdLieuDepart = id;
                                Cursor lieuxArrivee = mFireBaseDAO.getLieuxArriveeList(id);
                                lieuArrivee.setAdapter(new SallesAdapter(InitDatabaseAndScreen.this.mContext, lieuxArrivee, 0));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        lieuArrivee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                mIdLieuArrivee = id;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } //fin de processFinished
                } // fin de newAsynResponse (interface)
            , lieuDepart, lieuArrivee); //fin de new BackgroundOperation(
            backgroundOperation.execute(url);
        } // fin de charger données en Base


    public long getIdLieuDepart() {
        return mIdLieuDepart;
    }

    public long getIdLieuArrivee() {
        return mIdLieuArrivee;
    }
}
