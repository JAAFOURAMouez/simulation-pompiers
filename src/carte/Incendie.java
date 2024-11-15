package carte;

import robot.Robot;

public class Incendie {
    private final Case position; // Position de l'incendie sur la carte
    private int intensite; // Intensité de l'incendie (mesurée en quantité d'eau nécessaire pour l'éteindre)
    
    // Constructeur pour initialiser un incendie avec sa position et son intensité
    public Incendie(Case position, int intensite){
        this.position=position;
        this.intensite=intensite;
    }
    // Méthode pour éteindre l'incendie avec une certaine quantité d'eau
    public void eteindre(int quantiteEau){
        if(quantiteEau>0){
            this.intensite-=quantiteEau;
            if(this.intensite<0){
                this.intensite=0;
            }
        }
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
    /**
     * Calcule le temps d'intervention d'un robot pour éteindre l'incendie en fonction de son type.
     * 
     * @param robot L'objet robot qui intervient pour éteindre l'incendie.
     * @param vol Volume d'eau utilisé par le robot.
     * @return Le temps nécessaire pour éteindre l'incendie en fonction du type du robot.
     */
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
