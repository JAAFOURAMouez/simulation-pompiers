public class Drone extends Robot {
    public Drone (Case position){
        super(position,250,160);
    }
    public boolean peutsedeplacerSur(NatureTerrain terrain){
        return true;
    }
    public void setVitesseSur(NatureTerrain terrain){
        vitesse= 160.0;
    }
    public int getCapaciteMaxReservoir(){
        return 250;
    }
}
