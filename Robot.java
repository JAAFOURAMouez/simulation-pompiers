

public abstract class Robot{
    private Carte carte;
    private Case position;
    private int reservoirEau;
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
    public void setVitesse(double vitesse)
    {
        this.vitesse=vitesse;
    }

    public void setPosition(Case pos){
        this.position=pos;
    }
    void deverserEau(int vol){
        if (vol>reservoirEau)
            reservoirEau=0;
            
            
        else reservoirEau-=vol;
    }
    public int getNiveauReservoirEau(){
        return reservoirEau;
    }




    public void remplirEau() {
        if (position.getNature()==NatureTerrain.EAU && this.getNiveauReservoirEau()==0)
        {
            this.getCapaciteMaxReservoir();
        }
    }

    public void setReservoirEau(int nivreservoir)
    {
        this.reservoirEau=nivreservoir;
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

    public abstract int getCapaciteMaxReservoir();
    public abstract boolean peutSeDeplacerSur(NatureTerrain terrain);
    public abstract void setVitesseSur(NatureTerrain terrain);

}