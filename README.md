# Projet NFA024 annee 2020

>Sujet 1 : Géolocalisation «in door»  
On se propose de créer une application de localisation/guidage « in door », pour une personne se déplaçant à pieds, c'est-
à-dire à dire à l'intérieur d'un bâtiment. La localisation « in door » est encore très difficile de nos jours avec des solutions
traditionnelles comme le GPS-A et la triangulation WiFi ou autre.  
L'exemple typique représentant l'objectif de
l'application et que celle-ci puisse guider un élève arrivant au CNAM vers sa salle de cours.  
Pour ce faire, on se propose d'utiliser des tags (par exemple des QRcode) et de permettre ainsi la localisation de la
personne par la lecture d'un de ces codes via l'appareil photo du smart-phone.  
L'avantage de cette technique est de
garantir une localisation à quelques centimètres du tag (distance pour prendre en photo le tag en question).  
L'application se chargera alors de calculer un itinéraire en fonction de cette localisation.  
Pour calculer son itinéraire, l'application pourra utiliser un graphe étiqueté par les directions possibles pour atteindre un
prochain QRcode (nœud du graphe). Ce graphe pourra être disponible localement, par exemple dans un fichier XML ou
une base de données gérée par l'application, de sorte que l'application n'ait pas besoin d'accéder au réseau.  
Évidemment,
les plus téméraires d'entre vous pourront concevoir également la mise en place d'un WebService pour gérer la
cartographie depuis un serveur (Mais seulement dans un second temps  
l'objectif de ce projet est de vous amener à
développer l'algorithmique nécessaire sur le téléphone lui-même et d'intégrer cela dans le modèle de composants
d'Android en tenant compte de leur cycle de vie).  
La conception du graphe est une étape importante. Une idée simple consiste à considérer des nœuds (identifiés pas de
QRCode) et des transitions qui ont un nœud de d'origine un nœud d'arrivée et une étiquette qui indique le chemin à suivre
à partir du nœud de départ pour atteindre le nœud d'arrivée.  
Les nœuds reliés par une transition sont considérés comme
des voisins. (Le graphe ne doit pas être une clique c'est-à-dire que tous les nœuds ne doivent pas être voisins de tous les
autres). Le graphe doit être connexe : tout nœud doit être accessible directement ou non à partir de n'importe quel nœud
du graphe, par au moins un itinéraire. Lors du calcul d'un itinéraire, on ne demande pas que le chemin proposé soit
optimum (question subsidiaire).  
L'application offrira une interface permettant à l'utilisateur de se positionner en fonction d'un tag, ou de calculer un
itinéraire en permettant la saisie d'une destination (La saisie de la destination doit être la plus simple possible).  
Le
prototype n'a pas pour but de fonctionner en grandeur réelle mais devra reposer sur quelques tag associés aux différents
nœuds du graphe et proposer un cheminement en fonction de la destination sur le graphe. On se basera sur un minimum
d'une douzaine de nœuds. On veillera cependant à prévoir un passage à l'échelle.  
L'application devra renseigner
l'utilisateur sur l'endroit ou il se trouve et indiquer l'endroit ou il se rend, ainsi que le cheminement qu'il doit suivre.  
Une
fonctionnalité doit pouvoir lui permettre de parcourir le chemin proposé et des tags intermédiaires lui seront indiqué
durant son parcours. (il pourra ou non les flasher pour confirmer le suivi correct de l'itinéraire et proposer alors une mise à
jour de l'itinéraire en cas d'erreur).