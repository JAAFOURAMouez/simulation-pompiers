package robot;
import carte.Case;
import carte.NatureTerrain;

public class RobotARoues extends Robot{
    public RobotARoues(Case position){
        super(position,5000,80);
    }
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain){
        return terrain==NatureTerrain.TERRAIN_LIBRE||terrain==NatureTerrain.HABITAT;
    }
    @Override
    public void setVitesseSur(NatureTerrain terrain){
        if (null==terrain) 
            vitesse= 0;
        else vitesse = switch (terrain) {
            case TERRAIN_LIBRE -> vitesseBase;
            case HABITAT -> vitesseBase/2;
            default -> 0;
        };
    }
    @Override
    public void setVitesse(double  vitesse){
        this.vitesse=vitesse;
    }
    public void setVitesseBase(double  vitesse){
        this.vitesse=vitesse;
        this.vitesseBase=vitesse;
    }
    @Override
    public int getCapaciteMaxReservoir(){
        return 5000;
    }
    @Override
    public double getTempsTotal(int vol)
    {
        return vol * 0.12;
    }
    @Override
    public String getType(){
        return "RobotARoues";
    }
}
