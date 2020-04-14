package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.dao.LocalisationDatabase;

public class SallesAdapter extends CursorAdapter {


    public SallesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        textView.setText(cursor.getString(cursor.getColumnIndex(LocalisationDatabase.FIELD_NUMERO_SALLE)));
    }
}
