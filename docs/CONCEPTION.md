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