import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LectureDonnee {
    public static DonneeSimulation lire(String fichier) throws FileNotFoundException{
        try{
            Scanner scanner=new Scanner(new File(fichier));
            Carte carte=lireCarte(scanner);
            List<Incendie> incendies=lireIncendies(scanner);
            List<Robot> robots=lireRobots(scanner,carte);
            return new DonneeSimulation(carte, incendies, robots);
        } catch(FileNotFoundException e) {
            System.err.println("Fichier non trouvé:"+fichier+"!!!!");
            return null;

        }
    }
    private static Carte lireCarte(Scanner scanner){
        int nbLignes=scanner.nextInt();
        int nbColonnes=scanner.nextInt();
        int tailleCases=scanner.nextInt();
        Carte carte=new Carte(nbLignes, nbColonnes);
        carte.setTailleCases(tailleCases);
        for(int i=0;i<nbLignes;++i){
            for(int j=0;j<nbColonnes;++j){
                String nature=scanner.next();// Lire la nature de chaque case
                NatureTerrain natureTerrain=NatureTerrain.valueOf(nature);
                Carte.getCase(i, j).setNature(natureTerrain);
            }
        }
        return carte;
    }
    private static List<Incendie> lireIncendies(Scanner scanner){
        int nbIncendies=scanner.nextInt();
        List<Incendie> incendies=new ArrayList<>();
        for(int i=0;i<nbIncendies;++i){
            int ligne=scanner.nextInt();
            int colonne=scanner.nextInt();
            int intensite=scanner.nextInt();
            Case caseIncendie=new Case(ligne, colonne, null);
            Incendie incendie=new Incendie(caseIncendie, intensite);
            incendies.add(incendie);
        }
        return incendies;
    }
    private static List<Robot> lireRobots(Scanner scanner,Carte carte){
        int nbRobots=scanner.nextInt();
        List<Robot> robots=new ArrayList<>();
        for(int i=0;i<nbRobots;++i){
            int ligne=scanner.nextInt();
            int colonne=scanner.nextInt();
            String typeRobot=scanner.next();
            int vitesse = scanner.nextInt(); 
            Case positionRobot=Carte.getCase(ligne,colonne);
            Robot robot=creerRobot(typeRobot,positionRobot,vitesse);
            robots.add(robot);
        }
        return robots;
    }
    private static Robot creerRobot(String typeRobot,Case positionRobot,int vitesse){
        switch (typeRobot.toUpperCase()) {
            case "DRONE":
                // la drone on peut modifier sa vitesse
                Drone drone=new Drone(positionRobot);
                drone.setVitesse(vitesse);
                return drone;
            case "ROUES":
                return new RobotARoues(positionRobot);
            case "PATTES":
                return new RobotAPattes(positionRobot);
            case "CHANTILLES":
                RobotAChantilles robot= new RobotAChantilles(positionRobot);
                robot.setVitesse(vitesse);
                return robot;
            default:
                throw new IllegalArgumentException("Type de robot inconnu : "+ typeRobot);
        }
    }

}
