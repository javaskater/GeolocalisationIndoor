package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/*
 * L'objet Mouvement étant trop intriqué avec
 * l'objet Salle la serialisation du plus court chmein ne passe pas
 * l'Intent
 */

public class SerializablePlusCourtChemin implements Serializable {

    private List<SerializableSalle> mSalles = new ArrayList<SerializableSalle>();
    private List<SerializableMouvement> mMouvements = new ArrayList<SerializableMouvement>();

    public List<SerializableSalle> getmSalles() {
        return mSalles;
    }

    public void setmSalles(List<SerializableSalle> mSalles) {
        this.mSalles = mSalles;
    }

    public List<SerializableMouvement> getmMouvements() {
        return mMouvements;
    }

    public void setmMouvements(List<SerializableMouvement> mMouvements) {
        this.mMouvements = mMouvements;
    }

    private SerializableSalle findSalle(Integer salleId){
        for(SerializableSalle serializableSalle:this.mSalles){
            if (serializableSalle.getIdentifiant() == salleId){
                return serializableSalle;
            }
        }
        return null;
    }

    public List<String> toLogcat(){
        List<String> liste = new ArrayList<String>();
        for(SerializableMouvement serializableMouvement:this.getmMouvements()){
            SerializableSalle salleFrom = findSalle(serializableMouvement.getIdFrom());
            SerializableSalle salleTo = findSalle(serializableMouvement.getIdTo());
            liste.add("on va de :"+ salleFrom.getName() + " vers : "+ salleTo.getName()+ "en faisant :"+ serializableMouvement.getDeplacement());
        }
        return liste;
    }
    // TODO ajouter un accèsEtape i:
}
