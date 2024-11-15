package robot;

import carte.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

/**
 * Représente le résultat d'un calcul de chemin optimal pour un robot, comprenant
 * le chemin optimal sous forme de liste de cases et directions, ainsi que le temps total 
 * nécessaire pour parcourir ce chemin.
 */
public class ResultatChemin {
    
    /**
     * Liste des étapes du chemin optimal sous forme de paires de cases et directions.
     */
    private final List<SimpleEntry<Case, Direction>> cheminOptimal;
    
    /**
     * Temps total nécessaire pour parcourir le chemin optimal.
     */
    private final double tempsTotal;
    
    /**
     * Constructeur qui initialise le résultat du chemin optimal.
     * 
     * @param cheminOptimal La liste des étapes du chemin optimal, où chaque étape est représentée 
     *                      par une paire de case et direction.
     * @param tempsTotal Le temps total nécessaire pour parcourir le chemin optimal.
     */
    public ResultatChemin(List<SimpleEntry<Case, Direction>> cheminOptimal, double tempsTotal) {
        this.cheminOptimal = cheminOptimal;
        this.tempsTotal = tempsTotal;
    }

    /**
     * Retourne le chemin optimal.
     * 
     * @return La liste des étapes du chemin optimal.
     */
    public List<SimpleEntry<Case, Direction>> getCheminOptimal() {
        return cheminOptimal;
    }

    /**
     * Retourne le temps total pour parcourir le chemin optimal.
     * 
     * @return Le temps total en secondes pour parcourir le chemin optimal.
     */
    public double getTempsTotale() {
        return tempsTotal;
    }
}
