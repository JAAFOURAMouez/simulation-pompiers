package robot;
import carte.Case;
import carte.NatureTerrain;

public class RobotAChenilles extends Robot {
    public RobotAChenilles(Case position){
        super(position,2000,60);
    }
    public boolean peutSeDeplacerSur(NatureTerrain terrain){
        return true;
    }
    public void setVitesseSur(NatureTerrain terrain){
        if (terrain==NatureTerrain.FORET)
            vitesse/=2;
        else if (terrain!=NatureTerrain.HABITAT&&terrain!=NatureTerrain.TERRAIN_LIBRE){
            vitesse=0;
        }
    }
    public void setVitesse(double vitesse){
        if (vitesse>80) throw new IllegalArgumentException("la vitesse du robot est superieure a 80km");
        else this.vitesse=vitesse;
    }    
    public int getCapaciteMaxReservoir(){
        return 2000;
    }
    
    public double getTempsTotal(int vol)
    {
        return vol * 1.5;
    }
    

    public String getType(){
        return "RobotAChentilles";
    }
}
