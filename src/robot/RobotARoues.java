package robot;
import carte.Case;
import carte.NatureTerrain;

public class RobotARoues extends Robot{
    public RobotARoues(Case position){
        super(position,5000,80);
    }
    public boolean peutSeDeplacerSur(NatureTerrain terrain){
        return terrain==NatureTerrain.TERRAIN_LIBRE||terrain==NatureTerrain.HABITAT;
    }
    public void setVitesseSur(NatureTerrain terrain){
        if (terrain==NatureTerrain.TERRAIN_LIBRE) 
            vitesse= 80.0;
        else if (terrain==NatureTerrain.HABITAT) 
            vitesse/=2;            
        else vitesse= 0;
    }
    public void setVitesse(double  vitesse){
        this.vitesse=vitesse;
    }
    public int getCapaciteMaxReservoir(){
        return 5000;
    }
    public String getType(){
        return "RobotARoues";
    }
}
