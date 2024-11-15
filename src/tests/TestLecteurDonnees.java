package tests;

import carte.Carte;
import carte.Case;
import carte.Incendie;
import java.io.FileNotFoundException;
import robot.Robot;
import simulateur.*;

/**
 * Test de la lecture des données depuis un fichier de carte.
 */
public class TestLecteurDonnees {

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        // Vérification des arguments
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);  // Arrêt du programme si aucun fichier n'est spécifié
        }

        try {
            // Lecture des données depuis le fichier passé en argument
            String fichierCarte = args[0];
            DonneeSimulation donnees = LectureDonnee.lire(fichierCarte);  // Lecture des données

            // Test de la lecture des données
            System.out.println("Début de la lecture des données...");
            System.out.println("Données lues avec succès.");

            // Afficher les détails de la carte
            afficherDetailsCarte(donnees.getCarte());

            // Afficher les détails des robots
            afficherDetailsRobots(donnees);

            // Afficher les détails des incendies
            afficherDetailsIncendies(donnees);

        } catch (FileNotFoundException e) {
            // Gestion de l'erreur si le fichier n'est pas trouvé
            System.err.println("Erreur : le fichier '" + args[0] + "' est introuvable.");
        } catch (Exception e) {
            // Gestion d'autres erreurs inattendues
            System.err.println("Une erreur inattendue s'est produite : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Affiche les détails de la carte.
     *
     * @param carte La carte à afficher.
     */
    private static void afficherDetailsCarte(Carte carte) {
        System.out.println("\n=== Détails de la carte ===");
        System.out.println("Nombre de lignes : " + carte.getNbLignes());
        System.out.println("Nombre de colonnes : " + carte.getNbColonnes());
        System.out.println("Taille des cases : " + carte.getTailleCases());

        // Afficher les différents types de terrain
        for (int i = 0; i < carte.getNbLignes(); i++) {
            for (int j = 0; j < carte.getNbColonnes(); j++) {
                Case currentCase = carte.getCase(i, j);
                System.out.println("Case (" + i + ", " + j + ") - Terrain : " + currentCase.getNature());
            }
        }
    }

    /**
     * Affiche les détails des robots présents dans la simulation.
     *
     * @param donnees Les données de simulation contenant la liste des robots.
     */
    private static void afficherDetailsRobots(DonneeSimulation donnees) {
        System.out.println("\n=== Détails des robots ===");
        if (donnees.getRobots().isEmpty()) {
            System.out.println("Aucun robot trouvé.");
        } else {
            for (Robot robot : donnees.getRobots()) {
                if (robot.getCapaciteMaxReservoir()==Integer.MAX_VALUE){
                System.out.println("Position : (" + robot.getPosition().getLigne() + ", "
                        + robot.getPosition().getColonne() + "), Type : " + robot.getClass().getSimpleName()
                        + ", Vitesse : " + robot.getVitesse()
                        + ", Réservoir : infini");
                }else{
                    System.out.println("Position : (" + robot.getPosition().getLigne() + ", "
                    + robot.getPosition().getColonne() + "), Type : " + robot.getClass().getSimpleName()
                    + ", Vitesse : " + robot.getVitesse()
                    + ", Réservoir : " + robot.getNiveauReservoirEau());
                }
            }
        }
    }

    /**
     * Affiche les détails des incendies présents dans la simulation.
     *
     * @param donnees Les données de simulation contenant la liste des incendies.
     */
    private static void afficherDetailsIncendies(DonneeSimulation donnees) {
        System.out.println("\n=== Détails des incendies ===");
        if (donnees.getIncendies().isEmpty()) {
            System.out.println("Aucun incendie trouvé.");
        } else {
            for (Incendie incendie : donnees.getIncendies()) {
                System.out.println("Position : (" + incendie.getPosition().getLigne() + ", "
                        + incendie.getPosition().getColonne() + "), Intensité : " + incendie.getIntensite());
            }
        }
    }
}
