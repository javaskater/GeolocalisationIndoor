package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

public class Mouvement {
    private Integer idFrom; //idenntifiant de la salle de départ
    private Integer idTo; //identifian de la salle d'arrivee
    private String deplacement; //description du déplacement

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

    public Integer getWeight(){
        String [] deplacements = deplacement.split("\\+");
        return deplacements.length;
    }
}
