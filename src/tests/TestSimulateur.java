package tests;
import java.io.FileNotFoundException;
import robot.*;
import simulateur.*;

public class TestSimulateur {
    public static void main(String[] args) {
        try {
            // Vérifier si un argument (nom du fichier de la carte) a été fourni
            if (args.length == 0) {
                System.err.println("Veuillez fournir le nom du fichier de la carte en argument.");
                return;
            }

            // Utiliser le premier argument comme nom du fichier de la carte
            String nomFichierCarte = args[0];
            DonneeSimulation donnes = LectureDonnee.lire(nomFichierCarte);
            Simulateur simulateur = new Simulateur(donnes);

            // Utilisation de la stratégie
            Strategiez strat = new Strategiez();
            strat.chefPompier(donnes, simulateur);

        } catch (FileNotFoundException e) {
            System.err.println("Fichier de carte '" + args[0] + "' non trouvé !");
        } catch (Exception e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
        }
    }
}

