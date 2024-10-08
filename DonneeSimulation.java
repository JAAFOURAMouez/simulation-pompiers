import java.security.PublicKey;
import java.util.List;

public class DonneeSimulation {
    private Carte carte;
    private List<Robot> robots;
    private List<Incendie> incendies;
    public DonneeSimulation(Carte carte,List<Incendie> incendies,
                            List<Robot>robots){
        this.carte=carte;
        this.incendies=incendies;
        this.robots=robots;
    }
    public void ajoutIncendie(Incendie incendie){
        this.incendies.add(incendie);
    }
    public void ajoutRobot(Robot robot){
        this.robots.add(robot); 
    }
    public List<Incendie> geIncendies(){
        return incendies;
    }
    public List<Robot> getRobots(){
        return robots;
    }
    public Carte getCarte(){
        return carte;
    }
    // Méthode pour obtenir un incendie à une position donnée
    public Incendie getIncendie(Case position){
        for(Incendie incendie:incendies){
            if (incendie.getPosition().equals(position)) {
                return incendie;
            }
        }
        return null;
    }
    // Méthode pour obtenir un robot à une position donnée
    public Robot getRobot(Case position){
        for (Robot robot:robots){
            if (robot.getPosition().equals(position)) {
                return robot;
            }
        }
        return null;
    }
    public void eteindreTousLesIncendies(){
        for(Incendie incendie:incendies)
            incendie.eteindre(incendie.getIntensite());
    }
    public void deplacerRobot(Robot robot,Direction direction){
        robot.deplacer(direction,carte);
    }
    public void robotEteintIncendie(Robot robot,Incendie incendie){
        int quantiteEau=robot.getNiveauReservoirEau();
        incendie.eteindre(quantiteEau);
        robot.deverserEau(quantiteEau);
    }
}
