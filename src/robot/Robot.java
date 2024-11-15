package robot;

import carte.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import simulateur.Simulateur;

/**
 * Classe abstraite représentant un robot.
 * 
 * <p>Un robot est capable de se déplacer sur une carte, de remplir et vider son réservoir d'eau, 
 * et d'interagir avec le simulateur. Les robots spécifiques (par exemple, à roues, à chenilles, etc.) 
 * étendent cette classe et implémentent des comportements spécifiques selon le terrain et les capacités de 
 * chaque robot.</p>
 */
public abstract class Robot {

    private Carte carte;
    private Case position;
    private int reservoirEau;
    private Simulateur simulateur;
    protected double vitesse;
    protected double vitesseBase;

    /**
     * Constructeur du robot avec position, niveau d'eau et vitesse initiale.
     * 
     * @param position La position initiale du robot sur la carte.
     * @param reservoirEau Le niveau initial du réservoir d'eau du robot.
     * @param vitesse La vitesse initiale du robot.
     */
    public Robot(Case position, int reservoirEau, int vitesse) {
        this.position = position;
        this.reservoirEau = reservoirEau;
        this.vitesse = vitesse;
        this.vitesseBase = vitesse;
    }

    /**
     * Retourne la position actuelle du robot sur la carte.
     * 
     * @return La position du robot.
     */
    public Case getPosition() {
        return position;
    }

    /**
     * Retourne la vitesse actuelle du robot.
     * 
     * @return La vitesse du robot en km/h.
     */
    public double getVitesse() {
        return vitesse;
    }

    /**
     * Retourne la vitesse de base du robot (avant tout ajustement).
     * 
     * @return La vitesse de base du robot en km/h.
     */
    public double getVitesseBase() {
        return vitesseBase;
    }

    /**
     * Définit la carte sur laquelle le robot évolue.
     * 
     * @param carte La carte du terrain.
     */
    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    /**
     * Définit le simulateur associé au robot.
     * 
     * @param simulateur L'objet simulateur.
     */
    public void setSimulateur(Simulateur simulateur) {
        this.simulateur = simulateur;
    }

    /**
     * Modifie la vitesse du robot.
     * 
     * @param vitesse La nouvelle vitesse du robot.
     */
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * Modifie la position du robot.
     * 
     * @param pos La nouvelle position du robot.
     */
    public void setPosition(Case pos) {
        this.position = pos;
    }

    /**
     * Déverse une quantité d'eau du réservoir du robot.
     * 
     * @param vol Le volume d'eau à déverser.
     */
    public void deverserEau(int vol) {
        if (vol > reservoirEau) {
            reservoirEau = 0;
        } else {
            reservoirEau -= vol;
        }
    }

    /**
     * Retourne le niveau actuel du réservoir d'eau du robot.
     * 
     * @return Le niveau du réservoir en unités.
     */
    public int getNiveauReservoirEau() {
        return reservoirEau;
    }

    /**
     * Remplir le réservoir d'eau du robot avec un volume spécifié.
     * 
     * @param vol Le volume d'eau à ajouter au réservoir.
     * @throws IllegalArgumentException Si le volume d'eau est négatif.
     */
    public void remplirEau(int vol) {
        if (vol < 0) {
            throw new IllegalArgumentException("Le volume d'eau ne peut pas être négatif.");
        }
        int niv = this.getNiveauReservoirEau();
        if (niv + vol > this.getCapaciteMaxReservoir()) {
            this.setReservoirEau(this.getCapaciteMaxReservoir());
        } else {
            this.setReservoirEau(niv + vol);
        }
    }

    /**
     * Définit le niveau actuel du réservoir d'eau.
     * 
     * @param nivreservoir Le niveau du réservoir en unités.
     */
    public void setReservoirEau(int nivreservoir) {
        this.reservoirEau = nivreservoir;
    }

