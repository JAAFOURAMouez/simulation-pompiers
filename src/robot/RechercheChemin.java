package robot;
import carte.*;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class RechercheChemin {
    private Carte carte;

    public RechercheChemin(Carte carte) {
        this.carte = carte;
    }

    public ResultatChemin calculerCheminOptimal(Case depart, Case destination, Robot robot) {
        if(robot.peutSeDeplacerSur(destination.getNature())){ 
        Map<Case, Double> distances = new HashMap<>();
        Map<Case, SimpleEntry<Case, Direction>> predecesseurs = new HashMap<>();
        PriorityQueue<Case> filePriorite = new PriorityQueue<>(Comparator.comparing(distances::get));

        distances.put(depart, 0.0);
        filePriorite.add(depart);

        while (!filePriorite.isEmpty()) {
            Case courant = filePriorite.poll();

            if (courant.equals(destination)) {
                return new ResultatChemin(reconstruireChemin(predecesseurs, depart, destination), distances.get(destination));
            }
            robot.setVitesseSur(courant.getNature());

            for (Direction direction : Direction.values()) {
                if (carte.voisinExiste(courant, direction) && robot.getVitesse() != 0) {
                    Case voisin = carte.getVoisin(courant, direction);

                    double tempsDeplacement = carte.getTailleCases() / (1000 *robot.getVitesse());//par h
                    tempsDeplacement*=3600;//par s
                    double nouvelleDistance = distances.get(courant) + tempsDeplacement;

                    if (nouvelleDistance < distances.getOrDefault(voisin, Double.POSITIVE_INFINITY)) {
                        distances.put(voisin, nouvelleDistance);
                        predecesseurs.put(voisin, new SimpleEntry<>(courant, direction));
                        filePriorite.add(voisin);
                    }
                }
            }
        }
        return new ResultatChemin(null, Double.MAX_VALUE);// Aucun chemin trouve
     } 
     else {return new ResultatChemin(null, Double.MAX_VALUE);}
    }

    private List<SimpleEntry<Case, Direction>> reconstruireChemin(Map<Case, SimpleEntry<Case, Direction>> predecesseurs, Case depart, Case destination) {
        List<SimpleEntry<Case, Direction>> chemin = new ArrayList<>();
        Case courant = destination;
        chemin.add(new SimpleEntry<>(courant, null));
        while (!courant.equals(depart)) {
            SimpleEntry<Case, Direction> entry = predecesseurs.get(courant);
            chemin.add(new SimpleEntry<>(entry.getKey(), entry.getValue()));
            courant = entry.getKey();
        }
        Collections.reverse(chemin);
        return chemin;
    }
}
