package robot;
import carte.*;


/**
 * Classe représentant un evenement de deplacement sur la carte.
 */
public class Deplacement extends Evenement
{
    // Cette classe gère le déplacement d'un robot dans une direction donnée
    protected Robot robot; // Le robot qui va se déplacer
    private final  Direction direction; // La direction dans laquelle le robot doit se déplacer
    private final Carte carte;

    /**
     * Constructeur pour initialiser un événement de déplacement.
     * 
     * @param carte     La carte sur laquelle le déplacement a lieu
     * @param robot     Le robot qui va être déplacé
     * @param direction La direction du déplacement (NORD, SUD, EST, OUEST)
     * @param date      La date de l'événement (hérité de la classe Evenement)
     */
    public Deplacement(Carte carte,Robot robot,Direction direction,long date){
        super(date);
        this.robot=robot;
        this.direction=direction;
        this.carte=carte;
    }

    /**
     * Méthode d'exécution qui effectue le déplacement du robot.
     * Cette méthode est appelée lorsqu'il est temps d'exécuter l'événement.
     */
    @Override
    public void execute(){
        robot.deplacer(direction, carte);
    }

    /**
     * Redéfinition de la méthode toString pour une description textuelle de l'événement.
     * 
     * @return Une chaîne de caractères décrivant l'événement de déplacement
     */
    @Override
    public String toString() {
        return "Deplacement Event [Robot: " + robot + ", Direction: " + direction + ", Date: " + getDate() + "]";
    }
}