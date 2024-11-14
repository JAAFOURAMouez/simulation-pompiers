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

public final class Simulateur implements Simulable {

    private final Carte carte;
    private final List<Incendie> incendies;
    private final List<Robot> robots;

    private final List<Case> initialRobotPositions;
    private final List<Double> initialRobotvitesse;
    private final List<Integer> initialRobotReservoir;
    private final List<Integer> initialFireintensite;
    private final List<Evenement> initialEvents;

    private long dateSimulation;
    private final PriorityQueue<Evenement> evenements;
    private GUISimulator gui;  

    public Simulateur(DonneeSimulation donnes) {
        this.carte = donnes.getCarte();
        this.incendies = donnes.getIncendies();
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

                imagePath = switch (nature) {
                    case EAU -> "ressources/sea.png";
                    case FORET -> "ressources/forest.png";
                    case ROCHE -> "ressources/rocks.png";
                    case HABITAT -> "ressources/city.png";
                    default -> "ressources/grass.png";
                };

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
                case "RobotAChentilles" -> "ressources/achantilles.png";
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
            //System.out.println("Executing event at time: " + e.getDate());  // Impression directe lors de l'exécution
        }
        if (!evenements.isEmpty()) {
            dateSimulation = evenements.peek().getDate();
        }
    }

    public long getDateSimulation() {
        return dateSimulation;
    }

    @SuppressWarnings("unused")
    private boolean simulationTerminee(long date) {
        return date > dateSimulation;
    }
}