package robot;

public abstract class Evenement implements Comparable<Evenement> {
    private final long date;

    public Evenement(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    

    @Override
    public int compareTo(Evenement other) {
        return Long.compare(this.date, other.date);
    }

    public abstract void execute();
}
