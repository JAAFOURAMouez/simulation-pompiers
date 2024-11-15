package carte;

public class Case {
    private int Ligne,Colonne;
    private NatureTerrain Nature;// Type de terrain de la case (ex: EAU,FORET,ROCHE,TERRAIN_LIBRE,HABITAT)

    // Constructeur pour initialiser une case avec sa position (ligne, colonne) et sa nature de terrain
    public Case(int Ligne,int Colonne,NatureTerrain Nature){
        this.Colonne=Colonne;
        this.Ligne=Ligne;
        this.Nature=Nature;
    }

    public NatureTerrain getNature(){
        return Nature;
    }

    public int getColonne(){
        return Colonne;
    }

    public int getLigne(){
        return Ligne;
    }

    public void setNature(NatureTerrain terrain){
        this.Nature=terrain;
    }

    public void setColonne(int colonne){
        this.Colonne = colonne;
    }

    public void setLigne(int ligne){
        this.Ligne = ligne;
    }
}
