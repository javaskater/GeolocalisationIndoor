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

# 20/04/2020
* Dans la ViewCoursActivity récupérer du SerializableChemin le mouvement unitaire 
et l'afficher
* Un mouvement se compose de plusieurs étapes et chacune est à afficher avec soit la boussole 
ou un compte pas ?

# 23/04/2020

* vue detail Mouvement doit maintenant soit afficher une boussole si le Mouvement est NORD, SUD, EST, OUEST
* soit afficher un escalier avec une flèche si le mouvement est monter ou descendre
* voir le livre Packt CookBook pour la boussole
* voir le cours du professeur 2018-2019 pour le Canvas

# 26/04/2020

* pour monter ou descendre trouver une image d'escalier montant et une image d'escalier descendant
* Trouver s'il est possible de compter les pas ?
* retrouver sur 2018-2019 le TP d'appel d'un visualisateur de QRCodes
* QRCodes, trouver un générateur de QRCodes

# 29/04/2020

## passer à la phase suivante du projet

* mettre les plans de bâtiment au format JSON sur Firebase.
* sélectionner un des plans présents
* Téléchager le plan et le mettre dans la base SQLite ...

# 06/05/2020

* toute l'initialisation de l'interface du MainActivity doit être faite dans le *InitDatabase.java* dans le
```java
new AsyncResponse() {
                    @Override
                    public void processFinish(JSONObject jsonObject) {
                        JSONArray sallesArray = null;
```
* Sinon les Spinner plantent (car les curseurs n'ont aucun élément!

# 14/05/2020

* Passage d'un calcul dans le SallesAction.java à un appel à un service de Parcours Optimal!!!
```
IntentService[ParcoursOptimalService]
    Process: fr.cnam.nfa024.jpmena.geolocalisationindoor, PID: 21659
    android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity  context 
    requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
```
* Faut il passer par une notification ?

# 15/05/2020

* Notification OK [cf. ce lien](https://openclassrooms.com/fr/courses/2023346-creez-des-applications-pour-android/2027704-les-services#/id/r-2033586) complété par [ce post StackOverflow](https://stackoverflow.com/questions/32345768/cannot-resolve-method-setlatesteventinfo)
* TODO problème l'icone de Map ne s'affiche que quand on ouvre la barre de notification....
* TODO avoir une icône sur la barre ...

# 18/05/2020

* J'ai à résoudre cette difficulté [Post Stackoverflow](https://stackoverflow.com/questions/61874377/pending-intent-does-not-fire)
  * Le click sur l'icone ne fait plus rien (Emulateur API 19)
  * Pas d'icône de notification sur mon XIAOMI 
  
# 23/05/2020

* EtapeActivity et MouvementActivity doivent connaître l'indice de l'étape du mouvement
fait pour retourner dans l'écran principal avec cette valeur ?
* C'est possible avec un [StartActivityForResult](https://stackoverflow.com/questions/40372240/bundles-or-putextra-when-executing-finish)
* TODO : une ListView with chekboxes ? [SO](https://stackoverflow.com/questions/8060514/android-listview-with-check-boxes/8060732#8060732)
