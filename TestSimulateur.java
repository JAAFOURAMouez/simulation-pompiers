import gui.*;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import javax.swing.text.Position;
import org.w3c.dom.html.HTMLElement;

public class TestSimulateur {
    public static void main(String[] args) {
        try {
            DonneeSimulation donnes = LectureDonnee.lire("hell.map");
            Simulateur simulateur = new Simulateur(donnes);

            // Add movement events
            Robot robot1 = donnes.getRobots().get(0);
            System.out.println(robot1.getNiveauReservoirEau());
         
 
            Deplacement deplacerRobot1 = new Deplacement(donnes.getCarte(), robot1, Direction.SUD, 0);
            Incendie incendie1=donnes.geIncendies().get(0);
            simulateur.ajouteEvenement(deplacerRobot1);
            
            Intervention tfi=new Intervention(robot1, incendie1, 1);

            simulateur.ajouteEvenement(tfi);
            Deplacement deplacerRobot2 = new Deplacement(donnes.getCarte(), robot1, Direction.SUD, 2);
            simulateur.ajouteEvenement(deplacerRobot2);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
