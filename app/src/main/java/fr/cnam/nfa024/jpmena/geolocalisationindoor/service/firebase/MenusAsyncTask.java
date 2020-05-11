package fr.cnam.nfa024.jpmena.geolocalisationindoor.service.firebase;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MenusAsyncTask extends AsyncTask<String, Void, JSONObject> {

    public static final String TAG = "AsyncFireBase";

    private MenuAsyncResponse delegate;

    public MenusAsyncTask(MenuAsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected JSONObject doInBackground(String... params){
        //Your network connection code should be here .
        String urlString = params[0];
        JSONObject response = null;
        try {
            response = getJSONObjectFromURL(urlString);
        } catch (IOException e) {
            Log.e(TAG, "probbleme de lecture du json salles:"+ e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, "probbleme de parsing du json salles:"+ e.getMessage());
        }
        return response ;
    }

    public JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        Log.i(TAG,jsonString);

        return new JSONObject(jsonString);
    }

    @Override
    protected void onPostExecute(JSONObject menuJson) {
        delegate.processFinish(menuJson);
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}

}
