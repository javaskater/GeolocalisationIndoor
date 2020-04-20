package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlusCourtChemin implements Serializable {

    private List<Salle> mCheminOptimal;

    public PlusCourtChemin(List<Salle> mCheminOptimal) {
        this.mCheminOptimal = mCheminOptimal;
    }

    public List<Salle> getmCheminOptimal() {
        return mCheminOptimal;
    }

    public void setmCheminOptimal(List<Salle> mCheminOptimal) {
        this.mCheminOptimal = mCheminOptimal;
    }

    public List<String> toStringsForLogCat(){
        List<String> cheminOptimalStr = new ArrayList<String>();
        for(int i =0; i < mCheminOptimal.size(); i++){
            Salle salleEnCours = mCheminOptimal.get(i);
            if (i < mCheminOptimal.size() - 1){
                Salle sallesuivante =  mCheminOptimal.get(i+1);
                Mouvement mouvement = null;
                for( Salle salleAdjacente: salleEnCours.getAdjacentSalles().keySet()){
                    if(salleAdjacente == sallesuivante){
                        mouvement = salleEnCours.getAdjacentSalles().get(salleAdjacente);
                        cheminOptimalStr.add("déplacement de :"+salleEnCours.getName()+ " à :"+sallesuivante.getName()+"en passant par:"+ mouvement.getDeplacement());
                    }
                }
            }
        }
        return cheminOptimalStr;
    }

    /*
    * PlusCourtChemin n'est pas sérializable j'ai doncc besoin ici de simplifier
    * le chemin optimal que l'on va passer à l'intent de MainActivity
     */

    public SerializablePlusCourtChemin prepareSerialisation(){
        SerializablePlusCourtChemin serializablePlusCourtChemin = new SerializablePlusCourtChemin();
        List<SerializableMouvement> mouvements = serializablePlusCourtChemin.getmMouvements();
        List<SerializableSalle> salles = serializablePlusCourtChemin.getmSalles();
        for(int i =0; i < mCheminOptimal.size(); i++){
            Salle salleEnCours = mCheminOptimal.get(i);
            SerializableSalle serializableSalle = new SerializableSalle(salleEnCours.getIdentifiant(), salleEnCours.getName());
            salles.add(serializableSalle);
            if (i < mCheminOptimal.size() - 1){
                Salle sallesuivante =  mCheminOptimal.get(i+1);
                Mouvement mouvement = null;
                for( Salle salleAdjacente: salleEnCours.getAdjacentSalles().keySet()){
                    if(salleAdjacente == sallesuivante){
                        mouvement = salleEnCours.getAdjacentSalles().get(salleAdjacente);
                        SerializableMouvement serializableMouvement = new SerializableMouvement();
                        serializableMouvement.setIdFrom(salleEnCours.getIdentifiant());
                        serializableMouvement.setIdTo(salleAdjacente.getIdentifiant());
                        serializableMouvement.setDeplacement(mouvement.getDeplacement());
                        mouvements.add(serializableMouvement);
                    }
                }
            }
        }
        return serializablePlusCourtChemin;
    }


    /*
    * récupérer un mouvement particulier dans la liste
    * d'étape du chemin optimal. le numero d'étape commence à 0
     */
    public Mouvement getEtape(int numeroEtape){
        Mouvement mouvement = null;
        if (numeroEtape < mCheminOptimal.size() - 1){
            Salle salleEnCours = mCheminOptimal.get(numeroEtape);
            Salle sallesuivante =  mCheminOptimal.get(numeroEtape+1);
            for( Salle salleAdjacente: salleEnCours.getAdjacentSalles().keySet()){
                if(salleAdjacente == sallesuivante){
                    mouvement = salleEnCours.getAdjacentSalles().get(salleAdjacente);
                }
            }
        }
        return  mouvement;
    }
}
