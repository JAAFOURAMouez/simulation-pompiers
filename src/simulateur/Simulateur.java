package simulateur;
import java.util.PriorityQueue;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Oval;
import robot.*;
import carte.*;
import java.awt.Color;
import gui.Simulable;
import gui.ImageElement;
import java.util.ArrayList;
import java.util.List;



public class Simulateur implements Simulable {

    private Carte carte;
    private List<Incendie> incendies;
    private List<Robot> robots;

    private List<Case> initialRobotPositions;
    private List<Double> initialRobotvitesse;
    private List<Integer> initialRobotReservoir;
    private List<Integer> initialFireintensite;
    private List<Evenement> initialEvents;

    private long dateSimulation;
    private PriorityQueue<Evenement> evenements;
    private GUISimulator gui;  

    public Simulateur(DonneeSimulation donnes) {
        this.carte = donnes.getCarte();
        this.incendies = donnes.geIncendies();
        this.robots = donnes.getRobots();
        this.dateSimulation = 0;
        this.evenements = new PriorityQueue<>();

        // Sauvegarde des états initiaux
        this.initialRobotPositions = new ArrayList<>();
        this.initialRobotReservoir = new ArrayList<>();
        this.initialRobotvitesse = new ArrayList<>();
        this.initialEvents = new ArrayList<>();

        for (Robot robot : robots) {
            initialRobotPositions.add(robot.getPosition());
            initialRobotReservoir.add(robot.getNiveauReservoirEau());
            initialRobotvitesse.add(robot.getVitesse());
        }

        // Sauvegarder les états initiaux des incendies
        this.initialFireintensite = new ArrayList<>();
        for (Incendie incendie : incendies) {
            initialFireintensite.add(incendie.getIntensite());
        }

        // Sauvegarder les événements initiaux
        initialEvents.addAll(evenements);

        // Configuration de l'interface graphique
        int largeur = 800;
        int hauteur = 600;
        int largeurCase = largeur / carte.getNbColonnes();
        int hauteurCase = hauteur / carte.getNbLignes();

        this.gui = new GUISimulator(largeur, hauteur, Color.WHITE);
        gui.setSimulable(this);
        afficherSimulation(carte, largeurCase, hauteurCase);
    }

    public void afficherSimulation(Carte carte, int largeurCase, int hauteurCase) {
        gui.reset();

        // Redessiner la carte
        for (int i = 0; i < carte.getNbLignes(); i++) {
            for (int j = 0; j < carte.getNbColonnes(); j++) {
                int x = j * largeurCase;
                int y = i * hauteurCase;

                Case myCase = carte.getCase(i, j);
                NatureTerrain nature = myCase.getNature();
                String imagePath;

                switch (nature) {
                    case EAU:
                        imagePath = "ressources/sea.png";
                        break;
                    case FORET:
                        imagePath = "ressources/forest.png";
                        break;
                    case ROCHE:
                        imagePath = "ressources/rocks.png";
                        break;
                    case HABITAT:
                        imagePath = "ressources/city.png";
                        break;
                    default:
                        imagePath = "ressources/grass.png";
                        break;
                }

                gui.addGraphicalElement(new ImageElement(x, y, imagePath, largeurCase, hauteurCase, null));
            }
        }

        // Ajouter les incendies
        String fireImagePath = "ressources/fire.png";
        for (Incendie incendie : incendies) {
            if (incendie.getIntensite() > 0) {
                int x = incendie.getPosition().getColonne() * largeurCase;
                int y = incendie.getPosition().getLigne() * hauteurCase;
                gui.addGraphicalElement(new ImageElement(x, y, fireImagePath, largeurCase, hauteurCase, null));
            }
        }

        // Ajouter les robots
        for (Robot robot : robots) {
            String robotImagePath = switch (robot.getType()) {
                case "Drone" -> "ressources/drone.png";
                case "RobotAPattes" -> "ressources/apattes.png";
                case "RobotAChenilles" -> "ressources/achantilles.png";
                default -> "ressources/firetruck.png";
            };
            int x = robot.getPosition().getColonne() * largeurCase;
            int y = robot.getPosition().getLigne() * hauteurCase;
            gui.addGraphicalElement(new ImageElement(x, y, robotImagePath, largeurCase, hauteurCase, null));
        }
    }

    @Override
    public void next() {
        incrementeDate();
        int largeur = 800;
        int hauteur = 600;
        int largeurCase = largeur / carte.getNbColonnes();
        int hauteurCase = hauteur / carte.getNbLignes();
        afficherSimulation(carte, largeurCase, hauteurCase);
    }

    @Override
    public void restart() {
        // Réinitialiser la date de simulation
        this.dateSimulation = 0;

        // Réinitialiser la file d'événements
        this.evenements.clear();
        this.evenements.addAll(initialEvents);

        // Réinitialiser les robots
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).setPosition(initialRobotPositions.get(i));
            robots.get(i).setVitesse(initialRobotvitesse.get(i));
            robots.get(i).setReservoirEau(initialRobotReservoir.get(i));
        }

        // Réinitialiser les incendies
        for (int i = 0; i < incendies.size(); i++) {
            incendies.get(i).setIntensite(initialFireintensite.get(i));
        }

        // Redessiner la simulation
        int largeur = 800;
        int hauteur = 600;
        int largeurCase = largeur / carte.getNbColonnes();
        int hauteurCase = hauteur / carte.getNbLignes();
        afficherSimulation(carte, largeurCase, hauteurCase);
        gui.repaint();
    }

    public void ajouteEvenement(Evenement e) {
        evenements.add(e);
        initialEvents.add(e);
    }

    private void incrementeDate() {
        while (!evenements.isEmpty() && evenements.peek().getDate() <= dateSimulation) {
            Evenement e = evenements.poll();
            e.execute();
        }
        dateSimulation++;
    }

    public long getDateSimulation() {
        return dateSimulation;
    }

    private boolean simulationTerminee(long date) {
        return date > dateSimulation;
    }
}
