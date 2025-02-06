# **Simulation d’une Équipe de Robots Pompiers**  

Ce projet est une simulation en Java d'une équipe de robots pompiers autonomes conçus pour éteindre des incendies dans un environnement naturel. L'objectif est d'optimiser les interventions des robots en utilisant des algorithmes de plus court chemin (Dijkstra) et une stratégie de coordination efficace pour minimiser le temps d'intervention.

## 🚀 **Fonctionnalités**  
✅ Simulation d'un environnement naturel avec différents types de terrains (forêt, eau, roche).  
✅ Gestion de plusieurs types de robots (drones, robots à roues, etc.) avec des capacités spécifiques.  
✅ Coordination des robots par un chef pompier pour éteindre les incendies de manière optimisée.  
✅ Interface graphique pour visualiser la simulation en temps réel.  
✅ Planification des événements (déplacement, remplissage, intervention) via un modèle à événements discrets.  

## 🛠️ **Technologies utilisées**  
- **Langage** : Java  
- **Algorithmes** : Dijkstra pour le calcul des plus courts chemins.  
- **Interface graphique** : Affichage en temps réel de la carte, des robots et des incendies.  
- **Gestion des événements** : Modèle à événements discrets pour planifier les actions des robots.  

## 📁 **Structure du projet**

```bash
Robots_Pompiers/
├── src/         # Code source Java
├── gui/         # Classes pour l'interface graphique
├── tests/       # Tests unitaires et de validation
├── ressources/  # Images et ressources graphiques
├── maps/        # Fichiers de cartes pour la simulation
└── README.md    # Documentation du projet
```
## 🔧 **Installation et exécution**
### 1️⃣ Prérequis
- Java Development Kit (JDK) : Version 18.0.2 ou supérieure.

- IDE recommandé : IntelliJ, VS Code, ou tout autre IDE supportant Java.

### 2️⃣ Compilation et exécution
- Clonez ou décompressez le dépôt du projet.

-Compilez le projet :
```bash
  make
```

Lancez la simulation avec une carte spécifique :

``` bash
  make run MAP=nom-du-map.map
```
Exemples de cartes disponibles : carteSujet.map, desert.map, mushroom.map, spiral.map.

## 📝 Utilisation
Simulateur : Affiche la carte avec les robots, les incendies et les différents types de terrains. Les robots sont coordonnés par le chef pompier pour éteindre les incendies de manière optimale.

Gestion des robots : Ajoutez et contrôlez différents types de robots avec des capacités spécifiques.

Gestion de la carte : Modifiez les cartes pour tester différents scénarios (ajout de feux, points d'eau, etc.).

## 📌 Améliorations possibles
Stratégies collaboratives : Implémenter des stratégies où les robots collaborent pour éteindre des incendies de grande intensité.

Propagation des incendies : Modéliser une propagation réaliste des incendies en fonction du terrain et du temps.

Notifications : Ajouter des notifications pour informer l'utilisateur des événements critiques (incendies éteints, robots en panne, etc.).

👥 Auteurs
Mehdi El Idrissi El Fatmi

Moez Jaafoura

Mohammed Amine Hannoun
