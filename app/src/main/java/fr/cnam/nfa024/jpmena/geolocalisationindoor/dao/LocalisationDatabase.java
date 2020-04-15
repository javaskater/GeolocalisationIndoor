package fr.cnam.nfa024.jpmena.geolocalisationindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Mouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Salle;

public class LocalisationDatabase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "GeolocalisationCNAM.db";

    private static final String TABLE_SALLES = "salles";
    public static final String FIELD_NUMERO_SALLE = "numero_salle";

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
        long result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 2);
        values.put(FIELD_NUMERO_SALLE, "31.1.02");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 3);
        values.put(FIELD_NUMERO_SALLE, "31.1.03");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 4);
        values.put(FIELD_NUMERO_SALLE, "31.1.04");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 5);
        values.put(FIELD_NUMERO_SALLE, "31.2.01");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 6);
        values.put(FIELD_NUMERO_SALLE, "31.2.02");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 7);
        values.put(FIELD_NUMERO_SALLE, "31.2.03");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 8);
        values.put(FIELD_NUMERO_SALLE, "31.2.04");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 9);
        values.put(FIELD_NUMERO_SALLE, "31.3.01");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 10);
        values.put(FIELD_NUMERO_SALLE, "31.3.02");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 11);
        values.put(FIELD_NUMERO_SALLE, "31.3.03");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        values.put("_id", 12);
        values.put(FIELD_NUMERO_SALLE, "31.3.04");
        result = db.insertOrThrow(TABLE_SALLES, null, values);
        db.execSQL("CREATE TABLE "+TABLE_DEPLACEMENTS+" ( _id integer PRIMARY KEY," +
                FIELD_FROM + " integer, "+ FIELD_TO +" integer, "+ FIELD_MOUVEMENT +" TEXT );");
        values = new ContentValues();
        values.put(FIELD_FROM,1);
        values.put(FIELD_TO,2);
        values.put(FIELD_MOUVEMENT, "SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,2);
        values.put(FIELD_TO,1);
        values.put(FIELD_MOUVEMENT, "NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,1);
        values.put(FIELD_TO,3);
        values.put(FIELD_MOUVEMENT, "EST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,3);
        values.put(FIELD_TO,1);
        values.put(FIELD_MOUVEMENT, "OUEST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,3);
        values.put(FIELD_TO,4);
        values.put(FIELD_MOUVEMENT, "SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,4);
        values.put(FIELD_TO,3);
        values.put(FIELD_MOUVEMENT, "NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,2);
        values.put(FIELD_TO,4);
        values.put(FIELD_MOUVEMENT, "EST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,4);
        values.put(FIELD_TO,2);
        values.put(FIELD_MOUVEMENT, "OUEST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,3);
        values.put(FIELD_TO,7);
        values.put(FIELD_MOUVEMENT, "EST+MONTER+OUEST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,7);
        values.put(FIELD_TO,3);
        values.put(FIELD_MOUVEMENT, "EST+DESCENDRE+OUEST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,4);
        values.put(FIELD_TO,8);
        values.put(FIELD_MOUVEMENT, "EST+MONTER+OUEST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,8);
        values.put(FIELD_TO,4);
        values.put(FIELD_MOUVEMENT, "EST+DESCENDRE+OUEST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,1);
        values.put(FIELD_TO,5);
        values.put(FIELD_MOUVEMENT, "OUEST+MONTER+EST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,5);
        values.put(FIELD_TO,1);
        values.put(FIELD_MOUVEMENT, "OUEST+DESCENDRE+EST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,2);
        values.put(FIELD_TO,6);
        values.put(FIELD_MOUVEMENT, "OUEST+MONTER+EST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,6);
        values.put(FIELD_TO,2);
        values.put(FIELD_MOUVEMENT, "OUEST+DESCENDRE+EST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,5);
        values.put(FIELD_TO,6);
        values.put(FIELD_MOUVEMENT, "SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,6);
        values.put(FIELD_TO,5);
        values.put(FIELD_MOUVEMENT, "NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,5);
        values.put(FIELD_TO,7);
        values.put(FIELD_MOUVEMENT, "EST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,7);
        values.put(FIELD_TO,5);
        values.put(FIELD_MOUVEMENT, "OUEST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,6);
        values.put(FIELD_TO,8);
        values.put(FIELD_MOUVEMENT, "EST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,8);
        values.put(FIELD_TO,6);
        values.put(FIELD_MOUVEMENT, "OUEST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,5);
        values.put(FIELD_TO,9);
        values.put(FIELD_MOUVEMENT, "SUD+OUEST+MONTER+EST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,5);
        values.put(FIELD_TO,10);
        values.put(FIELD_MOUVEMENT, "SUD+OUEST+MONTER+EST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,9);
        values.put(FIELD_TO,5);
        values.put(FIELD_MOUVEMENT, "SUD+OUEST+DESCENDRE+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,10);
        values.put(FIELD_TO,5);
        values.put(FIELD_MOUVEMENT, "NORD+OUEST+DESCENDRE+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);

        values.put(FIELD_FROM,6);
        values.put(FIELD_TO,9);
        values.put(FIELD_MOUVEMENT, "NORD+OUEST+MONTER+EST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,6);
        values.put(FIELD_TO,10);
        values.put(FIELD_MOUVEMENT, "NORD+OUEST+MONTER+EST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,9);
        values.put(FIELD_TO,6);
        values.put(FIELD_MOUVEMENT, "SUD+OUEST+DESCENDRE+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,10);
        values.put(FIELD_TO,6);
        values.put(FIELD_MOUVEMENT, "NORD+OUEST+DESCENDRE+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);

        values.put(FIELD_FROM,9);
        values.put(FIELD_TO,10);
        values.put(FIELD_MOUVEMENT, "SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,10);
        values.put(FIELD_TO,9);
        values.put(FIELD_MOUVEMENT, "NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);

        values.put(FIELD_FROM,9);
        values.put(FIELD_TO,11);
        values.put(FIELD_MOUVEMENT, "EST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,11);
        values.put(FIELD_TO,9);
        values.put(FIELD_MOUVEMENT, "OUEST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);

        values.put(FIELD_FROM,10);
        values.put(FIELD_TO,12);
        values.put(FIELD_MOUVEMENT, "EST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,12);
        values.put(FIELD_TO,10);
        values.put(FIELD_MOUVEMENT, "OUEST");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);

        values.put(FIELD_FROM,12);
        values.put(FIELD_TO,11);
        values.put(FIELD_MOUVEMENT, "NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,11);
        values.put(FIELD_TO,12);
        values.put(FIELD_MOUVEMENT, "SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);

        values.put(FIELD_FROM,11);
        values.put(FIELD_TO,7);
        values.put(FIELD_MOUVEMENT, "SUD+EST+DESCENDRE+OUEST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,7);
        values.put(FIELD_TO,11);
        values.put(FIELD_MOUVEMENT, "SUD+EST+MONTER+OUEST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);

        values.put(FIELD_FROM,12);
        values.put(FIELD_TO,7);
        values.put(FIELD_MOUVEMENT, "NORD+EST+DESCENDRE+OUEST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,7);
        values.put(FIELD_TO,12);
        values.put(FIELD_MOUVEMENT, "SUD+EST+MONTER+OUEST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);

        values.put(FIELD_FROM,11);
        values.put(FIELD_TO,8);
        values.put(FIELD_MOUVEMENT, "SUD+EST+DESCENDRE+OUEST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,8);
        values.put(FIELD_TO,11);
        values.put(FIELD_MOUVEMENT, "NORD+EST+MONTER+OUEST+NORD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);

        values.put(FIELD_FROM,12);
        values.put(FIELD_TO,8);
        values.put(FIELD_MOUVEMENT, "NORD+EST+DESCENDRE+OUEST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        values.put(FIELD_FROM,8);
        values.put(FIELD_TO,12);
        values.put(FIELD_MOUVEMENT, "NORD+EST+MONTER+OUEST+SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getLieuxDepartList() {
        SQLiteDatabase db = getReadableDatabase();
        if (db==null) return null;
        String query = "SELECT _id, " + FIELD_NUMERO_SALLE + " FROM " + TABLE_SALLES + " ORDER BY _id ASC";
        return db.rawQuery(query, null);
    }

    public Cursor getLieuxArriveeList(long idDepart) {
        SQLiteDatabase db = getReadableDatabase();
        if (db==null) return null;
        String query = "SELECT _id, " + FIELD_NUMERO_SALLE + " FROM " + TABLE_SALLES + " WHERE _id <> "+idDepart+" ORDER BY _id ASC";
        return db.rawQuery(query, null);
    }

    public Salle getSalleFromID(int idSalle){
        SQLiteDatabase db = getReadableDatabase();
        if(db == null) return null;
        String query = "SELECT "+FIELD_NUMERO_SALLE+ " FROM " + TABLE_SALLES + " WHERE _id ="+ idSalle;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()){
            String nomSalle = c.getString(c.getColumnIndex(FIELD_NUMERO_SALLE));
            Salle salle = new Salle(idSalle, nomSalle);
            //on recherche les noeuds adjacent dans le sens cette salle vers TO
            return salle;
        } else {
            return null;
        }
    }

    public Integer getIdFromSalle(String numSalle){
        SQLiteDatabase db = getReadableDatabase();
        if(db == null) return null;
        String query = "SELECT _id FROM " + TABLE_SALLES + " WHERE " +FIELD_NUMERO_SALLE + "=" + numSalle;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()){
            int idSalle = c.getInt(c.getColumnIndex("_id"));
            return new Integer(idSalle);
        } else {
            return null;
        }
    }

    public List<Mouvement> getNextElemants(int idSalle){
        SQLiteDatabase db = getReadableDatabase();
        List<Mouvement> suivants = new LinkedList<Mouvement>();
        if(db == null) return null;
        String query = "SELECT "+FIELD_TO+ ","+FIELD_MOUVEMENT+ " FROM " + TABLE_DEPLACEMENTS + " WHERE " +FIELD_FROM+ "="+ idSalle;
        Cursor c = db.rawQuery(query, null);
        if (c != null)
        {
            c.moveToFirst(); //par défaut le curseur est à sa dernière position
            do {
                Mouvement mouvement = new Mouvement();
                mouvement.setIdFrom(idSalle);
                mouvement.setIdTo(c.getInt(c.getColumnIndex(FIELD_TO)));
                mouvement.setDeplacement(c.getString(c.getColumnIndex(FIELD_MOUVEMENT)));
                suivants.add(mouvement);
            } while (c.moveToNext());
            return suivants;
        } else {
            return null;
        }
    }

    public List<Integer> getAllSalles(){
        SQLiteDatabase db = getReadableDatabase();
        List<Integer> idsSalles = new ArrayList<Integer>();
        if(db == null) return null;
        String query = "SELECT _id  FROM " + TABLE_SALLES + " ORDER BY "+ FIELD_NUMERO_SALLE + " ASC";
        Cursor c = db.rawQuery(query, null);
        if (c != null)
        {
            c.moveToFirst(); //par défaut le curseur est à sa dernière position
            do {
                int idSalle = c.getInt(c.getColumnIndex("_id"));
                idsSalles.add(new Integer(idSalle));
            } while (c.moveToNext());
            return idsSalles;
        } else {
            return null;
        }
    }
}
