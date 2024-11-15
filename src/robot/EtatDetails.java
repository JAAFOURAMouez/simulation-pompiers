package robot;

import carte.Case;

/**
 * Classe représentant l'état détaillé d'un robot à un instant donné.
 * Cette classe contient des informations sur le temps estimé pour une tâche, la case associée au robot, 
 * la quantité d'eau restante dans le réservoir et le temps courant (timestamp).
 */
public class EtatDetails {

    private final Double temps;          // Temps estimé (peut représenter le temps de déplacement ou d'intervention)
    private Case caseAssociee;           // Case associée à cet état (position actuelle du robot)
    private int reservoir;               // Quantité d'eau restante dans le réservoir du robot
    private final long tempsCour;        // Temps courant (timestamp pour suivre les événements)

    /**
     * Constructeur pour initialiser un objet EtatDetails.
     * 
     * @param temps         Le temps estimé en double (peut représenter le temps de déplacement ou d'intervention).
     * @param caseAssociee  La case associée à cet état, représentant la position actuelle du robot.
     * @param reservoir     La quantité d'eau restante dans le réservoir du robot.
     * @param tempsCour     Le temps courant, généralement un timestamp pour suivre les événements.
     */
    public EtatDetails(Double temps, Case caseAssociee, int reservoir, long tempsCour) {
        this.temps = temps;
        this.caseAssociee = caseAssociee;
        this.reservoir = reservoir;
        this.tempsCour = tempsCour;
    }

    /**
     * Récupère le temps estimé pour cette tâche ou cet état.
     * 
     * @return Le temps estimé en double.
     */
    public Double getTemps() {
        return temps;
    }

    /**
     * Récupère le temps courant (timestamp) associé à cet état.
     * 
     * @return Le temps courant sous forme d'un long (généralement un timestamp).
     */
    public long getTempsCour() {
        return tempsCour;
    }

    /**
     * Récupère la case associée à cet état.
     * 
     * @return La case représentant la position actuelle du robot.
     */
    public Case getCaseAssociee() {
        return caseAssociee;
    }

    /**
     * Définit la case associée à cet état, c'est-à-dire la nouvelle position du robot.
     * 
     * @param caseAssociee La nouvelle case à associer au robot.
     */
    public void setCaseAssociee(Case caseAssociee) {
        this.caseAssociee = caseAssociee;
    }

    /**
     * Récupère la quantité d'eau restante dans le réservoir du robot.
     * 
     * @return La quantité d'eau restante dans le réservoir.
     */
    public int getReservoir() {
        return reservoir;
    }

    /**
     * Définit la quantité d'eau restante dans le réservoir du robot.
     * 
     * @param reservoir La nouvelle quantité d'eau à affecter au réservoir du robot.
     */
    public void setReservoir(int reservoir) {
        this.reservoir = reservoir;
    }
}
