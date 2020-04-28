# Le plan des salles du batiment

* On utilise une base SQLIte. La gestion de cette base se fait via la méthode **oncreate** de la classe *dao/LocalisationDatabase.java*.
* 2 tables la table *Salles* et la table *Déplacements*

## la table Salles:
 * Elle modélise les noeuds de mon graphe.
 * Elle est composée de 2 champs:
   * un champs **_id** qui est un entier et contitue la clé primaire
   * un champs **numero_salle** qui est une chaine de caractères reprenant le numéro de la salle telle que affiché sur la porte et que l'on devra retrouver sur le QRCode
   * On entre 12 salles, 4 salles par niveau, 2 salles de chaque côté du couloir
 * Ci dessous un extrait du code Java de création des ssalles. on s'est arrêté à la création de la première salle
```java
        db.execSQL("CREATE TABLE "+TABLE_SALLES+" ( _id integer PRIMARY KEY," +
                FIELD_NUMERO_SALLE + " TEXT);");
        ContentValues values = new ContentValues();
        values.put("_id", 1);
        values.put(FIELD_NUMERO_SALLE, "31.1.01");
        long result = db.insertOrThrow(TABLE_SALLES, null, values);
```

## la table Deplacements:

* Elle modélise les arêtes de mon graphe orienté!
* chaque ligne de cette table représente un déplacement élémentaire entre 2 salles voisines. 
On entend par voisines 2 salle séparées par un déplacement élémentaire.
  * rejoindre la salle d'à coté ou d'en face
  * monter ou descendre d'un étage pour retrouver la table correspondante de bout de couloir

* cette table est constituée de 3 champs:
  * le champs **from** reprend le champs *_id* de la table Salles pour repérer la salle d'ou part le déplaccement élémentaire
  * le champs **to** reprend le champs *_id* de la table Salles pour repérer la salle ou arrive le déplaccement élémentaire
  * le champs **mouvement** est unn champs de type String qui décrit de déplacement élémentaire de façon codifiée:
    * par exemple **EST+MONTER+OUEST+NORD** signifie aller à l'est jusqu'à rencontrer un escalier, monter cet escalier
    allez à l'oust jsuqu'à rencontrer une salle puis aller vers le nord jusqu'à rencontrer la salle qui nous intéresse.
    Ce déplacement à un poids de 4 car 4 indications
* Ci dessous un extrait de code java qui crée la table *deplacements* et y insère une première ligne
  * on va de las alle 31.1.01 (id 1) à la salle 31.1.02 (id: 2) en se diriggeant vers le sud. Une seule indication
  Ce déplacement a donc un moids de 1.   
```java
        db.execSQL("CREATE TABLE "+TABLE_DEPLACEMENTS+" ( _id integer PRIMARY KEY," +
                FIELD_FROM + " integer, "+ FIELD_TO +" integer, "+ FIELD_MOUVEMENT +" TEXT );");
        values = new ContentValues();
        values.put(FIELD_FROM,1);
        values.put(FIELD_TO,2);
        values.put(FIELD_MOUVEMENT, "SUD");
        result = db.insertOrThrow(TABLE_DEPLACEMENTS, null, values);
```

# L'algorithme de recherche de déplacements:

* partir d'une salle de départ et d'une salle d'arrivée trouver le chemin optimal 
dans le graphe orienté décrit par les tables *salles* et *deplacments*
* Dans un premier temps les salles de départ et d'arrivée sont chacune choisies à partir d'un Spinnner
  * le spinner d'arrivée et son adaptateur reprend toutes les valeurs de la table *Salles* à l'exception 
  de la valeur sélectionnée par le spinner SAlle de départ.
