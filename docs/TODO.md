# 08/04/2020
* la base SQLIte estt OK
* les Spinner fonctionnent
* il faut commencer le parcours du graphe

# 14/04/2020
* L'algorithme de [Dijkstra trouvé sur Internet](https://www.baeldung.com/java-dijkstra) retourne un Graphe. Or c'est le shortestPath que j'aurai aimé retrouver...
* comment mettre la recherche dans un service
* comment passer de l'activité principale à une ou des activités qui affichent les résultats
* Comment proposer des Tags tout au longg du parcours ?
# 15/04/2020
* regarder [L'utilisation du graphe construit](https://github.com/eugenp/tutorials/blob/c15908c3d213aaea96add429361357d7d2525a66/algorithms-miscellaneous-2/src/test/java/com/baeldung/algorithms/DijkstraAlgorithmLongRunningUnitTest.java#L48)
  * et en profiter pour parcourir mon propre graphe
```java
  /*
     * à partir de l'ID de destination on veut retrouver le chemin des salles du fichier source
     * cette méthode est à appeler une fois l'algorithme de Dijkstra passé
     */
    public List<Salle> retournePlusCourtChemin(Integer idDestination){
        Salle destination = this.mLocalisationDatabase.getSalleFromID(idDestination); //rechercher dans le grapde retourné par L'algorithme et nom le mdB!!!!!
        List<Salle> plusCourtChemin = destination.getShortestPath();
        if(plusCourtChemin != null && plusCourtChemin.size() > 0){
            plusCourtChemin.add(destination);
        }
        return plusCourtChemin;
    }
```

# Transitions et Compass sensor data:

* livre AndroidCookbook 
  * p 222 (Transition animation) 
  * et 227 (Creating a compass using sensor data and animation)
  
# 17/04/2020
* Tester le passage de la main Activity à l'activité suivante
* Trouver un moyen d'afficher les étapes en carousel
* insérer la boussole dans chaque étape
