package robot;
import carte.*;

public class EtatDetails {
    private final Double temps;
    private Case caseAssociee;
    private int reservoir;
    private final long tempsCour;
    /**
     * Constructeur de la classe EtatDetails.
     * 
     * @param temps         Temps estimé en double (peut représenter le temps de déplacement ou d'intervention)
     * @param caseAssociee  La case associée à cet état (position actuelle du robot)
     * @param reservoir     La quantité d'eau restante dans le réservoir du robot
     * @param tempsCour     Temps courant (timestamp pour le suivi des événements)
     */
    public EtatDetails(Double temps, Case caseAssociee, int reservoir, long tempsCour) {
        this.temps = temps;
        this.caseAssociee = caseAssociee;
        this.reservoir = reservoir;
        this.tempsCour = tempsCour;

    }

    public Double getTemps() {
        return temps;
    }
    
    public long getTempsCour() {
        return tempsCour;
    }

    public Case getCaseAssociee() {
        return caseAssociee;
    }

    public void setCaseAssociee(Case caseAssociee) {
        this.caseAssociee = caseAssociee;
    }

    public int getReservoir() {
        return reservoir;
    }

    public void setReservoir(int reservoir) {
        this.reservoir = reservoir;
    }
}
