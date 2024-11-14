package tests;
import java.io.FileNotFoundException;
import robot.Robot;
import simulateur.*;
public class TestLecteurDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            LectureDonnee.lire(args[0]);
            DonneeSimulation donnees = LectureDonnee.lire(args[0]);
            
            // Display each robot with its speed
            for (Robot robot : donnees.getRobots()) {
                System.out.println("Robot type: " + robot.getType() + ", Speed: " + robot.getVitesse());
            }

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        }
    }
}