    /**
     * Initialise le robot avec une carte et un simulateur.
     * 
     * @param carte La carte du terrain sur lequel le robot évolue.
     * @param simulateur Le simulateur associé au robot.
     */
    public void initialiserRobot(Carte carte, Simulateur simulateur) {
        this.setCarte(carte);
        this.setSimulateur(simulateur);
    }

    /**
     * Déplace le robot dans une direction donnée, si possible.
     * 
     * @param direction La direction vers laquelle déplacer le robot.
     * @param carte La carte du terrain sur lequel le robot évolue.
     * @throws IllegalArgumentException Si le robot ne peut pas se déplacer sur le terrain ciblé.
     */
    public void deplacer(Direction direction, Carte carte) {
        Case nouvellePosition = carte.getVoisin(position, direction);
        if (peutSeDeplacerSur(nouvellePosition.getNature())) {
            this.position = nouvellePosition;
        } else {
            throw new IllegalArgumentException("Le robot ne peut pas se déplacer sur ce type de terrain.");
        }
    }

    /**
     * Déplace le robot vers une case de destination en calculant le chemin optimal et retourne le temps écoulé.
     * 
     * @param depart La case de départ du robot.
     * @param destination La case de destination.
     * @param t Le temps courant avant le déplacement.
     * @return Le temps écoulé pendant le déplacement, en secondes.
     */
    public long deplacerVersCase(Case depart, Case destination, long t) {
        RechercheChemin rechercheChemin = new RechercheChemin(carte);
        ResultatChemin resultat = rechercheChemin.calculerCheminOptimal(depart, destination, this);

        if (resultat.getCheminOptimal() == null) {
            System.out.println("Aucun chemin trouvé pour atteindre la destination.");
            return -1;
        }

        List<SimpleEntry<Case, Direction>> cheminOptimal = resultat.getCheminOptimal();
        long temps = t;
        double tempsDeplacement;

        // Programmer les événements de déplacement pour chaque étape du chemin
        for (SimpleEntry<Case, Direction> etape : cheminOptimal) {
            Direction direction = etape.getValue();
            if (direction != null) { // Ignorer la première case (départ)
                Deplacement deplacement = new Deplacement(carte, this, direction, temps);
                simulateur.ajouteEvenement(deplacement);
                tempsDeplacement = carte.getTailleCases() / (1000 * this.getVitesse()); // par heure
                tempsDeplacement *= 3600; // Convertir en secondes

                temps += (long) tempsDeplacement; // Incrémenter le temps pour chaque déplacement
            }
        }
        return temps - t;
    }

    // Méthodes abstraites : Chaque type de robot devra les implémenter

    /**
     * Retourne la capacité maximale du réservoir d'eau du robot.
     * 
     * @return La capacité maximale du réservoir en unités.
     */
    public abstract int getCapaciteMaxReservoir();

    /**
     * Détermine si le robot peut se déplacer sur un terrain donné.
     * 
     * @param terrain Le type de terrain.
     * @return true si le robot peut se déplacer sur ce terrain, false sinon.
     */
    public abstract boolean peutSeDeplacerSur(NatureTerrain terrain);

    /**
     * Ajuste la vitesse du robot en fonction du terrain sur lequel il se trouve.
     * 
     * @param terrain Le type de terrain sur lequel le robot se trouve.
     */
    public abstract void setVitesseSur(NatureTerrain terrain);

    /**
     * Retourne le type du robot sous forme de chaîne de caractères.
     * 
     * @return Le type du robot (par exemple, "RobotARoues", "RobotAChenilles").
     */
    public abstract String getType();

    /**
     * Calcule le temps nécessaire pour remplir le réservoir du robot avec un volume donné.
     * 
     * @param vol Le volume d'eau à ajouter au réservoir.
     * @return Le temps nécessaire pour remplir le réservoir en heures.
     */
    public abstract double getTempsRemplissage(int vol);
}
