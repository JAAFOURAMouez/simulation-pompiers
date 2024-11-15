package carte;

import robot.Direction;

/**
 * Classe représentant une carte composée de cases.
 * La carte est un tableau de cases organisées en lignes et colonnes.
 */
public class Carte {
    
    private int TailleCases;
    private static Case[][] cases;

    /**
     * Constructeur de la classe Carte.
     * Initialise la carte avec un nombre spécifique de lignes et de colonnes.
     *
     * @param NbLignes Le nombre de lignes de la carte.
     * @param NbColonnes Le nombre de colonnes de la carte.
     */
    public Carte(int NbLignes, int NbColonnes){
        Carte.cases = new Case[NbLignes][NbColonnes];
    }

    /**
     * Récupère le nombre de lignes de la carte.
     *
     * @return Le nombre de lignes de la carte.
     */
    public int getNbLignes(){
        return cases.length;
    }

    /**
     * Récupère le nombre de colonnes de la carte.
     *
     * @return Le nombre de colonnes de la carte.
     */
    public int getNbColonnes(){
        return cases[0].length;
    }

    /**
     * Récupère la taille des cases de la carte.
     *
     * @return La taille des cases.
     */
    public int getTailleCases() {
        return TailleCases;
    }

    /**
     * Définit la taille des cases de la carte.
     *
     * @param taille La taille des cases.
     */
    public void setTailleCases(int taille){
        this.TailleCases = taille;
    }

    /**
     * Récupère la case située à la position spécifiée par ses coordonnées.
     *
     * @param Ligne La ligne de la case.
     * @param Colonne La colonne de la case.
     * @return La case située aux coordonnées spécifiées.
     */
    public Case getCase(int Ligne, int Colonne){
        return cases[Ligne][Colonne];
    }

    /**
     * Ajoute une nouvelle case à la position spécifiée dans la carte.
     *
     * @param nouvellecase La case à ajouter à la carte.
     */
    public void add_case(Case nouvellecase){
        int i = nouvellecase.getLigne();
        int j = nouvellecase.getColonne();
        cases[i][j] = nouvellecase;
    }

    /**
     * Vérifie si un voisin existe dans une direction donnée par rapport à une case source.
     * Cette méthode détermine si une case voisine existe dans la direction spécifiée
     * à partir de la case source.
     *
     * @param src La case source à partir de laquelle vérifier l'existence d'un voisin.
     * @param Dir La direction dans laquelle vérifier l'existence du voisin (NORD, SUD, OUEST, EST).
     * @return true si un voisin existe dans la direction spécifiée, sinon false.
     */
    public boolean voisinExiste(Case src, Direction Dir){
        int ligne = src.getLigne();
        int colonne = src.getColonne();
        return switch (Dir) {
            case NORD -> ligne > 0;
            case SUD -> ligne < (getNbLignes() - 1);
            case OUEST -> colonne > 0;
            case EST -> colonne < (getNbColonnes() - 1);
            default -> false;
        };
    }

    /**
     * Récupère la case voisine dans une direction donnée par rapport à une case source.
     * Si un voisin existe dans la direction spécifiée, la méthode retourne la case voisine.
     * Si aucun voisin n'existe dans la direction spécifiée, une exception est lancée.
     *
     * @param src La case source à partir de laquelle récupérer le voisin.
     * @param dir La direction dans laquelle chercher le voisin (NORD, SUD, OUEST, EST).
     * @return La case voisine dans la direction spécifiée.
     * @throws IllegalArgumentException Si aucun voisin n'existe dans la direction donnée.
     */
    public Case getVoisin(Case src, Direction dir){
        // Vérifie d'abord si un voisin existe dans cette direction
        if (!voisinExiste(src, dir)) {
            throw new IllegalArgumentException("Aucun voisin dans cette direction");
        }
        // Renvoie la case voisine en fonction de la direction
        return switch (dir) {
            case NORD -> getCase(src.getLigne() - 1, src.getColonne()); // Voisin en haut
            case SUD -> getCase(src.getLigne() + 1, src.getColonne()); // Voisin en bas
            case OUEST -> getCase(src.getLigne(), src.getColonne() - 1); // Voisin à gauche
            case EST -> getCase(src.getLigne(), src.getColonne() + 1); // Voisin à droite
            default -> null; // Par défaut, retourne null (ne devrait pas arriver)
        };
    }
}
