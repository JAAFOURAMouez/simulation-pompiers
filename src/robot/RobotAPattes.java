package robot;

import carte.Case;
import carte.NatureTerrain;

/**
 * Classe représentant un robot à pattes, qui hérite de la classe abstraite {@link Robot}.
 * Ce robot peut se déplacer sur différents types de terrains avec des vitesses spécifiques et un réservoir d'eau infini.
 * 
 * <p>Le robot à pattes a une capacité de réservoir infinie, ce qui lui permet de se concentrer sur la vitesse de déplacement 
 * en fonction des terrains. La vitesse du robot varie en fonction du type de terrain rencontré.</p>
 */
public class RobotAPattes extends Robot {

    /**
     * Constructeur du robot à pattes. Ce robot a un réservoir d'eau de capacité infinie et une vitesse de base de 30 km/h.
     * 
     * @param position La position initiale du robot sur la carte.
     */
    public RobotAPattes(Case position) {
        super(position, Integer.MAX_VALUE, 30); // Le robot a un réservoir d'eau de capacité infinie et une vitesse de base de 30 km/h
    }

    /**
     * Ajuste la vitesse du robot en fonction du terrain sur lequel il se trouve.
     * 
     * <p>La vitesse du robot varie selon le terrain :</p>
     * <ul>
     *   <li>Sur l'eau, la vitesse est de 0 km/h (impossible de se déplacer).</li>
     *   <li>Sur la roche, la vitesse est réduite à 10 km/h.</li>
     *   <li>Sur tous les autres terrains (forêt, habitat, terrain libre), la vitesse est la vitesse de base du robot (30 km/h).</li>
     * </ul>
     * 
     * @param terrain Le terrain sur lequel le robot se trouve.
     */
    @Override
    public void setVitesseSur(NatureTerrain terrain) {
        // Utilisation de switch pour ajuster la vitesse en fonction du terrain
        vitesse = switch (terrain) {
            case EAU -> 0;       // Si le terrain est de l'eau, la vitesse est 0 km/h
            case ROCHE -> 10;    // Si le terrain est de la roche, la vitesse est 10 km/h
            default -> vitesseBase; // Sinon, la vitesse est la vitesse de base
        };
    }

    /**
     * Détermine si le robot peut se déplacer sur un terrain donné.
     * 
     * <p>Le robot ne peut se déplacer que sur des terrains autres que l'eau.</p>
     * 
     * @param terrain Le terrain sur lequel on vérifie si le robot peut se déplacer.
     * @return true si le robot peut se déplacer sur ce terrain, false sinon.
     */
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain) {
        return terrain != NatureTerrain.EAU; // Le robot ne peut pas se déplacer sur de l'eau
    }

    /**
     * Définit la vitesse du robot.
     * 
     * <p>Dans le cas du robot à pattes, la vitesse est définie directement par l'utilisateur sans restriction.</p>
     * 
     * @param vitesse La nouvelle vitesse du robot en km/h.
     */
    @Override
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse; // La vitesse est définie par l'utilisateur sans validation
    }

    /**
     * Définit la vitesse de base du robot et met à jour la vitesse actuelle.
     * 
     * @param vitesse La nouvelle vitesse de base du robot en km/h.
     */
    public void setVitesseBase(double vitesse) {
        this.vitesse = vitesse;    // La vitesse actuelle est définie
        this.vitesseBase = vitesse; // La vitesse de base est mise à jour
    }

    /**
     * Récupère la capacité maximale du réservoir d'eau du robot.
     * 
     * <p>Étant donné que le robot à pattes a un réservoir infini, cette méthode retourne une valeur infinie.</p>
     * 
     * @return La capacité du réservoir, qui est infinie (Integer.MAX_VALUE).
     */
    @Override
    public int getCapaciteMaxReservoir() {
        return Integer.MAX_VALUE; // La capacité du réservoir est infinie
    }

    /**
     * Récupère le temps nécessaire pour remplir le réservoir du robot.
     * 
     * <p>Étant donné que le réservoir du robot à pattes est infini, il n'y a pas de temps de remplissage.</p>
     * 
     * @param vol Le volume d'eau à ajouter au réservoir.
     * @return Le temps nécessaire pour remplir le réservoir, qui est nul (0).
     */
    @Override
    public double getTempsRemplissage(int vol) {
        return 0; // Pas de temps nécessaire pour remplir un réservoir infini
    }

    /**
     * Récupère le type du robot sous forme de chaîne de caractères.
     * 
     * @return Le type de robot, qui est "RobotAPattes" pour cette classe.
     */
    @Override
    public String getType() {
        return "RobotAPattes"; // Retourne le nom du type de robot
    }
}
