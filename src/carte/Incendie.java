package carte;

import robot.Robot;

public class Incendie {
    private Case position;
    private int intensite;
    public Incendie(Case position, int intensite){
        this.position=position;
        this.intensite=intensite;
    }
    public void eteindre(int quantiteEau){
        if(quantiteEau>0){
            this.intensite-=quantiteEau;
            if(this.intensite<0){
                this.intensite=0;
            }
        }
    }
    public void setPosition(Case position) {
        this.position = position;
    }
    public boolean estEteint(){
        return this.intensite==0;
    }
    public Case getPosition(){
        return position;
    }
    
    public int getIntensite(){
        return intensite;
    }
    public void setIntensite(int intensite){
        this.intensite=intensite;
    }
    public double tempsIntervention(Robot robot,int vol){
        double tempsIntervention;
        switch (robot.getType()) {
            case "Drone" -> tempsIntervention=vol * 0.003;
            case "RobotAChentilles" -> tempsIntervention=vol * 0.08;
            case "RobotARoues" -> tempsIntervention=vol * 0.05;
            default -> tempsIntervention=vol/10;
        }
        return tempsIntervention;
    }
}
