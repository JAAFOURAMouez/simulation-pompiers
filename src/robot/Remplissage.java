package robot;

/**
 * Classe représentant un remplissage d'un robot pour éteindre un incendie.
 * Hérite de la classe abstraite Evenement.
 */
public class Remplissage extends Evenement
{
    private final Robot robot;  // Référence au robot dont le réservoir d'eau sera rempli
    private final int volume;      // Volume d'eau à remplir dans le réservoir du robot

    // Constructeur pour initialiser le robot, la date de l'événement et le volume d'eau
    Remplissage(Robot robot, long date, int volume){
        super(date);  // Appel du constructeur parent Evenement avec la date
        this.robot = robot;
        this.volume = volume;
    }

    /**
     * Exécute l'événement de remplissage du réservoir du robot.
     * Appelle la méthode `remplirEau` sur le robot pour ajouter l'eau au réservoir.
     */
    @Override
    public void execute(){
        robot.remplirEau(volume);  // Remplir le réservoir du robot avec le volume d'eau spécifié
    }
}
