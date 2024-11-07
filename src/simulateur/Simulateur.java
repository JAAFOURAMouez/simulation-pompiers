package simulateur;
import java.util.PriorityQueue;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Oval;
import robot.*;
import carte.*;
import java.awt.Color;
import gui.Simulable;

import java.util.ArrayList;
import java.util.List;



public class Simulateur implements  Simulable
{

    private  Carte carte;
    private List<Incendie> incendies;
    private List<Robot> robots;
    private List<Case> initialRobotPositions;
    private List<Double> initialRobotvitesse;
    private List<Integer> initialRobotReservoir;
    private List<Case> initialFirePositions;
    private List<Integer> initialFireintensite;



     
    private long dateSimulation;
    private PriorityQueue<Evenement> evenements;
    private GUISimulator gui;  
    
    public Simulateur(DonneeSimulation donnes) {
    this.carte = donnes.getCarte();
    this.incendies = donnes.geIncendies();
    this.robots = donnes.getRobots();
    this.dateSimulation = 0;
    this.evenements = new PriorityQueue<>();

    // Save initial positions of robots
    this.initialRobotPositions = new ArrayList<>();
    this.initialRobotReservoir = new ArrayList<>();
    this.initialRobotvitesse = new ArrayList<>();

    for (Robot robot : robots) {
        initialRobotPositions.add(robot.getPosition());  // Save initial position as Case
        initialRobotReservoir.add(robot.getNiveauReservoirEau());
        initialRobotvitesse.add(robot.getVitesse());
    }

    // Save initial positions of fires
    this.initialFirePositions = new ArrayList<>();
    this.initialFireintensite = new ArrayList<>();

    for (Incendie incendie : incendies) {
        initialFirePositions.add(incendie.getPosition());  // Save initial position as Case
        initialFireintensite.add(incendie.getIntensite());  // Save initial position as Case

    }

    // GUI setup
    int largeur = 800;
    int hauteur = 600;
    int largeurCase = largeur / carte.getNbColonnes();
    int hauteurCase = hauteur / carte.getNbLignes();
    this.gui = new GUISimulator(largeur, hauteur, Color.WHITE);
    gui.setSimulable(this);
    afficherSimulation(carte, largeurCase, hauteurCase);
}


  

public void afficherSimulation(Carte carte,int largeurCase,int hauteurCase) {      


   
    for (int i = 0; i < carte.getNbLignes(); i++) {
        for (int j = 0; j < carte.getNbColonnes(); j++) {
            int x = j * largeurCase + largeurCase / 2;
            int y = i * hauteurCase + hauteurCase / 2;

            Case myCase=carte.getCase(i,j);
            NatureTerrain nature=myCase.getNature();
            Color cellColor;

            if (nature==NatureTerrain.EAU)
            {
                cellColor = Color.CYAN;  
                
            }

            else if (nature==NatureTerrain.FORET)
            {
                    cellColor = Color.GREEN; 
            }
            else if(nature==NatureTerrain.ROCHE)
            {   
                    cellColor = Color.GRAY;   
            }
            else if (nature==NatureTerrain.HABITAT)

            {
                cellColor = Color.DARK_GRAY;
            }
            else 
            {
                cellColor = Color.YELLOW;  
            }

        

            Rectangle cellRect = new Rectangle(x, y, cellColor, Color.black, largeurCase, hauteurCase);
            gui.addGraphicalElement(cellRect);
        }
    }

    for (Incendie incendie : incendies) {
        if (incendie.getIntensite() > 0)
        {
            int colonneIncendie=incendie.getPosition().getColonne();
            int ligneIncendie=incendie.getPosition().getLigne();
            int x = colonneIncendie * largeurCase + largeurCase / 2;
            int y = ligneIncendie * hauteurCase + hauteurCase / 2;
            
            Oval fireOval = new Oval(x, y, Color.RED, Color.RED, largeurCase / 2, hauteurCase / 2);
            gui.addGraphicalElement(fireOval);
        }

    }

    for (Robot robot : robots) {
        int colonne=robot.getPosition().getColonne();
        int ligne=robot.getPosition().getLigne();

        int x = colonne * largeurCase + largeurCase / 2;
        int y = ligne * hauteurCase + hauteurCase / 2;
        
        Oval robotOval = new Oval(x, y, Color.BLUE, Color.white, largeurCase / 2, hauteurCase / 2);
        gui.addGraphicalElement(robotOval);
    }
}
    @Override
    public void next()
    {

        incrementeDate();
        int largeur = 800;
        int hauteur = 600;
        int largeurCase=largeur/carte.getNbColonnes();
        int hauteurCase=hauteur/carte.getNbLignes();

        afficherSimulation(carte,largeurCase, hauteurCase);

    }

    @Override
    public void restart() {
        this.dateSimulation = 0;
        this.evenements.clear();
    
        // Reset each robot's position to its initial position
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).setPosition(initialRobotPositions.get(i));  // Reset position
            robots.get(i).setVitesse(initialRobotvitesse.get(i));  // Reset position
            robots.get(i).setReservoirEau(initialRobotReservoir.get(i));  // Reset position


        }
    
        // Reset each fire's position to its initial position
        for (int i = 0; i < incendies.size(); i++) {
            incendies.get(i).setPosition(initialFirePositions.get(i));  // Reset position
            incendies.get(i).setIntensite(initialFireintensite.get(i));  // Reset position

        }
    
        int largeur = 800;
        int hauteur = 600;
        int largeurCase = largeur / carte.getNbColonnes();
        int hauteurCase = hauteur / carte.getNbLignes();
    
        afficherSimulation(carte, largeurCase, hauteurCase);
    
        // Repaint the GUI to reflect the reset state
        gui.repaint();
    }
    
    
    public void ajouteEvenement(Evenement e)
    {
        evenements.add(e);

    }

    private  void incrementeDate() {
        
        while (!evenements.isEmpty() && evenements.peek().getDate() <= dateSimulation) {
            Evenement e = evenements.poll();
            e.execute();
        }

        dateSimulation++;
    }

    public  long getDateSimulation()
    {
        return dateSimulation;
    }

    private boolean simulationTerminee(long date)
    {
        return (date > dateSimulation);
    }



}