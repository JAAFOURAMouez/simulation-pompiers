j ai cree un makefile
pour appeler javac ..... il suffit d appeler make
pour ecxecter le prog java ... il suffit d appeler make run.
il supprime tous les *.class et .jar situés en bin il faut juste appeler make clean
pour push dans le git il suffit d appeler make push
créer le fichier JAR il suffit d appeler make jar



Projet Carte et Gestion de Case
    Description du projet
        Ce projet implémente une structure de gestion de carte avec des cases ayant différentes natures de terrains. Le but est de modéliser une grille où chaque case représente un terrain spécifique, et les objets comme les robots peuvent interagir avec ces cases en fonction de leur nature.

Classes principales :
    Carte : Cette classe gère une grille de cases (Case[][]). Elle permet d'obtenir le nombre de lignes et de colonnes de la carte, ainsi que la taille des cases. Elle inclut également des méthodes pour vérifier si un voisin d'une case existe dans une certaine direction, et pour obtenir le voisin en fonction de la direction
    Case : Chaque objet de cette classe représente une case individuelle sur la carte. Une case est définie par ses coordonnées (ligne et colonne) et la nature du terrain (eau, forêt, roche, terrain libre, habitat).
    Direction (Enum) : Définit les quatre directions cardinales (Nord, Sud, Est, Ouest) que les robots ou objets peuvent utiliser pour se déplacer entre les cases.
    NatureTerrain (Enum) : Définit la nature de chaque case sur la carte, qui peut être eau, forêt, roche, terrain libre, ou habitat. Cette classification est importante pour déterminer les capacités des robots sur ces terrains.

Avancement actuel
    Carte : La classe est presque complète. Elle permet de créer une carte avec un nombre défini de lignes et colonnes, d’accéder à chaque case et de vérifier si des voisins existent dans une certaine direction.
    Case : La structure de la classe est définie avec la gestion des lignes, des colonnes, et de la nature du terrain.
    Enums : Les énumérations Direction et NatureTerrain sont bien définies, ce qui permet d’ajouter facilement des contraintes spécifiques en fonction du type de robot ou de l'interaction avec la nature du terrain.
    Robot : la classe et ses abstraites Drone, RobotAPattes ... sont presque completes
    LectureDonnees : est presque finie il reste a verifier si on lit vitesse robot(j ai fais qq uns mais reste les autres).
    
Prochaines étapes :
    Créer des tests unitaires pour vérifier le bon fonctionnement des méthodes de gestion des voisins et des interactions entre les cases.
    commencer l'interface graphique

Conclusion
    L’implémentation de base est bien avancée, mais il reste quelques fonctionnalités à ajouter pour gérer les interactions spécifiques des robots avec les différents terrains. N'hésitez pas à consulter le code et à me faire part de vos retours ou questions.

javac tests/TestSimulateur.java 
java tests.TestSimulateur