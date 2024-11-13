package robot;
import carte.Case;
import carte.NatureTerrain;

public class Drone extends Robot {
    public Drone (Case position){
        super(position,10000,100);
    }
    @Override
    public boolean peutSeDeplacerSur(NatureTerrain terrain){
        return true;
    }
    @Override
    public void setVitesse(double vitesse){
        if (vitesse>150) throw new IllegalArgumentException("la vitesse du drone est superieure a 150km");
        else this.vitesse=vitesse;
    }
    @Override
    //a modifier on peut lire la vitesse 
    public void setVitesseSur(NatureTerrain terrain){
        vitesse= vitesseBase;
    }
    @Override
    public int getCapaciteMaxReservoir(){
        return 10000;
    }
    @Override
    public double getTempsTotal(int vol)
    {
        return vol * 0.18;
    }
    @Override
    public String getType(){
        return "Drone";
    }
}
