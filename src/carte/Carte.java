package carte;
import robot.Direction;
public class Carte {
    private int TailleCases;
    private static Case[][] cases;
    // Constructeur pour initialiser une carte avec un certain nombre de lignes et de colonnes
    public Carte(int NbLignes,int NbColonnes){
        Carte.cases=new Case[NbLignes][NbColonnes];
    }

    public int getNbLignes(){
        return cases.length;
    }
    public int getNbColonnes(){
        return cases[0].length;
    }
    public int getTailleCases() {
        return TailleCases;
    }
    public void setTailleCases(int taille){
        this.TailleCases=taille;
    }
    public Case getCase(int Ligne,int Colonne){
        return cases[Ligne][Colonne];
    }

    // Ajoute une nouvelle case à la position spécifiée par ses coordonnées (Ligne, Colonne)
    public void add_case(Case nouvellecase)
    {
        int i=nouvellecase.getLigne();
        int j=nouvellecase.getColonne();
        cases[i][j]=nouvellecase;
        
    }

    // Vérifie si un voisin existe dans une direction donnée par rapport à une case source
    public boolean voisinExiste(Case src,Direction Dir){
            int ligne=src.getLigne();
            int colonne=src.getColonne();
        return switch (Dir) {
            case NORD -> ligne>0;
            case SUD -> ligne<(getNbLignes()-1);
            case OUEST -> colonne > 0;
            case EST -> colonne<(getNbColonnes()-1);
            default -> false;
        };
    }

    // Retourne la case voisine dans une direction donnée par rapport à une case source
    public Case getVoisin(Case src,Direction dir){
        // Vérifie d'abord si un voisin existe dans cette direction
        if (!voisinExiste(src, dir)) {
            throw new IllegalArgumentException("Aucun voisin dans cette direction");
        }
        // Renvoie la case voisine en fonction de la direction
        return switch (dir) {
            case NORD -> getCase(src.getLigne()-1, src.getColonne()); // Voisin en haut
            case SUD -> getCase(src.getLigne()+1, src.getColonne());// Voisin en bas
            case OUEST -> getCase(src.getLigne(), src.getColonne()-1);// Voisin à gauche
            case EST -> getCase(src.getLigne(), src.getColonne()+1);// Voisin à droite
            default -> null;// Par défaut, retourne null (ne devrait pas arriver)
        };
    }

}
