package robot;

import carte.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
public class ResultatChemin {
    private List<SimpleEntry<Case, Direction>> cheminOptimal;
    private double tempsTotal;

    public ResultatChemin(List<SimpleEntry<Case, Direction>> cheminOptimal, double tempsTotal) {
        this.cheminOptimal = cheminOptimal;
        this.tempsTotal = tempsTotal;
    }

    public List<SimpleEntry<Case, Direction>> getCheminOptimal() {
        return cheminOptimal;
    }

    public double getTempsTotal() {
        return tempsTotal;
    }
}
