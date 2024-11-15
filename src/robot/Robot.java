package robot;
import carte.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import simulateur.Simulateur;
// Classe abstraite représentant un robot
public abstract class Robot{
    private Carte carte;
    private Case position;
    private int reservoirEau;
    private Simulateur simulateur;
    protected double vitesse;
    protected double vitesseBase;

    // Constructeur du robot avec position, niveau d'eau et vitesse initiale
    public Robot(Case position,int reservoirEau,int vitesse){
        this.position=position;
        this.reservoirEau=reservoirEau;
        this.vitesse=vitesse;
        this.vitesseBase=vitesse;
    }
    public Case getPosition(){
        return position;
    }
    public double getVitesse(){
        return vitesse;
    }
    public double getVitesseBase(){
        return vitesseBase;
    }

    public void setCarte(Carte carte){
        this.carte = carte;
    }

    public void setSimulateur(Simulateur simulateur){
        this.simulateur = simulateur;
    }
    
    public void setVitesse(double vitesse){
        this.vitesse=vitesse;
    }

    public void setPosition(Case pos){
        this.position=pos;
    }

    // Méthode pour déverser une quantité d'eau depuis le réservoir du robot
    public void deverserEau(int vol){
        if (vol>reservoirEau)
            reservoirEau=0;
        else reservoirEau-=vol;
    }

    public int getNiveauReservoirEau(){
        return reservoirEau;
    }

    // Méthode pour remplir le réservoir d'eau du robot
    public void remplirEau(int vol) {
        if (vol < 0) throw new IllegalArgumentException("Le volume d'eau ne peut pas être négatif.");
        int niv = this.getNiveauReservoirEau();
        if (niv + vol > this.getCapaciteMaxReservoir()) {
            this.setReservoirEau(this.getCapaciteMaxReservoir());
        } else {
            this.setReservoirEau(niv + vol);
        }
    }
    

    public void setReservoirEau(int nivreservoir){
        this.reservoirEau=nivreservoir;
    }

    public void initialiserRobot(Carte carte, Simulateur simulateur){
        this.setCarte(carte);
        this.setSimulateur(simulateur);
    }
        
    // Méthode pour déplacer le robot dans une direction donnée, si possible
    public void deplacer(Direction direction,Carte carte){
        Case nouvellePosition=carte.getVoisin(position, direction);
        if (peutSeDeplacerSur(nouvellePosition.getNature()))
        {
            this.position=nouvellePosition;
        }
        else {
            throw new IllegalArgumentException("Le robot ne peut pas se déplacer sur ce type de terrain.");
        }
    }
    //deplacer le robot vers une case et retourner le temps ecoule
    public long  deplacerVersCase(Case depart, Case destination, long t) {
        RechercheChemin rechercheChemin = new RechercheChemin(carte);
        ResultatChemin resultat = rechercheChemin.calculerCheminOptimal(depart, destination, this);
        // System.out.println(position.getLigne() + " " + position.getColonne() + "//" + destination.getLigne() + " "+ destination.getColonne());
        if (resultat.getCheminOptimal() == null) {
            System.out.println("Aucun chemin trouvé pour atteindre la destination.");
            return -1;
        }

        List<SimpleEntry<Case, Direction>> cheminOptimal = resultat.getCheminOptimal();
        long temps = t;
        double tempsDeplacement;
        
        // Programmer les événements de déplacement pour chaque étape du chemin
        for (SimpleEntry<Case, Direction> etape : cheminOptimal) {
            Direction direction = etape.getValue();
            if (direction != null) { // Ignorer la première case (départ)
                Deplacement deplacement = new Deplacement(carte, this, direction, temps);
                simulateur.ajouteEvenement(deplacement);
                tempsDeplacement= carte.getTailleCases() / (1000 *this.getVitesse());//par h
                tempsDeplacement*=3600;//par s

                temps += (long) tempsDeplacement; // Incrémenter le temps pour chaque déplacement
            }
        }
        return temps - t;
    }
    // Méthodes abstraites : Chaque type de robot devra les implémenter
    public abstract int getCapaciteMaxReservoir();
    public abstract boolean peutSeDeplacerSur(NatureTerrain terrain);
    public abstract void setVitesseSur(NatureTerrain terrain);
    public abstract String getType();
    public abstract double getTempsRemplissage(int vol);
}
