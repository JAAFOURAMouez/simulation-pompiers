public class RobotARoues extends Robot{
    public RobotARoues(Case position){
        super(position,500,100);
    }
    public boolean peutsedeplacerSur(NatureTerrain terrain){
        return terrain==NatureTerrain.TERRAIN_LIBRE||terrain==NatureTerrain.HABITAT;
    }
    public void setVitesseSur(NatureTerrain terrain){
        if (terrain==NatureTerrain.TERRAIN_LIBRE) 
            vitesse= 100.0;
        else if (terrain==NatureTerrain.HABITAT) 
            vitesse= 50.0;            
        else vitesse= 0;
    }
    public int getCapaciteMaxReservoir(){
        return 500;
    }
}
