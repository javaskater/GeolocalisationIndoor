package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

import java.io.Serializable;

/*
* L'objet Mouvement étant trop intriqué avec
* l'objet Salle la serialisation du plus court chmein ne passe pas
* l'Intent
 */

public class SerializableMouvement implements Serializable {
    private Integer idFrom; //idenntifiant de la salle de départ
    private Integer idTo; //identifiant de la salle d'arrivee
    private String deplacement; //description du déplacement
    //private Boolean accessible; //dit si le déplacment est possible ou non en raison de travaux, Vigipirate COVID19

    public Integer getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Integer idFrom) {
        this.idFrom = idFrom;
    }

    public Integer getIdTo() {
        return idTo;
    }

    public void setIdTo(Integer idTo) {
        this.idTo = idTo;
    }

    public String getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(String deplacement) {
        this.deplacement = deplacement;
    }

    /*public Boolean getAccessible() {
        return accessible;
    }

    public void setAccessible(Boolean accessible) {
        this.accessible = accessible;
    }*/

    public Integer getWeight(){
        String [] deplacements = deplacement.split("\\+");
        return deplacements.length;
    }
}
