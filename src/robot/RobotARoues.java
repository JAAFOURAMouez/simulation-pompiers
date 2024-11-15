package robot;

import carte.Case;
import carte.NatureTerrain;

/**
 * Classe représentant un robot à roues, qui hérite de la classe abstraite {@link Robot}.
 * Ce robot se déplace à une vitesse maximale de 80 km/h et dispose d'un réservoir d'eau de 5000 unités.
 * 
 * <p>Le robot à roues peut se déplacer uniquement sur certains types de terrains, comme le terrain libre ou l'habitat. 
 * Il ajuste sa vitesse en fonction du type de terrain rencontré.</p>
 */
public class RobotARoues extends Robot {

    /**
     * Constructeur du robot à roues. Ce robot a un réservoir d'eau de 5000 unités et une vitesse de base de 80 km/h.
     * 
     * @param position La position initiale du robot sur la carte.
     */
    public RobotARoues(Case position) {
        super(position, 5000, 80); // Le robot a un réservoir d'eau de 5000 unités et une vitesse de base de 80 km/h
    }

    /**
     * Détermine si le robot peut se déplacer sur un terrain donné.
     * 
     * <p>Le robot à roues ne peut se déplacer que sur des terrains libres (Terrain libre ou Habitat).</p>
     * 
     * @param terrain Le terrain sur lequel on vérifie si le robot peut se déplacer.
     * @return true si le robot peut se déplacer sur ce terrain, false sinon.
     */
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain) {
        // Le robot ne peut se déplacer que sur le terrain libre ou l'habitat
        return terrain == NatureTerrain.TERRAIN_LIBRE || terrain == NatureTerrain.HABITAT;
    }

    /**
     * Ajuste la vitesse du robot en fonction du terrain sur lequel il se trouve.
     * 
     * <p>La vitesse du robot est ajustée selon le type de terrain :</p>
     * <ul>
     *   <li>Sur le terrain libre, la vitesse reste la vitesse de base (80 km/h).</li>
     *   <li>Sur l'habitat, la vitesse est réduite de moitié (40 km/h).</li>
     *   <li>Pour tous les autres terrains, la vitesse est définie à 0 (le robot ne peut pas se déplacer).</li>
     * </ul>
     * 
     * @param terrain Le terrain sur lequel le robot se trouve.
     */
    @Override
    public void setVitesseSur(NatureTerrain terrain) {
        if (terrain == null) {
            vitesse = 0; // Si le terrain est nul, la vitesse est de 0
        } else {
            // Ajuste la vitesse en fonction du type de terrain
            vitesse = switch (terrain) {
                case TERRAIN_LIBRE -> vitesseBase; // Sur terrain libre, la vitesse est la vitesse de base
                case HABITAT -> vitesseBase / 2;  // Sur habitat, la vitesse est réduite de moitié
                default -> 0; // Pour tous les autres terrains, la vitesse est de 0
            };
        }
    }

    /**
     * Définit la vitesse du robot.
     * 
     * <p>La vitesse est définie directement par l'utilisateur sans aucune restriction.</p>
     * 
     * @param vitesse La nouvelle vitesse du robot en km/h.
     */
    @Override
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse; // La vitesse est définie par l'utilisateur
    }

    /**
     * Définit la vitesse de base du robot. Elle met également à jour la vitesse actuelle.
     * 
     * @param vitesse La nouvelle vitesse de base du robot en km/h.
     */
    public void setVitesseBase(double vitesse) {
        this.vitesse = vitesse;  // La vitesse actuelle est définie
        this.vitesseBase = vitesse; // La vitesse de base est mise à jour
    }

    /**
     * Récupère la capacité maximale du réservoir d'eau du robot.
     * 
     * @return La capacité du réservoir, qui est de 5000 unités.
     */
    @Override
    public int getCapaciteMaxReservoir() {
        return 5000; // La capacité du réservoir est de 5000 unités
    }

    /**
     * Récupère le temps nécessaire pour remplir le réservoir du robot.
     * 
     * <p>Le temps nécessaire pour remplir le réservoir est calculé en fonction du volume à remplir. 
     * Chaque unité de volume prend 0.12 secondes pour être remplie.</p>
     * 
     * @param vol Le volume d'eau à ajouter au réservoir.
     * @return Le temps nécessaire pour remplir le réservoir, en secondes.
     */
    @Override
    public double getTempsRemplissage(int vol) {
        return vol * 0.12; // Le temps pour remplir le réservoir est de 0.12 secondes par unité de volume
    }

    /**
     * Récupère le type du robot sous forme de chaîne de caractères.
     * 
     * @return Le type de robot, qui est "RobotARoues" pour cette classe.
     */
    @Override
    public String getType() {
        return "RobotARoues"; // Retourne le nom du type de robot
    }
}
