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

    public void remplirEau(){
        if (position.getNature()==NatureTerrain.EAU && reservoirEau<getCapaciteMaxReservoir()){
            reservoirEau=getCapaciteMaxReservoir();
        }
    }

    public abstract int getCapaciteMaxReservoir();
    public abstract boolean peutsedeplacerSur(NatureTerrain terrain);
    public abstract void setVitesseSur(NatureTerrain terrain);

}