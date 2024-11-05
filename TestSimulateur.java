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
         
 
            Deplacement deplacerRobot1 = new Deplacement(donnes.getCarte(), robot1, Direction.NORD, 0);

            simulateur.ajouteEvenement(deplacerRobot1);
            Incendie inc1 = donnes.geIncendies().get(0);
            int deb=inc1.getIntensite();
            System.err.println(deb);
            Robot robot2 = donnes.getRobots().get(1);
            robot2.getCapaciteMaxReservoir();
            
            Intervention tfi=new Intervention(robot2, inc1, 1);
            int after=inc1.getIntensite();
            System.err.println(after);

            simulateur.ajouteEvenement(tfi);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
