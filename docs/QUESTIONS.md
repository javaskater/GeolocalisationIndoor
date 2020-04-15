# Questions au 15/04/2020
## comment mettre la recherche dans un service
* Actuellement 2 Spinners nous donnent la salle de départ et la salle d'arrivée de mon parcours
* Et un bouton **Calculer Itinéraire** lance le calcul toujours dans le MainActivity
* Comment passer le calcul à un service qui tournerait en arrière plan ?
## comment passer de l'activité principale à une ou des activités qui affichent les résultats
* Doit on passer par des Intent 
  * avec pour cible l'activité qui affiche le résultat pas à pas ?
  * Cette activité doint n'afficher chaque fois qu'un seul mouvement
* il faudrait afficher chaque élément comme un élément d'un caroussel. 
  * Comment faire ?
## Les QR Codes:
* Quel lecteur de QRCode choisir ?
* Comment lire un QR Code qui me retourne le numéro de la salle ?
  * faut il créer un QRCOde qui contient le numéro de salle (générateur de QRCode ?)
  * faut il passer en pas à pas pour remplacer le QRCode lu par le numéro d'une salle ?