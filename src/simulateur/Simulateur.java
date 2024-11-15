package simulateur;
import java.util.PriorityQueue;
import gui.GUISimulator;
import robot.*;
import carte.*;
import java.awt.Color;
import gui.Simulable;
import gui.ImageElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Simulateur pour gérer la simulation des robots et des incendies sur une carte.
 * Cette classe implémente l'interface {@link Simulable} pour permettre une interaction avec l'interface graphique.
 */
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

    /**
     * Constructeur du Simulateur. Initialise les données de simulation et configure l'interface graphique.
     *
     * @param donnes Les données nécessaires pour la simulation (carte, robots, incendies).
     */
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

    /**
     * Affiche la simulation dans l'interface graphique.
     *
     * @param carte        La carte à afficher.
     * @param largeurCase  Largeur d'une case dans l'interface.
     * @param hauteurCase  Hauteur d'une case dans l'interface.
     */
    public void afficherSimulation(Carte carte, int largeurCase, int hauteurCase) {
        gui.reset();

        // Dessiner la carte avec les différents types de terrain
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

        // Ajouter les incendies sur la carte
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
                case "RobotAChenilles" -> "ressources/achenilles.png";
                default -> "ressources/firetruck.png";
            };
            int x = robot.getPosition().getColonne() * largeurCase;
            int y = robot.getPosition().getLigne() * hauteurCase;
            gui.addGraphicalElement(new ImageElement(x, y, robotImagePath, largeurCase, hauteurCase, null));
        }
    }

    /**
     * Méthode appelée à chaque étape de la simulation (suivant l'appui sur le bouton "Next").
     */
    @Override
    public void next() {
        incrementeDate();
        int largeur = 800;
        int hauteur = 600;
        int largeurCase = largeur / carte.getNbColonnes();
        int hauteurCase = hauteur / carte.getNbLignes();
        afficherSimulation(carte, largeurCase, hauteurCase);
    }

    /**
     * Réinitialise la simulation à son état initial.
     */
    @Override
    public void restart() {
        this.dateSimulation = 0;
        this.evenements.clear();
        this.evenements.addAll(initialEvents);

        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).setPosition(initialRobotPositions.get(i));
            robots.get(i).setVitesse(initialRobotvitesse.get(i));
            robots.get(i).setReservoirEau(initialRobotReservoir.get(i));
        }

        for (int i = 0; i < incendies.size(); i++) {
            incendies.get(i).setIntensite(initialFireintensite.get(i));
        }

        int largeur = 800;
        int hauteur = 600;
        int largeurCase = largeur / carte.getNbColonnes();
        int hauteurCase = hauteur / carte.getNbLignes();
        afficherSimulation(carte, largeurCase, hauteurCase);
        gui.repaint();
    }

    /**
     * Ajoute un événement à la file des événements de la simulation.
     *
     * @param e L'événement à ajouter.
     */
    public void ajouteEvenement(Evenement e) {
        evenements.add(e);
        initialEvents.add(e);
    }

    /**
     * Incrémente la date de la simulation et exécute les événements correspondants.
     */
    private void incrementeDate() {
        while (!evenements.isEmpty() && evenements.peek().getDate() <= dateSimulation) {
            Evenement e = evenements.poll();
            e.execute();
        }
        if (!evenements.isEmpty()) {
            dateSimulation = evenements.peek().getDate();
        }
    }

    /**
     * Retourne la date actuelle de la simulation.
     *
     * @return La date de simulation.
     */
    public long getDateSimulation() {
        return dateSimulation;
    }

    /**
     * Vérifie si la simulation est terminée.
     *
     * @param date La date à vérifier.
     * @return true si la simulation est terminée, false sinon.
     */
    @SuppressWarnings("unused")
    private boolean simulationTerminee(long date) {
        return date > dateSimulation;
    }
}