* Pour cela on reprend [cette implémentation Java](https://www.baeldung.com/java-dijkstra) de l'algorithme de Dijkstra.

# passage de l'action principale à l'action de visualisation

* Les objets **Salle** et **Mouvement**  ayant trop de références croisées  leur serialisation 
n'est pas possible. Je suis donc passé en objets simplifiés **SerializableSalle** et **SerilizableMouvement** de
même que **SerializableParcours** 
  * La verification que le parcours s'est bien transmis se fait via LogCat en se limitant au messages de la classe
  *info* avec un filtre portant sur *ViewCourseActivity*
* la grande question qui se pose est comment produire une visualisation des déplacements
 * demmarer une 3ème activité qui affichera une boussole et une étape du parcours ?                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        

# Passage de l'action de visualisation du pacours à l'étape de visualisation d'une étape

* La visualisation du pacours affiche une liste 'étape avec un Go après chacune d'entre elles
  * ce qui nous renvoie à EtapeActivity
* Pour chaque étape on a:
  * soit un mouvement simple représenté par un direction à suivre
  * soit un changement d'étage représenté par un ensemble de directions à prendre séparéee par une action de montée ou de descente
    * l'ensemble de directions à suivre permet soir de se rendre à la cage d'escalier
    * soit de quitter la cage d'escalier pour rejoindre la salle la plus proche
# Passage de l'action de parcours de l'étape à l'affichage des mouvements 
 * comme pour la visualisation des étapes nous allons ici visualiser les mouvements
   * un bouton détail premettra d'accéder soit à la boussole (cf. chapitre ci dessous)
   * soit à un podomètre lors d'un changement de niveau 
    
## la visualistation d'une direction à suivre!

* Elle se fait en affichant une boussole comme montré dans le [livre PAckt Android Cookbook developement](https://www.packtpub.com/application-development/android-application-development-cookbook-second-edition)
  * page 230 (ou 249/428). On part d'une [image de boussole](https://pixabay.com/en/geography-map-compass-rose-plot-42608/)
  
* Pour ajouter une flèche indiquant la direction réelle à suivre on affiche suivant la direction
  * une image différente sur laquelle la direction souhaitée a été visualisée par une flèche 
  jaune ajoutée avec GIMP sur [l'image de base de boussole](https://pixabay.com/en/geography-map-compass-rose-plot-42608/)
  * ceci m'a été suggéré sur [StackOverflow](https://stackoverflow.com/questions/61425687/adding-a-circle-on-an-imagei-n-android/61425779#61425779)
  en réponse à ma question... 
 
## compter les pas quand on monte ou descend un escalier

### Plusieurs références:
* une video qui explique comment utiliser l'acceleromètre [video](https://programmerworld.co/android/how-to-create-walking-step-counter-app-using-accelerometer-sensor-and-shared-preference-in-android/)
  * inconvenient, il ne prend pas en compte les faux positifs, il se base juste sur la norme
  * avantage: il garde en mémoire physique via les shared preferences la dernière valeur du nombre de pas.
* un [lien](https://montemagno.com/part-1-my-stepcounter-android-step-sensors/) qui explique comment utiliser directement le STEP DETECTOR
  * problème ce n'est pas en java mais dans un langage très proche *.cs*
  * je n'ai que des morceaux de code et pas le code intégral qui est sur GitHub
* utiliser le StepCounter comme indiqué sur [PAcktPub AndroidSensor Programming](https://hub.packtpub.com/step-detector-and-step-counters-sensors/)
## j'ai utilisé le Step Detector
* hier 27/04/2020 il ne passait jamais dans la méthode _onSensorChangedonSensorChanged_ du listener.
* Aujourd'hui j'ai changé le délai de prise en compte de SENSOR_DELAY_NORMAL vers SENSOR_DELAY_UI
* le nombre de pas est pris en charge avec un peu de reatard
```java
mSensorManager.registerListener(mSensorStepsListener,mStepCounter, SensorManager.SENSOR_DELAY_UI);
```
* faire cohabiter le comptage de pas en arrière plan 
avec la mise à jour de l'interface n'est ce pas risqué ?
```java
private SensorEventListener mSensorStepsListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {


            mNombrePas ++;
            mCompteurPas.setText("nombre de pas realisés:" + String.valueOf(mNombrePas));
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //Nothing to do
        }
    };
```