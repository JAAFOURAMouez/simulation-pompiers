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
        ignorerCommentaires(scanner);

        int nbLignes=scanner.nextInt();
        int nbColonnes=scanner.nextInt();

        int tailleCases=scanner.nextInt();
        Carte carte=new Carte(nbLignes, nbColonnes);
        carte.setTailleCases(tailleCases);
        for(int i=0;i<nbLignes;++i){
            for(int j=0;j<nbColonnes;++j){
                ignorerCommentaires(scanner);
                String nature=scanner.next();
                NatureTerrain natureTerrain=NatureTerrain.valueOf(nature);
                Case myCase=new Case(i,j,natureTerrain);
                carte.add_case(myCase);
                carte.getCase(i, j).setNature(natureTerrain);
            }
        }
        return carte;
    }
    private static List<Incendie> lireIncendies(Scanner scanner){
        ignorerCommentaires(scanner);

        int nbIncendies=scanner.nextInt();
        List<Incendie> incendies=new ArrayList<>();
        for(int i=0;i<nbIncendies;++i){
            ignorerCommentaires(scanner);
            int ligne=scanner.nextInt();
            int colonne=scanner.nextInt();
            int intensite=scanner.nextInt();
            Case caseIncendie=new Case(ligne, colonne, null);
            Incendie incendie=new Incendie(caseIncendie, intensite);
            incendies.add(incendie);
        }
        return incendies;
    }
 private static List<Robot> lireRobots(Scanner scanner, Carte carte) {
    ignorerCommentaires(scanner);

    int nbRobots = scanner.nextInt();
    List<Robot> robots = new ArrayList<>();
    scanner.nextLine(); // Passer à la ligne suivante après avoir lu le nombre de robots

    for (int i = 0; i < nbRobots; ++i) {
        ignorerCommentaires(scanner);
        String ligneRobot = scanner.nextLine().trim();
        Scanner ligneScanner = new Scanner(ligneRobot);

        int ligne = ligneScanner.nextInt();
        int colonne = ligneScanner.nextInt();
        String typeRobot = ligneScanner.next();
        
        int vitesse = 0; // Valeur par défaut si aucune vitesse n'est spécifiée
        if (ligneScanner.hasNextInt()) {
            // Si une vitesse est spécifiée sur la même ligne, on la lit
            vitesse = ligneScanner.nextInt();

        }

        Case positionRobot = carte.getCase(ligne, colonne);
        NatureTerrain nature = positionRobot.getNature();
        Robot robot = creerRobot(typeRobot, positionRobot, vitesse, nature);
        robots.add(robot);
    }
    return robots;
}


    



    private static Robot creerRobot(String typeRobot,Case positionRobot,double  vitesse,NatureTerrain nature){
        switch (typeRobot.toUpperCase()) {
            case "DRONE":
                // la drone on peut modifier sa vitesse
                if (vitesse==0)
                {
                    Drone drone=new Drone(positionRobot);
                    drone.setVitesseSur(nature);
                    return drone;
                }
                Drone drone=new Drone(positionRobot);
                drone.setVitesse(vitesse);
                return drone;

                

            case "ROUES":
                if (vitesse==0)
                {
    
                    RobotARoues robot_a_roues=new RobotARoues(positionRobot);
                    robot_a_roues.setVitesseSur(nature);
                    return robot_a_roues;         
                }


                RobotARoues robot_a_roues=new RobotARoues(positionRobot);
                robot_a_roues.setVitesse(vitesse);
                return robot_a_roues;
            case "PATTES":
                if (vitesse==0)
                {   
                    RobotAPattes robot_a_pattes=new RobotAPattes(positionRobot);
                    robot_a_pattes.setVitesseSur(nature);
                    return robot_a_pattes;   
                }
                    RobotAPattes robot_a_pattes=new RobotAPattes(positionRobot);
                    robot_a_pattes.setVitesse(vitesse);
                    return robot_a_pattes;   

            case "CHENILLES":
                if (vitesse==0)
                {
                    RobotAChenilles robot= new RobotAChenilles(positionRobot);
                    robot.setVitesseSur(nature);
                    return robot;
                }
                RobotAChenilles robot= new RobotAChenilles(positionRobot);
                robot.setVitesse(vitesse);
                return robot;
            default:
                throw new IllegalArgumentException("Type de robot inconnu : "+ typeRobot);
        }
    }



    //fct qui permet d'ignorer les commentaires et les lignes vide
    public static void ignorerCommentaires(Scanner scanner) {
        while (scanner.hasNext("#.*")) 
        {
            scanner.nextLine();
        }



}
}
