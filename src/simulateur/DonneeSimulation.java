package simulateur;
import carte.*;
import java.util.List;
import robot.*;

/**
 * La classe DonneeSimulation représente les données nécessaires pour une simulation.
 * Elle contient une carte, une liste de robots, une liste d'incendies, et une liste des cases contenant de l'eau.
 */
public class DonneeSimulation {
    private final Carte carte;
    private final List<Robot> robots;
    private final List<Incendie> incendies;
    private final List<Case> casesEau;

    /**
     * Constructeur pour initialiser les données de la simulation.
     *
     * @param carte     La carte utilisée pour la simulation.
     * @param incendies La liste des incendies présents sur la carte.
     * @param robots    La liste des robots disponibles pour la simulation.
     * @param casesEau  La liste des cases contenant de l'eau.
     */
    public DonneeSimulation(Carte carte, List<Incendie> incendies, 
                            List<Robot> robots, List<Case> casesEau) {
        this.carte = carte;
        this.incendies = incendies;
        this.robots = robots;
        this.casesEau = casesEau;
    }

    /**
     * Ajoute un incendie à la liste des incendies.
     *
     * @param incendie L'incendie à ajouter.
     */
    public void ajoutIncendie(Incendie incendie) {
        this.incendies.add(incendie);
    }

    /**
     * Ajoute un robot à la liste des robots.
     *
     * @param robot Le robot à ajouter.
     */
    public void ajoutRobot(Robot robot) {
        this.robots.add(robot); 
    }

    /**
     * Retourne la liste des incendies.
     *
     * @return La liste des incendies.
     */
    public List<Incendie> getIncendies() {
        return incendies;
    }

    /**
     * Retourne la liste des robots.
     *
     * @return La liste des robots.
     */
    public List<Robot> getRobots() {
        return robots;
    }

    /**
     * Retourne la liste des cases contenant de l'eau.
     *
     * @return La liste des cases d'eau.
     */
    public List<Case> getCasesEau() {
        return casesEau;
    }

    /**
     * Retourne la carte de la simulation.
     *
     * @return La carte.
     */
    public Carte getCarte() {
        return carte;
    }

    /**
     * Obtient un incendie à une position donnée sur la carte.
     *
     * @param position La case où chercher l'incendie.
     * @return L'incendie à la position donnée, ou null s'il n'y en a pas.
     */
    public Incendie getIncendie(Case position) {
        for (Incendie incendie : incendies) {
            if (incendie.getPosition().equals(position)) {
                return incendie;
            }
        }
        return null;
    }

    /**
     * Obtient un robot à une position donnée sur la carte.
     *
     * @param position La case où chercher le robot.
     * @return Le robot à la position donnée, ou null s'il n'y en a pas.
     */
    public Robot getRobot(Case position) {
        for (Robot robot : robots) {
            if (robot.getPosition().equals(position)) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Déplace un robot dans une direction donnée, si le mouvement est valide.
     *
     * @param robot     Le robot à déplacer.
     * @param direction La direction du déplacement.
     */
    public void deplacerRobot(Robot robot, Direction direction) {
        if (robot != null && direction != null) {
            robot.deplacer(direction, carte);
        }
    }

    /**
     * Permet à un robot d'éteindre un incendie en utilisant l'eau de son réservoir.
     * La quantité d'eau utilisée est équivalente à la quantité d'eau présente dans le réservoir du robot.
     *
     * @param robot    Le robot utilisé pour éteindre l'incendie.
     * @param incendie L'incendie à éteindre.
     */
    public void robotEteintIncendie(Robot robot, Incendie incendie) {
        int quantiteEau = robot.getNiveauReservoirEau();
        incendie.eteindre(quantiteEau);
        robot.deverserEau(quantiteEau);
    }
}
