public class RobotAChantilles extends Robot {
    public RobotAChantilles(Case position){
        super(position,250,100);
    }
    public boolean peutsedeplacerSur(NatureTerrain terrain){
        return true;
    }
    public void setVitesseSur(NatureTerrain terrain){
        if (terrain==NatureTerrain.HABITAT||terrain==NatureTerrain.TERRAIN_LIBRE) {
            vitesse=100;
        }else if (terrain==NatureTerrain.FORET)
            vitesse=50;
        else{
            vitesse=0;
        }
    }
    public int getCapaciteMaxReservoir(){
        return 250;
    }
}
