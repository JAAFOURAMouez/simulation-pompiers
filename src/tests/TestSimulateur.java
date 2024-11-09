package tests;
import simulateur.*;
import robot.*;
import carte.*;
import simulateur.LectureDonnee;
import simulateur.Simulateur;

import java.io.FileNotFoundException;

public class TestSimulateur {
    public static void main(String[] args) {
        try {
            DonneeSimulation donnes = LectureDonnee.lire("maps/hell.map");
            Simulateur simulateur = new Simulateur(donnes);

            // Choisissez un robot et définissez les cases de départ et de destination
            Robot robot1 = donnes.getRobots().get(1);
            robot1.setCarte(donnes.getCarte());
            robot1.setSimulateur(simulateur);
            //Case depart = robot1.getPosition(); // Exemple de case de départ
            Case destination = donnes.getCarte().getCase(donnes.geIncendies().get(0).getPosition().getLigne(), donnes.geIncendies().get(0).getPosition().getColonne()); // Exemple de case de destination
            // Afficher le niveau du réservoir d'eau du robot 
            System.out.println(robot1.deplacerVersCase(destination));

        } catch (FileNotFoundException e) {
            System.err.println("Fichier de carte non trouvé !");
            e.printStackTrace();
        } 
    }
}
