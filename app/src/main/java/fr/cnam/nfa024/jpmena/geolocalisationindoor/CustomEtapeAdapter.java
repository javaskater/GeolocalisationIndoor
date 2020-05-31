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

import java.util.ArrayList;
import java.util.List;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.SerializableEtape;

public class CustomEtapeAdapter extends ArrayAdapter<SerializableEtape> {

    public CustomEtapeAdapter(Context context, List<SerializableEtape> lesEtapes) {
        super(context, 0, lesEtapes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_etape, parent, false);
        }

        // Get the data item for this position
        final SerializableEtape etape = getItem(position);

        // Lookup view for data population
        final TextView tvFrom = (TextView) convertView.findViewById(R.id.tvFrom);
        final TextView tvTo = (TextView) convertView.findViewById(R.id.tvTo);

        CheckBox cbDone = (CheckBox) convertView.findViewById(R.id.checkbox_done);
        cbDone.setChecked(etape.getmEtapeTerminee());
        // Populate the data into the template view using the data object
        //Do note populate with an Integer setText takes String
        //see https://stackoverflow.com/questions/26001780/android-content-res-resourcesnotfoundexception-string-resource-id-0x1-error
        tvFrom.setText(etape.getmSalleFrom().getName());
        tvTo.setText(etape.getmSalleTo().getName());

        Button action = (Button) convertView.findViewById(R.id.tvAction);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "From:"+tvFrom.getText().toString()+ " to: " + tvTo.getText().toString() + "By:" + etape.getmMouvement().getDeplacement(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), EtapeActivity.class);
                intent.putExtra(ViewCourseActivity.ETAPE, etape); //step must be serializable
                ((Activity)getContext()).startActivityForResult(intent, ViewCourseActivity.REQUEST_CODE); //thanks to answer 62 of https://stackoverflow.com/questions/2848775/use-startactivityforresult-from-non-activity
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
