package robot;
import carte.Case;
import carte.NatureTerrain;

// Classe représentant un robot à pattes, qui hérite de la classe abstraite Robot
public class RobotAPattes extends Robot {

    // Constructeur du robot à pattes. Le réservoir d'eau est infini (Integer.MAX_VALUE) et la vitesse de base est de 30 km/h
    public RobotAPattes(Case position) {
        super(position, Integer.MAX_VALUE, 30); // Le robot a un réservoir d'eau de capacité infinie
    }

    // Méthode qui ajuste la vitesse du robot en fonction du terrain sur lequel il se trouve.
    @Override
    public void setVitesseSur(NatureTerrain terrain) {
        // Utilisation de switch pour définir la vitesse en fonction du terrain
        vitesse = switch (terrain) {
            case EAU -> 0; // Si le terrain est de l'eau, la vitesse du robot est 0
            case ROCHE -> 10; // Si le terrain est de la roche, la vitesse est de 10 km/h
            default -> vitesseBase; // Sinon, on garde la vitesse de base
        };
    }

    // Méthode qui détermine si le robot peut se déplacer sur un terrain donné.
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain) {
        return terrain != NatureTerrain.EAU; // Le robot ne peut pas se déplacer sur de l'eau
    }

    // Méthode pour définir la vitesse du robot. Dans ce cas, la vitesse est directement définie sans aucune restriction.
    @Override
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse; // La vitesse est définie par l'utilisateur sans validation
    }

    // Méthode pour définir la vitesse de base du robot. Elle met également à jour la vitesse actuelle.
    public void setVitesseBase(double vitesse) {
        this.vitesse = vitesse;  // La vitesse actuelle est définie
        this.vitesseBase = vitesse; // La vitesse de base est mise à jour
    }

    // Méthode pour obtenir la capacité maximale du réservoir d'eau du robot. Ici, il est infini.
    @Override
    public int getCapaciteMaxReservoir() {
        return Integer.MAX_VALUE; // La capacité du réservoir est infinie
    }

    // Méthode qui retourne le temps nécessaire pour remplir le réservoir. Comme le réservoir est infini, le temps est nul.
    @Override
    public double getTempsRemplissage(int vol) {
        return 0; // Pas de temps nécessaire pour remplir un réservoir infini
    }

    // Méthode qui retourne le type du robot sous forme de chaîne de caractères
    @Override
    public String getType() {
        return "RobotAPattes"; // Retourne le nom du type de robot
    }
}
