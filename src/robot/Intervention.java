package robot;
import carte.Incendie;
/**
 * Classe représentant une intervention d'un robot pour éteindre un incendie.
 * Hérite de la classe abstraite Evenement.
 */
public class Intervention extends Evenement
{
    private final Robot robot;
    private  final Incendie incendie;

    /**
     * Constructeur de la classe Intervention.
     * 
     * @param robot    Le robot qui effectuera l'intervention.
     * @param incendie L'incendie que le robot doit éteindre.
     * @param date     La date (timestamp) à laquelle l'intervention est planifiée.
     */
    public Intervention(Robot robot,Incendie incendie,long date){
        super(date);
        this.robot=robot;
        this.incendie=incendie;
    }
    /**
     * Méthode qui exécute l'intervention pour éteindre l'incendie.
     * Le robot utilise de l'eau pour réduire l'intensité de l'incendie.
     * L'intensité de l'incendie est réduite par le minimum entre l'eau disponible dans le réservoir
     * du robot et l'intensité de l'incendie.
     */
    @Override
    public void execute(){
        int vol=incendie.getIntensite();
        int reservoirEau=robot.getNiveauReservoirEau();
        robot.deverserEau(vol);
        incendie.eteindre(Math.min(reservoirEau,vol));
    }
}