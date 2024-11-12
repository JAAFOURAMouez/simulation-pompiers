package tests;
import java.io.FileNotFoundException;
import robot.*;
import simulateur.*;

public class TestSimulateur {
    public static void main(String[] args) {
        try {
            DonneeSimulation donnes = LectureDonnee.lire("maps/spiral.map");
            Simulateur simulateur = new Simulateur(donnes);
           
            //Robot robot1 = donnes.getRobots().get(1);
            //robot1.setCarte(donnes.getCarte());
            //robot1.setSimulateur(simulateur);
            //Case depart = robot1.getPosition(); // Exemple de case de départ
            //Case destination = donnes.getCarte().getCase(donnes.geIncendies().get(1).getPosition().getLigne(), donnes.geIncendies().get(1).getPosition().getColonne()); // Exemple de case de destination
            // Afficher le niveau du réservoir d'eau du robot 
            //System.out.println(robot1.deplacerVersCase(destination));
            Strategiez strat =new Strategiez();
            strat.chefPompier(donnes,simulateur);

        } catch (FileNotFoundException e) {
            System.err.println("Fichier de carte non trouvé !");
        } 
    }
}
