package robot;
import carte.Case;
import carte.NatureTerrain;
/**
 * Classe représentant un robot de type drone.
 */
public class Drone extends Robot {
    /**
     * Constructeur du drone.
     * Initialise un drone avec une position de départ, une capacité maximale du réservoir de 10,000 litres,
     * et une vitesse par défaut de 100 km/h.
     * 
     * @param position La case où se trouve initialement le drone.
     */
    public Drone (Case position){
        super(position,10000,100);
    }
    /**
     * Méthode pour vérifier si le drone peut se déplacer sur un type de terrain donné.
     * Le drone peut se déplacer sur tous types de terrains, donc renvoie toujours true.
     * 
     * @param terrain Le type de terrain à vérifier.
     * @return true car le drone peut voler au-dessus de tout terrain.
     */
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain){
        return true;
    }
    /**
     * Méthode pour définir la vitesse du drone.
     * La vitesse ne peut pas dépasser 150 km/h, sinon une exception est lancée.
     * 
     * @param vitesse La vitesse en km/h.
     */
    @Override
    public void setVitesse(double vitesse){
        if (vitesse>150) throw new IllegalArgumentException("la vitesse du drone est superieure a 150km");
        else this.vitesse=vitesse;
    }
    /**
     * Méthode pour définir la vitesse du drone en fonction du terrain.
     * Ici, le drone n'est pas affecté par le type de terrain, donc il conserve sa vitesse de base.
     * 
     * @param terrain Le type de terrain (pas d'impact pour le drone).
     */
    @Override
    public void setVitesseSur(NatureTerrain terrain){
        vitesse= vitesseBase;
    }
    /**
     * Méthode pour définir la vitesse de base du drone.
     * La vitesse de base ne peut pas dépasser 150 km/h.
     * 
     * @param vitesse La vitesse de base en km/h.
     */
    public void setVitesseBase(double vitesse){
        if (vitesse>150) throw new IllegalArgumentException("la vitesse du drone est superieure a 150km");
        else {this.vitesseBase=vitesse;
            this.vitesse=vitesse;
        }
    }
    /**
     * Méthode pour obtenir la capacité maximale du réservoir d'eau du drone.
     * 
     * @return La capacité maximale de 10,000 litres.
     */
    @Override
    public int getCapaciteMaxReservoir(){
        return 10000;
    }
    /**
     * Méthode pour calculer le temps total d'intervention en fonction du volume d'eau utilisé.
     * 
     * @param vol Le volume d'eau en litres utilisé pour éteindre un incendie.
     * @return Le temps total nécessaire, calculé comme vol * 0.18 (seconde par litre).
     */
    @Override
    public double getTempsRemplissage(int vol)
    {
        return vol * 0.18;
    }
    /**
     * Méthode pour obtenir le type de robot.
     * 
     * @return "Drone" pour indiquer qu'il s'agit d'un drone.
     */
    @Override
    public String getType(){
        return "Drone";
    }
}
