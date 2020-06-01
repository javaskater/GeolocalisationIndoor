package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableDeplacement;

public class CustomDeplacementAdapter extends ArrayAdapter<SerializableDeplacement> {

    private final static int REQUEST_CODE = 3;

    public CustomDeplacementAdapter(Context context, List<SerializableDeplacement> lesDeplacments) {
        super(context, 0, lesDeplacments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_deplacement, parent, false);
        }

        // Get the data item for this position
        final SerializableDeplacement deplacement = getItem(position);

        // Lookup view for data population
        final TextView tvDeplacement = (TextView) convertView.findViewById(R.id.tvDeplacement);

        CheckBox cbDone = (CheckBox) convertView.findViewById(R.id.checkbox_done);
        cbDone.setChecked(deplacement.getmFait());
        // Populate the data into the template view using the data object
        //Do note populate with an Integer setText takes String
        //see https://stackoverflow.com/questions/26001780/android-content-res-resourcesnotfoundexception-string-resource-id-0x1-error
        tvDeplacement.setText(deplacement.getmDeplacement());

        Button action = (Button) convertView.findViewById(R.id.tvAction);
        action.setText("Voir mouvement "+ (position + 1));
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Mouvemment:"+tvDeplacement.getText().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MouvementActivity.class);
                intent.putExtra(EtapeActivity.MOUVEMENT, deplacement); //step must be serializable
                ((Activity)getContext()).startActivityForResult(intent, REQUEST_CODE); //thanks to answer 62 of https://stackoverflow.com/questions/2848775/use-startactivityforresult-from-non-activity
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
