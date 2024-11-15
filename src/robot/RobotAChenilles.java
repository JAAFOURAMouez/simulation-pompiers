package robot;

import carte.Case;
import carte.NatureTerrain;

/**
 * Classe représentant un robot à chenilles. Ce robot hérite de la classe abstraite {@link Robot}.
 * Le robot à chenilles a des capacités spécifiques en fonction des types de terrains sur lesquels il peut se déplacer.
 * 
 * <p>Le robot possède un réservoir d'eau, une vitesse de déplacement et peut être influencé par différents types de terrains.
 * Il est conçu pour se déplacer sur des terrains tels que la forêt, les habitats et les terrains dégagés, mais il ne peut pas traverser
 * des terrains comme les roches ou l'eau.</p>
 */
public class RobotAChenilles extends Robot {

    /**
     * Constructeur du robot à chenilles.
     * 
     * @param position La position initiale du robot sur la carte.
     * Le réservoir d'eau est initialisé à 2000 unités et la vitesse de base est de 60 km/h.
     */
    public RobotAChenilles(Case position) {
        super(position, 2000, 60); // Initialisation avec un réservoir de 2000 et une vitesse de 60 km/h
    }

    /**
     * Détermine si le robot peut se déplacer sur un terrain donné.
     * 
     * <p>Le robot à chenilles ne peut pas se déplacer sur des terrains rocheux ou aquatiques.</p>
     * 
     * @param terrain Le terrain sur lequel on vérifie la possibilité de déplacement.
     * @return true si le robot peut se déplacer sur le terrain, false sinon.
     */
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain) {
        return !(terrain == NatureTerrain.ROCHE || terrain == NatureTerrain.EAU); // Le robot ne peut pas se déplacer sur les terrains rocheux ou aquatiques
    }

    /**
     * Ajuste la vitesse du robot en fonction du terrain sur lequel il se trouve.
     * 
     * <p>Si le robot se trouve sur un terrain forestier, sa vitesse est réduite de moitié.
     * Il ne peut pas se déplacer sur des terrains comme l'eau ou la roche. Sur les autres terrains, la vitesse reste inchangée.</p>
     * 
     * @param terrain Le terrain sur lequel le robot se trouve.
     */
    @Override
    public void setVitesseSur(NatureTerrain terrain) {
        if (terrain == NatureTerrain.FORET) {
            vitesse = vitesseBase / 2; // Réduction de la vitesse de moitié dans une forêt
        } else if (terrain != NatureTerrain.HABITAT && terrain != NatureTerrain.TERRAIN_LIBRE) {
            vitesse = 0; // Le robot ne peut pas se déplacer sur d'autres terrains
        } else {
            vitesse = vitesseBase; // Maintien de la vitesse de base pour les autres terrains
        }
    }

    /**
     * Définit la vitesse actuelle du robot.
     * 
     * <p>La vitesse ne peut pas dépasser 80 km/h. Si la vitesse donnée dépasse cette limite, une exception est lancée.</p>
     * 
     * @param vitesse La nouvelle vitesse du robot en km/h.
     * @throws IllegalArgumentException Si la vitesse dépasse 80 km/h.
     */
    @Override
    public void setVitesse(double vitesse) {
        if (vitesse > 80) {
            throw new IllegalArgumentException("La vitesse du robot est supérieure à 80 km/h"); // Exception si la vitesse est trop élevée
        } else {
            this.vitesse = vitesse; // Si la vitesse est valide, elle est définie
        }
    }

    /**
     * Définit la vitesse de base du robot.
     * 
     * <p>La vitesse de base ne peut pas dépasser 80 km/h. Si la vitesse dépasse cette limite, une exception est lancée.</p>
     * 
     * @param vitesse La nouvelle vitesse de base du robot en km/h.
     * @throws IllegalArgumentException Si la vitesse dépasse 80 km/h.
     */
    public void setVitesseBase(double vitesse) {
        if (vitesse > 80) {
            throw new IllegalArgumentException("La vitesse du robot est supérieure à 80 km/h"); // Vérifie si la vitesse dépasse la limite
        } else {
            this.vitesse = vitesse; // Si la vitesse est valide, on la définit
            this.vitesseBase = vitesse; // Mise à jour de la vitesse de base
        }
    }

    /**
     * Récupère la capacité maximale du réservoir d'eau du robot.
     * 
     * @return La capacité maximale du réservoir, qui est de 2000 unités d'eau.
     */
    @Override
    public int getCapaciteMaxReservoir() {
        return 2000; // La capacité maximale du réservoir est de 2000
    }

    /**
     * Calcule le temps nécessaire pour remplir le réservoir avec un volume donné.
     * 
     * <p>Le temps nécessaire pour remplir le réservoir est de 0.15 heures (soit 9 minutes) par unité de volume.</p>
     * 
     * @param vol Le volume d'eau à ajouter au réservoir.
     * @return Le temps nécessaire pour remplir le réservoir en heures.
     */
    @Override
    public double getTempsRemplissage(int vol) {
        return vol * 0.15; // Le temps de remplissage est de 0.15 heures par unité de volume
    }

    /**
     * Récupère le type du robot sous forme de chaîne de caractères.
     * 
     * @return Le type de robot, qui est "RobotAChenilles" pour cette classe.
     */
    @Override
    public String getType() {
        return "RobotAChenilles"; // Retourne le type spécifique du robot
    }
}
