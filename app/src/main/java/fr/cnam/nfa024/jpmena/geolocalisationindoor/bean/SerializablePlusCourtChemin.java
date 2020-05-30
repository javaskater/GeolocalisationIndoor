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

    public static final long serialVersionUID = 6330414299656389849L;


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
    /*
    * Accès SerializableEtape i
    * pour afficchage des Etapes
     une à une dans la vue */
    public SerializableEtape extractEtape(Integer indiceEtapeDansChemin){
        if (indiceEtapeDansChemin < this.getmMouvements().size()){
            SerializableMouvement serializableMouvement = this.getmMouvements().get(indiceEtapeDansChemin);
            SerializableSalle salleFrom = findSalle(serializableMouvement.getIdFrom());
            SerializableSalle salleTo = findSalle(serializableMouvement.getIdTo());
            SerializableEtape serializableEtape = new SerializableEtape();
            serializableEtape.setmSalleFrom(salleFrom);
            serializableEtape.setmSalleTo(salleTo);
            serializableEtape.setmMouvement(serializableMouvement);
            return serializableEtape;
        }
        return null;
    }

    /* Accès la liste des étapes
    * pour afficche de la liste des étapes via un custom Array Adpater */
    public List<SerializableEtape> extractEtapes(){
        ArrayList<SerializableEtape> lesEtapes = new ArrayList<SerializableEtape>();
        for (int i = 0; i < this.getmMouvements().size(); i++){
            lesEtapes.add(extractEtape(i));
        }
        return lesEtapes;
    }



    public int getNombreEtapes(){
        return this.getmMouvements().size();
    }

}
