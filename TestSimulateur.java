import gui.*;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import javax.swing.text.Position;
import org.w3c.dom.html.HTMLElement;

public class TestSimulateur {
    public static void main(String[] args) {
        try {
            DonneeSimulation donnes = LectureDonnee.lire("spiral.map");
            Simulateur simulateur = new Simulateur(donnes);

            // Add movement events
            Robot robot1 = donnes.getRobots().get(0);
         
 
            Deplacement deplacerRobot1 = new Deplacement(donnes.getCarte(), robot1, Direction.NORD, 0);

            simulateur.ajouteEvenement(deplacerRobot1);



            Robot robot2 = donnes.getRobots().get(1);
            Deplacement deplacerRobot2 = new Deplacement(donnes.getCarte(), robot2, Direction.OUEST, 1);
            simulateur.ajouteEvenement(deplacerRobot2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
