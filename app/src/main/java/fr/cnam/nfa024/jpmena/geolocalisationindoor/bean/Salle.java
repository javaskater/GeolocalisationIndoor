package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Salle {

    private List<Integer> idSallesVoisines = new LinkedList<Integer>();
    private long idSallePred; //L'identifiant de la salle qui me précède dans l'algorithme
    private int distanceNoeudDepart;
    private int idSalle;
    private String nomSalle;
    private int couleur; //1 = white pas visité, 2 Gray visité mais pas testée, 3 Black visitée et testée

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public List<Integer> getIdSallesVoisines() {
        return idSallesVoisines;
    }

    public void setIdSallesVoisines(List<Integer> idSallesVoisines) {
        this.idSallesVoisines = idSallesVoisines;
    }

    public long getIdSallePred() {
        return idSallePred;
    }

    public void setIdSallePred(long idSallePred) {
        this.idSallePred = idSallePred;
    }

    public int getDistanceNoeudDepart() {
        return distanceNoeudDepart;
    }

    public void setDistanceNoeudDepart(int distanceNoeudDepart) {
        this.distanceNoeudDepart = distanceNoeudDepart;
    }
}
