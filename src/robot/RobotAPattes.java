package robot;
import carte.Case;
import carte.NatureTerrain;

public class RobotAPattes extends Robot {
    public RobotAPattes(Case position){
        super(position, Integer.MAX_VALUE, 30);
    }
    @Override
    public void setVitesseSur(NatureTerrain terrain){
        vitesse = switch (terrain) {
            case EAU -> 0;
            case ROCHE -> 10;
            default -> vitesseBase;
        };
    }
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain){
        return terrain!=NatureTerrain.EAU;
    }
    @Override
    public void setVitesse(double vitesse){
        this.vitesse=vitesse;
    }
    public void setVitesseBase(double vitesse){
        this.vitesse=vitesse;
        this.vitesseBase = vitesse;
    }
    @Override
    public int getCapaciteMaxReservoir(){
        return Integer.MAX_VALUE;
    }
    @Override
    public double getTempsTotal(int vol){
        return 0;
    }
    @Override
    public String getType(){
        return "RobotAPattes";
    }
}
