package robot;
import carte.Case;
import carte.NatureTerrain;

public class Drone extends Robot {
    public Drone (Case position){
        super(position,10000,100);
    }
    public boolean peutSeDeplacerSur(NatureTerrain terrain){
        return true;
    }
    public void setVitesse(double vitesse){
        if (vitesse>150) throw new IllegalArgumentException("la vitesse du drone est superieure a 150km");
        else this.vitesse=vitesse;
    }
    //a modifier on peut lire la vitesse 
    public void setVitesseSur(NatureTerrain terrain){
        vitesse= 100.0;
    }
    public int getCapaciteMaxReservoir(){
        return 10000;
    }

    public double getTempsTotal(int vol)
    {
        return vol * 0.18;
    }
    
    public String getType(){
        return "Drone";
    }
}
