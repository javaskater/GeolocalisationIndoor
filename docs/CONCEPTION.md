# Le plan des salles du batiment

* On utilise une base SQLIte. La gestion de cette base se fait via la méthode **oncreate** de la classe *dao/LocalisationDatabase.java*.
* 2 tables la table *Salles* et la table *Déplacements*

## la table Salles:
 * Elle modélise les noeuds de mon graphe.
 * Elle est composée de 3 champs:
   * un champs **_id** qui est un entier et contitue la clé primaire
   * un champs **numero_salle** qui est une chaine de caractères reprenant le numéro de la salle telle que affiché sur la porte et que l'on devra retrouver sur le QRCode
   * un champs accessible qui est un booléen indiquant si la salle est disponible ou non (condamnée travaux ou autres)
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
  * le champs **accessible** est un champs de type Booléen qui indique si cette portion du parccours est ouverte ou non
    * (Sens de circulation cause COVID, travaux, plan vigipirate)
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

# QRCodes

## Leur génération:

* LA salle de départ peut être choisie avec le Spinner ou en scannant un QRCode
  * Pour génréer un QRCODE j'ai utilisé [Ce générateur web](https://www.the-qrcode-generator.com/)
  * j'ai 2 images de QRCode
    * la salle et valeur 31.1.01
    * la salle et valeur 31.3.02
  * Leurs images sont sous docs...
  
## Leur lecture

