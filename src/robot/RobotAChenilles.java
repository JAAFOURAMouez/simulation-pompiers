package robot;
import carte.Case;
import carte.NatureTerrain;

// Classe représentant un robot à chenilles, qui hérite de la classe abstraite Robot
public class RobotAChenilles extends Robot {

    // Constructeur du robot à chenilles. Il initialise le robot avec une position et des valeurs par défaut.
    public RobotAChenilles(Case position) {
        super(position, 2000, 60); // Le réservoir d'eau est de 2000 unités et la vitesse de base est de 60 km/h
    }

    // Méthode qui détermine si le robot peut se déplacer sur un terrain donné.
    // Le robot ne peut pas se déplacer sur des terrains rocheux ou aquatiques.
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain) {
        return !(terrain == NatureTerrain.ROCHE || terrain == NatureTerrain.EAU); // Retourne false si le terrain est de type ROCHE ou EAU
    }

    // Méthode qui ajuste la vitesse du robot en fonction du terrain sur lequel il se trouve.
    @Override
    public void setVitesseSur(NatureTerrain terrain) {
        // Si le terrain est de type FORET, la vitesse du robot est réduite à moitié
        if (terrain == NatureTerrain.FORET) {
            vitesse = vitesseBase / 2; // Réduction de la vitesse à moitié dans une forêt
        }
        // Si le terrain est de type HABITAT ou TERRAIN_LIBRE, la vitesse reste inchangée
        else if (terrain != NatureTerrain.HABITAT && terrain != NatureTerrain.TERRAIN_LIBRE) {
            vitesse = 0; // Le robot ne peut pas se déplacer sur d'autres types de terrain
        }
        // Sinon, on garde la vitesse de base du robot
        else {
            vitesse = vitesseBase;
        }
    }

    // Méthode pour définir la vitesse du robot. Elle lance une exception si la vitesse dépasse 80 km/h.
    @Override
    public void setVitesse(double vitesse) {
        if (vitesse > 80) {
            throw new IllegalArgumentException("La vitesse du robot est supérieure à 80 km/h"); // Lancer une exception si la vitesse est trop élevée
        } else {
            this.vitesse = vitesse; // Sinon, on définit la vitesse
        }
    }

    // Méthode pour définir la vitesse de base du robot. Elle lance une exception si la vitesse dépasse 80 km/h.
    public void setVitesseBase(double vitesse) {
        if (vitesse > 80) {
            throw new IllegalArgumentException("La vitesse du robot est supérieure à 80 km/h"); // Vérifie si la vitesse est supérieure à la limite autorisée
        } else {
            this.vitesse = vitesse; // Si la vitesse est valide, on la définit
            this.vitesseBase = vitesse; // La vitesse de base est également mise à jour
        }
    }

    // Méthode pour obtenir la capacité maximale du réservoir d'eau du robot (2000 litres)
    @Override
    public int getCapaciteMaxReservoir() {
        return 2000; // La capacité du réservoir est de 2000
    }

    // Méthode pour calculer le temps nécessaire pour remplir le réservoir avec un volume donné
    @Override
    public double getTempsRemplissage(int vol) {
        return vol * 0.15; // Le temps nécessaire pour remplir le réservoir est 0.15 heures (9 minutes) par unité de volume
    }

    // Méthode qui retourne le type du robot sous forme de chaîne de caractères
    @Override
    public String getType() {
        return "RobotAChenilles"; // Retourne le nom du type de robot
    }
}
