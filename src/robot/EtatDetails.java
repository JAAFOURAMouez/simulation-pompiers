package robot;
import carte.*;

public class EtatDetails {
    private Double temps;
    private Case caseAssociee;
    private int reservoir;

    public EtatDetails(Double temps, Case caseAssociee, int reservoir) {
        this.temps = temps;
        this.caseAssociee = caseAssociee;
        this.reservoir = reservoir;
    }

    public Double gettemps() {
        return temps;
    }

    public void settemps(Double temps) {
        this.temps = temps;
    }

    public Case getCaseAssociee() {
        return caseAssociee;
    }

    public void setCaseAssociee(Case caseAssociee) {
        this.caseAssociee = caseAssociee;
    }

    public int getreservoir() {
        return reservoir;
    }

    public void setreservoir(int reservoir) {
        this.reservoir = reservoir;
    }
}
