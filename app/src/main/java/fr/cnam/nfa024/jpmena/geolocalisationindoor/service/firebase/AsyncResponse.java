package fr.cnam.nfa024.jpmena.geolocalisationindoor.service.firebase;

import org.json.JSONObject;

public interface AsyncResponse {
    void processFinish(JSONObject output);
}
