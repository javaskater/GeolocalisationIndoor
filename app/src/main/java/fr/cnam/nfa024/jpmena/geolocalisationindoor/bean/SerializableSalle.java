package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/*
 * L'objet Mouvement étant trop intriqué avec
 * l'objet Salle la serialisation du plus court chmein ne passe pas
 * l'Intent
 */

public class SerializableSalle implements Serializable {

    private String name;

    private Integer identifiant;

    public SerializableSalle(Integer id, String name) {
        this.name = name;
        this.identifiant = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }




}
