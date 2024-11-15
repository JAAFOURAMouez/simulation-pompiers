package tests;

import java.io.FileNotFoundException;
import robot.*;
import simulateur.*;

/**
 * Test du simulateur avec le fichier de carte.
 */
public class TestSimulateur {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        // Vérification si un argument (nom du fichier de la carte) a été fourni
        if (args.length == 0) {
            System.err.println("Erreur : Veuillez fournir le nom du fichier de la carte en argument.");
            return;
        }

        // Utilisation du premier argument comme nom du fichier de la carte
        String nomFichierCarte = args[0];
        DonneeSimulation donnes;

        try {
            // Lecture des données depuis le fichier
            donnes = LectureDonnee.lire(nomFichierCarte);
            if (donnes == null) {
                System.err.println("Erreur : Les données de la carte n'ont pas pu être lues correctement.");
                return;
            }

            // Création du simulateur
            Simulateur simulateur = new Simulateur(donnes);

            // Utilisation de la stratégie
            Strategie strat = new Strategie();
            strat.chefPompier(donnes, simulateur);  // Assurez-vous que cette méthode existe et fonctionne correctement

        } catch (FileNotFoundException e) {
            System.err.println("Erreur : Le fichier de carte '" + nomFichierCarte + "' est introuvable.");
        } catch (Exception e) {
            // Gestion des autres exceptions
            System.err.println("Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
