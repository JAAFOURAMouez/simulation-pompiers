package robot;
import carte.*;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
/**
 * Classe représentant la recherche du chemin le plus court.
 */

public class RechercheChemin {
    private final Carte carte;  // Référence à la carte sur laquelle se fait la recherche de chemin

    // Constructeur qui initialise la carte
    public RechercheChemin(Carte carte) {
        this.carte = carte;
    }

    /**
     * Calcul du chemin optimal entre une case de départ et une case de destination.
     * Utilise une approche de Dijkstra pour déterminer le chemin le plus rapide.
     * 
     * @param depart      La case de départ
     * @param destination La case de destination
     * @param robot       Le robot qui effectue le déplacement
     * @return            Un objet ResultatChemin contenant le chemin et le temps nécessaire pour y parvenir
     */
    public ResultatChemin calculerCheminOptimal(Case depart, Case destination, Robot robot) {
        // Vérification que le robot peut se déplacer sur le terrain de la destination
        if (robot.peutSeDeplacerSur(destination.getNature())) { 
            // Dictionnaires pour stocker les distances et les prédécesseurs
            Map<Case, Double> distances = new HashMap<>();
            Map<Case, SimpleEntry<Case, Direction>> predecesseurs = new HashMap<>();
            // File de priorité pour choisir le prochain cas à traiter (minimum distance)
            PriorityQueue<Case> filePriorite = new PriorityQueue<>(Comparator.comparing(distances::get));

            // Initialisation de la distance de départ à 0
            distances.put(depart, 0.0);
            filePriorite.add(depart);

            // Algorithme de Dijkstra pour calculer les chemins
            while (!filePriorite.isEmpty()) {
                Case courant = filePriorite.poll();  // Récupère la case avec la plus petite distance

                // Si la case courante est la destination, le chemin a été trouvé
                if (courant.equals(destination)) {
                    return new ResultatChemin(reconstruireChemin(predecesseurs, depart, destination), distances.get(destination));
                }
                
                // Définir la vitesse du robot en fonction du terrain de la case courante
                robot.setVitesseSur(courant.getNature());

                // Parcours des voisins de la case courante dans toutes les directions possibles
                for (Direction direction : Direction.values()) {
                    // Vérifie si le voisin existe et si le robot peut se déplacer
                    if (carte.voisinExiste(courant, direction) && robot.getVitesse() != 0) {
                        Case voisin = carte.getVoisin(courant, direction);

                        // Calcul du temps nécessaire pour se déplacer vers le voisin
                        double tempsDeplacement = carte.getTailleCases() / (1000 * robot.getVitesse()); // Temps par heure
                        tempsDeplacement *= 3600; // Convertit en secondes
                        
                        // Calcul de la nouvelle distance vers le voisin
                        double nouvelleDistance = distances.get(courant) + tempsDeplacement;

                        // Si cette nouvelle distance est plus courte, on met à jour
                        if (nouvelleDistance < distances.getOrDefault(voisin, Double.POSITIVE_INFINITY)) {
                            distances.put(voisin, nouvelleDistance);
                            predecesseurs.put(voisin, new SimpleEntry<>(courant, direction));
                            filePriorite.add(voisin);  // Ajout du voisin à la file de priorité
                        }
                    }
                }
            }

            // Si aucun chemin n'a été trouvé, on retourne un chemin null avec une distance infinie
            return new ResultatChemin(null, Double.MAX_VALUE);
        } else {
            // Si le robot ne peut pas se déplacer sur le terrain de la destination, retourne un chemin invalide
            return new ResultatChemin(null, Double.MAX_VALUE);
        }
    }

    /**
     * Reconstruit le chemin optimal à partir des prédécesseurs stockés.
     * 
     * @param predecesseurs Les prédécesseurs de chaque case
     * @param depart        La case de départ
     * @param destination   La case de destination
     * @return              La liste des cases et directions formant le chemin
     */
    private List<SimpleEntry<Case, Direction>> reconstruireChemin(Map<Case, SimpleEntry<Case, Direction>> predecesseurs, Case depart, Case destination) {
        List<SimpleEntry<Case, Direction>> chemin = new ArrayList<>();
        Case courant = destination;
        chemin.add(new SimpleEntry<>(courant, null));

        // Remonte le chemin à partir de la destination en utilisant les prédécesseurs
        while (!courant.equals(depart)) {
            SimpleEntry<Case, Direction> entry = predecesseurs.get(courant);
            chemin.add(new SimpleEntry<>(entry.getKey(), entry.getValue()));
            courant = entry.getKey();  // Passe à la case précédente
        }
        
        // Inverse le chemin pour le donner dans l'ordre de départ à destination
        Collections.reverse(chemin);
        return chemin;
    }
}
