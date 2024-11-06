package robot;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import carte.*;
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
