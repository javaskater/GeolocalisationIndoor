package fr.cnam.nfa024.jpmena.geolocalisationindoor.dao;

import android.content.Context;
import android.database.Cursor;

public class FireBaseDAO {

    private static FireBaseDAO ourInstance;

    private Context activityContext;

    private LocalisationDatabase mLocalisationDatabase;

    public static FireBaseDAO getInstance(Context context) {
        ourInstance = new FireBaseDAO(context);
        return ourInstance;
    }

    private FireBaseDAO(Context context) {
        this.activityContext = context;
        this.mLocalisationDatabase = new LocalisationDatabase(this.activityContext);
    }

    public void initialiserDatabase(){
        this.mLocalisationDatabase.viderTable(LocalisationDatabase.TABLE_SALLES);
        this.mLocalisationDatabase.viderTable(LocalisationDatabase.TABLE_DEPLACEMENTS);
    }

    public long insererSalle(Integer salleId, String nomSalle){
        return this.mLocalisationDatabase.insererSalle(salleId, nomSalle);
    }

    public long insererMouvement(Integer from, Integer to, String mouvement){
        return this.mLocalisationDatabase.insererMouvement(from, to, mouvement);
    }

    public Cursor getLieuxDepartList(){
        return this.mLocalisationDatabase.getLieuxDepartList();
    }

    public Cursor getLieuxArriveeList(long idDepart){
        return this.mLocalisationDatabase.getLieuxArriveeList(idDepart);
    }
}
