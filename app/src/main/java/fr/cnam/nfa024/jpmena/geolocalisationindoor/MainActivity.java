package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Iterator;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.firebase.MenuAsyncResponse;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.service.firebase.MenusAsyncTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final  String URLBATIMENTS = "https://geolocalisation-indoor.firebaseio.com/buildingmaps.json?shallow=true";

    public static final  String BATIMENT = "Batiment";

    TextView mTvBase;
    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvBase = (TextView) findViewById(R.id.tvBase);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenusAsyncTask backgroundOperation = new MenusAsyncTask(
                new MenuAsyncResponse() {
                    @Override
                    public void processFinish(JSONObject menusJson) {
                        Iterator<String> keys = menusJson.keys();
                        int indice = 0;

                        while(keys.hasNext()) {
                            String key = keys.next();
                            menu.add(0, indice, 0, key);
                            indice++;
                        }
                        MainActivity.this.mMenu = menu;
                    }
                });
        backgroundOperation.execute(URLBATIMENTS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String batiment = item.getTitle().toString();
        mTvBase.setText("Bâtiment: "+ batiment + " sélectionné");
        Toast.makeText(this, "bâtiment "+batiment +" sélectionné", Toast.LENGTH_LONG).show();
        for(int i = 0; i < mMenu.size(); i++){
            MenuItem mItem = mMenu.getItem(i);
            //Cf. réponse 85 de https://stackoverflow.com/questions/3519277/how-to-change-the-text-color-of-menu-item-in-android
            SpannableString s = new SpannableString(mItem.getTitle());
            if (mItem == item) {
                s.setSpan(new ForegroundColorSpan(Color.GREEN), 0, s.length(), 0);
            } else {
                s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), 0);
            }
            mItem.setTitle(s);
            Intent intentViewSalles = new Intent(this, SallesActivity.class);
            intentViewSalles.putExtra(BATIMENT, batiment);
            startActivity(intentViewSalles);
        }

        return super.onOptionsItemSelected(item);
    }
}
