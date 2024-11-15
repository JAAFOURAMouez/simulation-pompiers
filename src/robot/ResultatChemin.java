package robot;

import carte.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
public class ResultatChemin {
    private final List<SimpleEntry<Case, Direction>> cheminOptimal;
    private final double tempsTotal;
    
    // Constructeur qui prend en paramètre le chemin optimal et le temps total
    public ResultatChemin(List<SimpleEntry<Case, Direction>> cheminOptimal, double tempsTotal) {
        this.cheminOptimal = cheminOptimal;
        this.tempsTotal = tempsTotal;
    }

    public List<SimpleEntry<Case, Direction>> getCheminOptimal() {
        return cheminOptimal;
    }

    public double getTempsTotale() {
        return tempsTotal;
    }
}
