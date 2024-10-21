import java.io.FileNotFoundException;

public class test_map {

    public static void main(String[] args) {
            try {
            // Test de la lecture des données
            System.out.println("Début de la lecture des données...");
            
            DonneeSimulation donnees = LectureDonnee.lire("carteSujet.map");
            System.out.println("Données lues avec succès.");

            // Affiche les détails de la carte
            System.out.println("=== Détails de la carte ===");
            Carte carte = donnees.getCarte();
            System.out.println("Nombre de lignes : " + carte.getNbLignes());
            System.out.println("Nombre de colonnes : " + carte.getNbColonnes());
            System.out.println("Taille des cases : " + carte.getTailleCases());


            // Affiche les détails des robots
            System.out.println("\n=== Détails des robots ===");
            for (Robot robot : donnees.getRobots()) {
                System.out.println("Position : (" + robot.getPosition().getLigne() + ", "
                    + robot.getPosition().getColonne() + "), Type : " + robot.getClass().getSimpleName()
                    + ", Vitesse : " + robot.getVitesse());
            }

            } catch (FileNotFoundException e) {
        System.err.println("Erreur : le fichier 'carteSujet.map' est introuvable.");
    }


    }
}