* Je réutilise [le TP chef d'Orchestre (Exercice 2 ED 4)](http://jeanferdysusini.free.fr/Cours/CP48/index_2019.php)
* Comme dans le TP du chef d'Orchestre je reprends le [scanner zxing](https://github.com/zxing/zxing) avec le code d'appel suivant:
```java
Intent photoIntent  = new Intent("com.google.zxing.client.android.SCAN");
PackageManager pm = getPackageManager();
List<ResolveInfo> listeActivites = pm.queryIntentActivities(photoIntent,0);
if(listeActivites.size() > 0){
    startActivityForResult(photoIntent, Util.REQUEST_SCANNER);
}else{
    Toast.makeText(this, "l'application BarCode Scanner doit être installée", Toast.LENGTH_SHORT).show();
}
```
* Dans lm mainActivity, il y a aussi la méthode _onActivityResult_
```java
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
        case Util.REQUEST_PHOTO_CODE: {
            if (resultCode == RESULT_OK) {
                this.photo = (Bitmap) data.getExtras().get("data");
                ImageView photo_iv = findViewById(R.id.picture_iv);
                photo_iv.setImageBitmap(this.photo);
            }
        }
        break;
        case Util.REQUEST_SCANNER: {
            if (resultCode == RESULT_OK) {
                this.messageScanner= (String) data.getExtras().get("SCAN_RESULT");
                TextView scan_tv = (TextView)findViewById(R.id.scan_tv);
                scan_tv.setText(this.messageScanner);
            }
        }
        break;
    }
}
```
* Je dois donc installer [Barcode Scanner by Zxing sur google Play](https://play.google.com/store/apps/details?id=com.google.zxing.client.android):

## ce que j'en fais:
* une fois le QrCode lu dans le onActivityResult
* Je cherche l'indice dans le spinner de départ, indice correspondant au numéro de salle retourné pas le scanner
* une fois cet indice retourné je mets le spinner de départ à la position trouvée
* je mets à jour le spinner d'arrivée des salles en exculant la salle trouvée par le scanner

# récupération du projet Firebase

* Pour rappel, [ma console Firebase](https://console.firebase.google.com/u/0/project/geolocalisation-indoor/database/geolocalisation-indoor/data)

* il a fallu rendre public la Override Methode (par défaut protected)
```java
@Override
    public void onPostExecute(JSONObject jsonObject) {

        delegate.processFinish(jsonObject);
    }
```
* Pour qu'elle soit bien lancée...

* TODO mettre le fireBAseDAO en attribut !! afin de pouvoir l'appeler pour insérer ...

# passage du calcul d'optimisation en Service:
 
* j'ai trouvé [Cet exemple sur Openclassrooms](https://openclassrooms.com/fr/courses/2023346-creez-des-applications-pour-android/2027704-les-services#/id/r-2033531)
  * peut être trop compliqué ?
  * je suis arrivé à [ici](https://openclassrooms.com/fr/courses/2023346-creez-des-applications-pour-android/2027704-les-services#/id/r-2033609)
  * faire une nouvelle application avec [le code suivant](https://openclassrooms.com/fr/courses/2023346-creez-des-applications-pour-android/2027704-les-services#/id/r-2033602)
  
## SallesActivity.java / Lancer calcul

* Il faut remplacer cette fonction par un appel à Service cf. [service Simple](https://openclassrooms.com/fr/courses/2023346-creez-des-applications-pour-android/2027704-les-services#/id/r-2033529)
  * le OnHandleIntent s'exécute en arrière plan !!!
* Le service lance une notification une fois le parcours prêt cf [cours OpenClassRomm](https://openclassrooms.com/fr/courses/2023346-creez-des-applications-pour-android/2027704-les-services#/id/r-2033587)
  * Il utilise des méthodes deprecated donc on prèfère [la réponse 84 à ce post StackOverflow](https://stackoverflow.com/questions/32345768/cannot-resolve-method-setlatesteventinfo)
* le 15/052020 à 18:35 tout marche sauf que l'on ne voit pas de petit icon sur la barre de notification

# prise en compte des travaux, de Vigipirate et de sens de circulatioin (COVID)

* Sur Firebase, on a ajouté *acccessible* 
  * aux salles (pas en travaux)
  * aux éléments de parcours (travaux, de Vigipirate et de sens de circulatioin (COVID))
  
## réperccuter sur la base de données:

### partie structure de la BDD 

* on a [les bonnes pratiques de la mise à jour BDD](https://thebhwgroup.com/blog/how-android-sqlite-onupgrade)
* c'est plutôt *private static final int DATABASE_VERSION = 2;* utilisé au contructeur de notre base LocalisationDatabase
  * ne sert à rien : on passe l'application en version 2 c'est dans le defaultconfig de *app/build.gradle* l'entrée *versionCode*
* On récupère la base de données en tapant *adb shell* et l'on va vers
  * */data/data/fr.cnam.nfa024.jpmena.geolocalisationindoor/databases*
```bash
root@generic_x86:/data/data/fr.cnam.nfa024.jpmena.geolocalisationindoor/databases # ll
-rw-rw---- u0_a60   u0_a60      20480 2020-05-16 12:57 GeolocalisationCNAM.db
-rw------- u0_a60   u0_a60       8720 2020-05-16 12:57 GeolocalisationCNAM.db-journal
```
* je récupère en local cette base pouur vérifier que la colonnne a été ajoutée
```bash
jpmena@jpmena-P34:~/AndroidStudioProjects/GeolocalisationIndoor$ adb pull /data/data/fr.cnam.nfa024.jpmena.geolocalisationindoor/databases/GeolocalisationCNAM.db .
/data/data/fr.cnam.nfa024.jpmena.geolocalisationindoor/databases/GeolocalisationCNAM.db: 1 file pulled. 3.6 MB/s (20480 bytes in 0.005s)
```

# passage de du choix du parcours à sa visualisation

## le service de détermination du parcours

* SallesActivity démarre le service ParcoursOptimalService choisi pour implémenter une méthode de traitement en arrière plan.
* Une fois le calcul du parcours optimal obtenu, ce dernier lance une notification

### Les notifications

* je me suis appuyé sur  [la réponse 84 a cette question StackOverflow](https://stackoverflow.com/questions/32345768/cannot-resolve-method-setlatesteventinfo)
* Or à partir de l'API 26 (Oreo) toute notification doit être associée à un canal channel cela a été [l'objet de la réponse à ma question StackOverflow](https://stackoverflow.com/questions/61931503/why-a-pending-intent-does-not-start-an-activity-when-called-form-a-services-not/61933204?noredirect=1#comment109544710_61933204)
* cela est expliqué également sur [cette vidéo des android developpers](https://developer.android.com/training/notify-user/channels)
* à la notification est asssocié un pending intent
* quand on ouvre la barre de notification et que l'on clique sur le large icon, le intent du pending intent est lancé
  * il démarre la ViewCourseActivity
  * en transportant le parccoursOptimisé (bundle putExtra)

## La ViewCourse Activity

* elle doit affficcher le parcours calculé étape par étape.
* Chaque étape étant le fiat de se rendre d'une pièce à une pièce voisine
  * une étape peut être simple: une direction à suivre jusqu'à tomber sur la salle en question
  * une étape peut être complexe: quand les salles adjaccentes sont sur 2 niveauxx différents, direction à suivre pour se rendre sur la cage d'escalier, 
  monter/descendre (on démarre alors un podomètre) puis on se rend de la cage d'escalier à une salle voisine
  
# supprimer les accès au second étage par l'ouest
* aller sur la [console Firebase avec mon compte gmail](https://console.firebase.google.com/u/0/project/geolocalisation-indoor/database/geolocalisation-indoor/data)
* rendre à false l'entrée cnamaccess31 mouvement entrée 12 (monter de 1 à 5) mis à false ainsi que l'entrée 14 (monter de 2 à 6 ) mis à false
