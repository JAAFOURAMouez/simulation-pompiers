package tests;
import simulateur.*;
import robot.*;
import simulateur.LectureDonnee;
import simulateur.Simulateur;

import java.io.FileNotFoundException;

import carte.Incendie;
import robot.Deplacement;
public class TestSimulateur {
    public static void main(String[] args) {
        try {
            DonneeSimulation donnes = LectureDonnee.lire("maps/hell.map");
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
