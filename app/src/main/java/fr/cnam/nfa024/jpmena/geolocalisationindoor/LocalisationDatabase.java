package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalisationDatabase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "GeolocalisationCNAM.db";

    private static final String TABLE_SALLES = "salles";
    private static final String FIELD_NUMERO_SALLE = "numero_salle";

    private static final String TABLE_DEPLACEMENTS = "deplacements";
    private static final String FIELD_FROM = "de";
    private static final String FIELD_TO = "a";
    private static final String FIELD_MOUVEMENT = "mouvement";



    private static final int DATABASE_VERSION = 1;

    public LocalisationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_SALLES+" ( _id integer PRIMARY KEY," +
                FIELD_NUMERO_SALLE + " TEXT);");
        ContentValues values = new ContentValues();
        values.put("_id", 1);
        values.put(FIELD_NUMERO_SALLE, "31.1.01");
        values.put("_id", 2);
        values.put(FIELD_NUMERO_SALLE, "31.1.02");
        values.put("_id", 3);
        values.put(FIELD_NUMERO_SALLE, "31.1.03");
        values.put("_id", 4);
        values.put(FIELD_NUMERO_SALLE, "31.1.04");
        values.put("_id", 5);
        values.put(FIELD_NUMERO_SALLE, "31.2.01");
        values.put("_id", 6);
        values.put(FIELD_NUMERO_SALLE, "31.2.02");
        values.put("_id", 7);
        values.put(FIELD_NUMERO_SALLE, "31.2.03");
        values.put("_id", 8);
        values.put(FIELD_NUMERO_SALLE, "31.2.04");
        values.put("_id", 9);
        values.put(FIELD_NUMERO_SALLE, "31.3.01");
        values.put("_id", 10);
        values.put(FIELD_NUMERO_SALLE, "31.3.02");
        values.put("_id", 11);
        values.put(FIELD_NUMERO_SALLE, "31.3.03");
        values.put("_id", 12);
        values.put(FIELD_NUMERO_SALLE, "31.3.04");
        long result = db.insertOrThrow(TABLE_SALLES, null, values);
        db.execSQL("CREATE TABLE "+TABLE_DEPLACEMENTS+" ( _id integer PRIMARY KEY," +
                FIELD_FROM + " integer, "+ FIELD_TO +" integer, "+ FIELD_MOUVEMENT +" TEXT );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
