package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

public class Mouvement {
    private Integer idFrom; //idenntifiant de la salle de départ
    private Salle salleFrom; // salle de départ
    private Integer idTo; //identifiant de la salle d'arrivee
    private Salle salleTo; // la salle d'arrivee
    private String deplacement; //description du déplacement

    public Integer getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Integer idFrom) {
        this.idFrom = idFrom;
    }

    public Salle getSalleFrom() {
        return salleFrom;
    }

    public void setSalleFrom(Salle salleFrom) {
        this.salleFrom = salleFrom;
    }

    public Integer getIdTo() {
        return idTo;
    }

    public void setIdTo(Integer idTo) {
        this.idTo = idTo;
    }

    public Salle getSalleTo() {
        return salleTo;
    }

    public void setSalleTo(Salle salleTo) {
        this.salleTo = salleTo;
    }

    public String getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(String deplacement) {
        this.deplacement = deplacement;
    }

    public Integer getWeight(){
        String [] deplacements = deplacement.split("\\+");
        return deplacements.length;
    }
}
