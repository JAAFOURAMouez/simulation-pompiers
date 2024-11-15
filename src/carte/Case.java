package carte;

/**
 * Classe représentant une case de la carte.
 * Chaque case possède une position (ligne, colonne) et un type de terrain (NatureTerrain).
 */
public class Case {
    
    private int Ligne, Colonne;
    private NatureTerrain Nature; // Type de terrain de la case (ex: EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT)

    /**
     * Constructeur pour initialiser une case avec sa position et sa nature de terrain.
     *
     * @param Ligne La ligne de la case.
     * @param Colonne La colonne de la case.
     * @param Nature Le type de terrain de la case (ex: EAU, FORET, ROCHE, etc.).
     */
    public Case(int Ligne, int Colonne, NatureTerrain Nature){
        this.Colonne = Colonne;
        this.Ligne = Ligne;
        this.Nature = Nature;
    }

    /**
     * Récupère le type de terrain (NatureTerrain) de cette case.
     *
     * @return Le type de terrain de la case.
     */
    public NatureTerrain getNature(){
        return Nature;
    }

    /**
     * Récupère la colonne de cette case.
     *
     * @return La colonne de la case.
     */
    public int getColonne(){
        return Colonne;
    }

    /**
     * Récupère la ligne de cette case.
     *
     * @return La ligne de la case.
     */
    public int getLigne(){
        return Ligne;
    }

    /**
     * Définit le type de terrain de cette case.
     *
     * @param terrain Le type de terrain à affecter à la case (ex: EAU, FORET, ROCHE, etc.).
     */
    public void setNature(NatureTerrain terrain){
        this.Nature = terrain;
    }

    /**
     * Définit la colonne de cette case.
     *
     * @param colonne La nouvelle colonne de la case.
     */
    public void setColonne(int colonne){
        this.Colonne = colonne;
    }

    /**
     * Définit la ligne de cette case.
     *
     * @param ligne La nouvelle ligne de la case.
     */
    public void setLigne(int ligne){
        this.Ligne = ligne;
    }
}
