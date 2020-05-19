package fr.cnam.nfa024.jpmena.geolocalisationindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Mouvement;
import fr.cnam.nfa024.jpmena.geolocalisationindoor.bean.Salle;

public class LocalisationDatabase extends SQLiteOpenHelper {

    private static final String TAG = "LocalisationDatabase";

    private static final String DATABASE_NAME = "GeolocalisationCNAM.db";

    public static final String TABLE_SALLES = "salles";
    public static final String FIELD_NUMERO_SALLE = "numero_salle";


    public static final String TABLE_DEPLACEMENTS = "deplacements";
    private static final String FIELD_FROM = "de";
    private static final String FIELD_TO = "a";
    private static final String FIELD_MOUVEMENT = "mouvement";

    //commun à la table Salle et à la table Mouvement
    private static final String FIELD_ACCESSIBLE = "accessible";


    private static final int DATABASE_VERSION = 2;

    public LocalisationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "On lance la création de la table "+TABLE_SALLES);
        db.execSQL("CREATE TABLE "+TABLE_SALLES+" ( _id integer PRIMARY KEY," +
                FIELD_NUMERO_SALLE + " TEXT,"+FIELD_ACCESSIBLE+" INTEGER DEFAULT 1);");
        Log.i(TAG, "On lance la création de la table "+TABLE_DEPLACEMENTS);
        db.execSQL("CREATE TABLE "+TABLE_DEPLACEMENTS+" ( _id integer PRIMARY KEY," +
                FIELD_FROM + " integer, "+ FIELD_TO +" integer, "+ FIELD_MOUVEMENT +" TEXT, "+FIELD_ACCESSIBLE+" INTEGER DEFAULT 1);");
    }

    //ne fonctionne pas
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Ancienne version:: "+ oldVersion+ " version actuelle "+ newVersion);
        //cf. https://thebhwgroup.com/blog/how-android-sqlite-onupgrade
        if (oldVersion < 2) {
            Log.i(TAG, "On lance les mises à jour des tables "+TABLE_SALLES+ " et "+TABLE_DEPLACEMENTS);
            db.execSQL("ALTER TABLE "
                    + TABLE_SALLES + " ADD COLUMN " + FIELD_ACCESSIBLE + " INTEGER DEFAULT 1;");
            db.execSQL("ALTER TABLE "
                    + TABLE_DEPLACEMENTS + " ADD COLUMN " + FIELD_ACCESSIBLE + " INTEGER DEFAULT 1;");
        }

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

    public Integer viderTable(String nomTable){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(nomTable, null, null);
    }

    public long insererSalle(Integer id, String numeroSalle, Boolean accessible){
        Integer iAccessible = new Integer(1);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put(FIELD_NUMERO_SALLE, numeroSalle);
        if(accessible.booleanValue()){
            iAccessible = new Integer(1);
        } else {
            iAccessible = new Integer(0);
        }
        values.put(FIELD_ACCESSIBLE, iAccessible);
        long result = db.insertOrThrow(TABLE_SALLES, null, values);
        return result;
    }

    public long insererMouvement(Integer from, Integer to, String mouvement, Boolean accessible){
        Integer iAccessible = new Integer(1);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_FROM,from);
        values.put(FIELD_TO,to);
        values.put(FIELD_MOUVEMENT, mouvement);
        if(accessible.booleanValue()){
            iAccessible = new Integer(1);
        } else {
            iAccessible = new Integer(0);
        }
        values.put(FIELD_ACCESSIBLE, iAccessible);
        long result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
        return result;
    }
}
