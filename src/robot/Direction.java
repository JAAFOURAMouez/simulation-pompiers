package robot;

/**
 * Enumération représentant les différentes directions possibles sur la carte.
 * Les directions sont définies par les points cardinaux :
 * <ul>
 * <li><b>NORD</b> : en haut, vers le nord.</li>
 * <li><b>SUD</b> : en bas, vers le sud.</li>
 * <li><b>EST</b> : à droite, vers l'est.</li>
 * <li><b>OUEST</b> : à gauche, vers l'ouest.</li>
 * </ul>
 */
public enum Direction {

    /**
     * Direction vers le nord (haut de la carte).
     */
    NORD, 

    /**
     * Direction vers le sud (bas de la carte).
     */
    SUD,  

    /**
     * Direction vers l'est (droite de la carte).
     */
    EST,  

    /**
     * Direction vers l'ouest (gauche de la carte).
     */
    OUEST 
}
