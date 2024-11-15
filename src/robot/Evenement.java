package robot;
/**
 * Classe abstraite représentant un événement.
 * Un événement est caractérisé par une date d'exécution et une action à effectuer (méthode execute).
 * Cette classe implémente l'interface Comparable pour permettre le tri des événements par date.
 */
public abstract class Evenement implements Comparable<Evenement> {
    private final long date;
    /**
     * Constructeur de la classe Evenement.
     * 
     * @param date La date (timestamp) à laquelle cet événement doit être exécuté.
     */
    public Evenement(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    /**
     * Méthode pour comparer deux événements par leur date.
     * Cette méthode est utilisée pour trier les événements dans une file d'attente, par exemple.
     * 
     * @param other L'autre événement à comparer.
     * @return Un entier négatif, zéro, ou un entier positif si la date de cet événement
     *         est respectivement antérieure, égale ou postérieure à celle de l'autre événement.
     */
    @Override
    public int compareTo(Evenement other) {
        return Long.compare(this.date, other.date);
    }

    public abstract void execute();
}
