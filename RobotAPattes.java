public class RobotAPattes extends Robot {
    public RobotAPattes(Case position){
        super(position, 100, 30);
    }
    public void setVitesseSur(NatureTerrain terrain){
        if (terrain==NatureTerrain.EAU) vitesse=0;
        else if (terrain==NatureTerrain.ROCHE)vitesse=15;
        else vitesse=30;
    }
    public boolean peutsedeplacerSur(NatureTerrain terrain){
        if (terrain==NatureTerrain.EAU) 
            return false;
        return true;
    }
    public int getCapaciteMaxReservoir(){
        return 100;
    }

}
