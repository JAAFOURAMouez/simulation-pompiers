package robot;
import carte.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import simulateur.Simulateur;

public abstract class Robot{
    private Carte carte;
    private Case position;
    private int reservoirEau;
    private Simulateur simulateur;
    protected double vitesse;
    public Robot(Case position,int reservoirEau,int vitesse){
        this.position=position;
        this.reservoirEau=reservoirEau;
        this.vitesse=vitesse;
    }
    public Case getPosition(){
        return position;
    }
    public double getVitesse()
    {
        return vitesse;
    }

    public void setCarte(Carte carte){
        this.carte = carte;
    }

    public void setSimulateur(Simulateur simulateur){
        this.simulateur = simulateur;
    }
    
    public void setVitesse(double vitesse)
    {
        this.vitesse=vitesse;
    }

    public void setPosition(Case pos){
        this.position=pos;
    }
    public void deverserEau(int vol){
        if (vol>reservoirEau)
            reservoirEau=0;
            
            
        else reservoirEau-=vol;
    }
    public int getNiveauReservoirEau(){
        return reservoirEau;
    }




    public void remplirEau(int vol) {
       
        
            int niv=this.getNiveauReservoirEau();
            if (niv+vol > this.getCapaciteMaxReservoir())
            {
                this.getCapaciteMaxReservoir();
            }
            else
            {
                this.setReservoirEau(niv+vol);

            }
        
    }

    public void setReservoirEau(int nivreservoir)
    {
        this.reservoirEau=nivreservoir;
    }


    public void initialiserRobot(Carte carte, Simulateur simulateur){
        this.setCarte(carte);
        this.setSimulateur(simulateur);
    }
        
    
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

        // Programmer les événements de déplacement pour chaque étape du chemin
        for (SimpleEntry<Case, Direction> etape : cheminOptimal) {
            Direction direction = etape.getValue();
            // System.out.println(direction + "\n");
            if (direction != null) { // Ignorer la première case (départ)
                Deplacement deplacement = new Deplacement(carte, this, direction, temps);
                simulateur.ajouteEvenement(deplacement);
                temps += 1; // Incrémenter le temps pour chaque déplacement
            }
        }
        // System.out.println("------------------------------------------------");
        return temps - t;
    }

    public abstract int getCapaciteMaxReservoir();
    public abstract boolean peutSeDeplacerSur(NatureTerrain terrain);
    public abstract void setVitesseSur(NatureTerrain terrain);
    public abstract String getType();
    public abstract double getTempsTotal(int vol);


}
