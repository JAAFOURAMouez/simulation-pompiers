package robot;
import carte.Case;
import carte.NatureTerrain;

public class RobotAPattes extends Robot {
    public RobotAPattes(Case position){
        super(position, Integer.MAX_VALUE, 30);
    }
    public void setVitesseSur(NatureTerrain terrain){
        if (terrain==NatureTerrain.EAU) vitesse=0;
        else if (terrain==NatureTerrain.ROCHE)vitesse=10;
        else vitesse=vitesseBase;
    }
    public boolean peutSeDeplacerSur(NatureTerrain terrain){
        return terrain!=NatureTerrain.EAU;
    }
    public void setVitesse(double vitesse){
        this.vitesse=vitesse;
    }
    public int getCapaciteMaxReservoir(){
        return Integer.MAX_VALUE;
    }
    public double getTempsTotal(int vol)
    {
        return 0;
    }
    public String getType(){
        return "RobotAPattes";
    }
}
