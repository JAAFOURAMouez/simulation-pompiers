package simulateur;
import carte.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import robot.*;

/**
 * Classe LectureDonnee qui permet de lire les données de simulation à partir d'un fichier texte.
 */
public class LectureDonnee {
    private static final List<Case> casesEau = new ArrayList<>();

    /**
     * Lit un fichier de simulation et retourne un objet DonneeSimulation contenant les données lues.
     *
     * @param fichier Le chemin du fichier à lire.
     * @return Un objet DonneeSimulation contenant la carte, les incendies, les robots, et les cases d'eau.
     * @throws FileNotFoundException si le fichier spécifié est introuvable.
     */
    public static DonneeSimulation lire(String fichier) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File(fichier));
            Carte carte = lireCarte(scanner);
            List<Incendie> incendies = lireIncendies(scanner);
            List<Robot> robots = lireRobots(scanner, carte);
            return new DonneeSimulation(carte, incendies, robots, casesEau);
        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + fichier + " !!!!");
            throw e; // relance l'exception pour le traitement en dehors de la méthode
        }
    }

    /**
     * Lit la carte à partir du scanner.
     *
     * @param scanner Le scanner utilisé pour lire le fichier.
     * @return Un objet Carte représentant la carte lue.
     */
    private static Carte lireCarte(Scanner scanner) {
        ignorerCommentaires(scanner);
        int nbLignes = scanner.nextInt();
        int nbColonnes = scanner.nextInt();
        int tailleCases = scanner.nextInt();

        Carte carte = new Carte(nbLignes, nbColonnes);
        carte.setTailleCases(tailleCases);

        for (int i = 0; i < nbLignes; ++i) {
            for (int j = 0; j < nbColonnes; ++j) {
                ignorerCommentaires(scanner);
                String nature = scanner.next();
                NatureTerrain natureTerrain = NatureTerrain.valueOf(nature);

                Case myCase = new Case(i, j, natureTerrain);
                if (natureTerrain == NatureTerrain.EAU) {
                    casesEau.add(myCase);
                }

                carte.add_case(myCase);
                carte.getCase(i, j).setNature(natureTerrain);
            }
        }
        return carte;
    }

    /**
     * Lit la liste des incendies à partir du scanner.
     *
     * @param scanner Le scanner utilisé pour lire le fichier.
     * @return Une liste d'objets Incendie.
     */
    private static List<Incendie> lireIncendies(Scanner scanner) {
        ignorerCommentaires(scanner);
        int nbIncendies = scanner.nextInt();
        List<Incendie> incendies = new ArrayList<>();

        for (int i = 0; i < nbIncendies; ++i) {
            ignorerCommentaires(scanner);
            int ligne = scanner.nextInt();
            int colonne = scanner.nextInt();
            int intensite = scanner.nextInt();
            Case caseIncendie = new Case(ligne, colonne, null);
            Incendie incendie = new Incendie(caseIncendie, intensite);
            incendies.add(incendie);
        }
        return incendies;
    }

    /**
     * Lit la liste des robots à partir du scanner.
     *
     * @param scanner Le scanner utilisé pour lire le fichier.
     * @param carte   La carte sur laquelle les robots seront placés.
     * @return Une liste d'objets Robot.
     */
    private static List<Robot> lireRobots(Scanner scanner, Carte carte) {
        ignorerCommentaires(scanner);
        int nbRobots = scanner.nextInt();
        List<Robot> robots = new ArrayList<>();
        scanner.nextLine(); // Passe à la ligne suivante après avoir lu le nombre de robots

        for (int i = 0; i < nbRobots; ++i) {
            ignorerCommentaires(scanner);
            String ligneRobot = scanner.nextLine().trim();

            try (Scanner ligneScanner = new Scanner(ligneRobot)) {
                int ligne = ligneScanner.nextInt();
                int colonne = ligneScanner.nextInt();
                String typeRobot = ligneScanner.next();
                
                int vitesse = 0;
                if (ligneScanner.hasNextInt()) {
                    vitesse = ligneScanner.nextInt();
                }

                Case positionRobot = carte.getCase(ligne, colonne);
                NatureTerrain nature = positionRobot.getNature();
                Robot robot = creerRobot(typeRobot, positionRobot, vitesse, nature);
                robots.add(robot);
            }
        }
        return robots;
    }

    /**
     * Crée un robot en fonction du type spécifié.
     *
     * @param typeRobot     Le type de robot (DRONE, ROUES, PATTES, CHENILLES).
     * @param positionRobot La position initiale du robot.
     * @param vitesse       La vitesse du robot (facultatif).
     * @param nature        La nature du terrain de la case.
     * @return Un objet Robot correspondant au type spécifié.
     * @throws IllegalArgumentException si le type de robot est inconnu.
     */
    private static Robot creerRobot(String typeRobot, Case positionRobot, double vitesse, NatureTerrain nature) {
        switch (typeRobot.toUpperCase()) {
            case "DRONE" -> {
                Drone drone = new Drone(positionRobot);
                if (vitesse < 0) throw new IllegalArgumentException("vitesse de robot negatif : " + typeRobot);
                else{
                if (vitesse >0)drone.setVitesseBase(vitesse);
                else drone.setVitesseSur(nature);
                return drone;
                }
            }
            case "ROUES" -> {
                RobotARoues robotARoues = new RobotARoues(positionRobot);
                if (vitesse < 0) throw new IllegalArgumentException("vitesse de robot negatif : " + typeRobot);
                else{
                if (vitesse >0)robotARoues.setVitesseBase(vitesse);
                else robotARoues.setVitesseSur(nature);
                return robotARoues;}
            }
            case "PATTES" -> {
                RobotAPattes robotAPattes = new RobotAPattes(positionRobot);
                if (vitesse < 0) throw new IllegalArgumentException("vitesse de robot negatif : " + typeRobot);
                else{
                if (vitesse >0)robotAPattes.setVitesseBase(vitesse);
                else robotAPattes.setVitesseSur(nature);
                return robotAPattes;
                }
            }
            case "CHENILLES" -> {
                RobotAChenilles robotAChenilles = new RobotAChenilles(positionRobot);
                if (vitesse < 0) throw new IllegalArgumentException("vitesse de robot negatif : " + typeRobot);
                else{ 
                if (vitesse >0)robotAChenilles.setVitesseBase(vitesse);
                else robotAChenilles.setVitesseSur(nature);
                return robotAChenilles;
                }
            }
            default -> throw new IllegalArgumentException("Type de robot inconnu : " + typeRobot);
        }
    }

    /**
     * Ignore les lignes de commentaires et les lignes vides dans le fichier.
     *
     * @param scanner Le scanner utilisé pour lire le fichier.
     */
    public static void ignorerCommentaires(Scanner scanner) {
        while (scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }
}
