package fr.cnam.nfa024.jpmena.geolocalisationindoor.service.firebase;

import android.widget.Spinner;

import org.json.JSONObject;

public interface AsyncResponse {
    void processFinish(JSONObject output, Spinner lieuDepart, Spinner lieuArrivee);
}
