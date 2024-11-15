package robot;
import carte.Case;
import carte.NatureTerrain;

// Classe représentant un robot à roues, qui hérite de la classe abstraite Robot
public class RobotARoues extends Robot {

    // Constructeur du robot à roues. Le réservoir d'eau est de 5000 et la vitesse de base est de 80 km/h
    public RobotARoues(Case position) {
        super(position, 5000, 80); // Le robot a un réservoir d'eau de 5000 unités et une vitesse de base de 80 km/h
    }

    // Méthode qui détermine si le robot peut se déplacer sur un terrain donné
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain) {
        // Le robot ne peut se déplacer que sur des terrains libres (Terrain libre ou Habitat)
        return terrain == NatureTerrain.TERRAIN_LIBRE || terrain == NatureTerrain.HABITAT;
    }

    // Méthode qui ajuste la vitesse du robot en fonction du terrain sur lequel il se trouve
    @Override
    public void setVitesseSur(NatureTerrain terrain) {
        if (terrain == null) {
            vitesse = 0; // Si le terrain est nul, le robot ne peut pas se déplacer
        } else {
            // Utilisation d'un switch pour ajuster la vitesse en fonction du type de terrain
            vitesse = switch (terrain) {
                case TERRAIN_LIBRE -> vitesseBase; // Sur terrain libre, la vitesse est la vitesse de base
                case HABITAT -> vitesseBase / 2;  // Sur habitat, la vitesse est réduite de moitié
                default -> 0; // Pour tous les autres types de terrain (non définis ici), la vitesse est 0
            };
        }
    }

    // Méthode pour définir la vitesse du robot
    @Override
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse; // La vitesse est directement définie par l'utilisateur
    }

    // Méthode pour définir la vitesse de base du robot. Elle met également à jour la vitesse actuelle.
    public void setVitesseBase(double vitesse) {
        this.vitesse = vitesse;  // La vitesse actuelle est définie
        this.vitesseBase = vitesse; // La vitesse de base est mise à jour
    }

    // Méthode pour obtenir la capacité maximale du réservoir d'eau du robot
    @Override
    public int getCapaciteMaxReservoir() {
        return 5000; // La capacité du réservoir est de 5000 unités
    }

    // Méthode qui retourne le temps nécessaire pour remplir le réservoir. Le temps est calculé en fonction du volume à remplir.
    @Override
    public double getTempsRemplissage(int vol) {
        return vol * 0.12; // Le temps pour remplir le réservoir est de 0.12 secondes par unité de volume
    }

    // Méthode qui retourne le type du robot sous forme de chaîne de caractères
    @Override
    public String getType() {
        return "RobotARoues"; // Retourne le nom du type de robot
    }
}
