public abstract class Robot{
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
    public void remplirEau(){
        if (position.getNature()==NatureTerrain.EAU && 
            reservoirEau<getCapaciteMaxReservoir()){
            reservoirEau=getCapaciteMaxReservoir();
        }
    }
    public void deplacer(Direction direction,Carte carte){
        Case nouvellePosition=carte.getVoisin(position, direction);
        if (peutSeDeplacerSur(nouvellePosition.getNature()))
            this.position=nouvellePosition;
        else 
            throw new IllegalArgumentException("Le robot ne peut pas se déplacer sur ce terrain.");
    }

    public abstract int getCapaciteMaxReservoir();
    public abstract boolean peutSeDeplacerSur(NatureTerrain terrain);
    public abstract void setVitesseSur(NatureTerrain terrain);

}