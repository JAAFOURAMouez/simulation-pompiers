package carte;

import robot.Robot;

/**
 * Classe représentant un incendie sur la carte.
 * Chaque incendie a une position (case) et une intensité.
 * L'intensité représente la quantité d'eau nécessaire pour éteindre l'incendie.
 */
public class Incendie {

    private final Case position; // Position de l'incendie sur la carte
    private int intensite; // Intensité de l'incendie (mesurée en quantité d'eau nécessaire pour l'éteindre)

    /**
     * Constructeur pour initialiser un incendie avec sa position et son intensité.
     *
     * @param position La case où l'incendie se trouve sur la carte.
     * @param intensite L'intensité de l'incendie, mesurée en quantité d'eau nécessaire pour l'éteindre.
     */
    public Incendie(Case position, int intensite){
        this.position = position;
        this.intensite = intensite;
    }

    /**
     * Méthode pour éteindre l'incendie en utilisant une certaine quantité d'eau.
     * L'intensité de l'incendie est réduite en fonction de la quantité d'eau utilisée.
     * Si l'intensité devient inférieure à zéro, elle est fixée à zéro (incendie éteint).
     *
     * @param quantiteEau La quantité d'eau utilisée pour éteindre l'incendie.
     */
    public void eteindre(int quantiteEau){
        if(quantiteEau > 0){
            this.intensite -= quantiteEau;
            if(this.intensite < 0){
                this.intensite = 0; // L'intensité ne peut pas être inférieure à zéro
            }
        }
    }

    /**
     * Récupère la position de l'incendie sur la carte.
     *
     * @return La case représentant la position de l'incendie.
     */
    public Case getPosition(){
        return position;
    }

    /**
     * Récupère l'intensité actuelle de l'incendie.
     *
     * @return L'intensité de l'incendie.
     */
    public int getIntensite(){
        return intensite;
    }

    /**
     * Définit l'intensité de l'incendie.
     *
     * @param intensite La nouvelle intensité de l'incendie.
     */
    public void setIntensite(int intensite){
        this.intensite = intensite;
    }

    /**
     * Calcule le temps d'intervention d'un robot pour éteindre l'incendie en fonction de son type.
     * Le temps d'intervention dépend du volume d'eau utilisé et du type de robot.
     *
     * @param robot L'objet robot qui intervient pour éteindre l'incendie.
     * @param vol Le volume d'eau utilisé par le robot pour éteindre l'incendie.
     * @return Le temps nécessaire pour éteindre l'incendie, en fonction du type du robot et du volume d'eau utilisé.
     */
    public double tempsIntervention(Robot robot, int vol){
        double tempsIntervention;
        switch (robot.getType()) {
            case "Drone" -> tempsIntervention = vol * 0.003; // Temps d'intervention pour un Drone
            case "RobotAChentilles" -> tempsIntervention = vol * 0.08; // Temps d'intervention pour un Robot à chenilles
            case "RobotARoues" -> tempsIntervention = vol * 0.05; // Temps d'intervention pour un Robot à roues
            default -> tempsIntervention = vol / 10.0; // Temps d'intervention pour d'autres types de robots
        }
        return tempsIntervention;
    }
}
