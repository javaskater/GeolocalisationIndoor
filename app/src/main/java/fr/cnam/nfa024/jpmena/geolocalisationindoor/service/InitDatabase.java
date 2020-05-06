package fr.cnam.nfa024.jpmena.geolocalisationindoor.service;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.dao.FireBaseDAO;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.firebase.AsyncResponse;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.firebase.BackgroundOperation;

public class InitDatabase {

    private static final String TAG = "SERVICEFirebase";
    private Context context;
    private FireBaseDAO fireBaseDAO;

    public InitDatabase(Context context) {
        this.context = context;
    }

    public void viderBase(){
        fireBaseDAO = FireBaseDAO.getInstance(this.context);
        fireBaseDAO.initialiserDatabase();
    }

    public void chargerDonneesEnBase(String url){
        BackgroundOperation backgroundOperation= new BackgroundOperation(
                new AsyncResponse() {
                    @Override
                    public void processFinish(JSONObject jsonObject) {
                        JSONArray sallesArray = null;
                        JSONArray mouvementsArray = null;
                        try {
                            sallesArray = jsonObject.getJSONArray("salles");

                            for (int i = 0 ; i < sallesArray.length(); i++) {
                                try {
                                    JSONObject obj = sallesArray.getJSONObject(i);
                                    Integer idSalle = obj.getInt("_id");
                                    String nomSalle = obj.getString("numero_salle");
                                    Log.i(TAG, "on insère salle de _id:" + idSalle + " et de nom:" + nomSalle);
                                    fireBaseDAO.insererSalle(idSalle, nomSalle);

                                }catch (JSONException e){
                                    Log.w(TAG, "erreur de parsing de l'élément salle "+i+ " cause:"+e.getMessage());
                                    continue;
                                }
                            }
                        }  catch (JSONException e1) {
                            Log.e(TAG, "erreur de parsing JSON des salles:"+ e1.getMessage());
                        }
                        try {
                            mouvementsArray = jsonObject.getJSONArray("mouvements");

                            for (int i = 0 ; i < mouvementsArray.length(); i++) {
                                try {
                                    JSONObject obj = mouvementsArray.getJSONObject(i);
                                    Integer from = obj.getInt("de");
                                    Integer to = obj.getInt("a");
                                    String mouvement = obj.getString("mouvement");
                                    Log.i(TAG, "Insertion du mouvement: On va de (id):" + from + " vers (id):" + to + " via le déplacement:"+mouvement);
                                    fireBaseDAO.insererMouvement(from, to, mouvement);
                                }catch (JSONException e){
                                    Log.w(TAG, "erreur de parsing de l'élément mouvement "+i+ " cause:"+e.getMessage());
                                    continue;
                                }
                            }
                        }  catch (JSONException e1) {
                            Log.e(TAG, "erreur de parsing JSON des mouvements:"+ e1.getMessage());
                        }
                    }
                }
        );
        backgroundOperation.execute(url);
    }
}
