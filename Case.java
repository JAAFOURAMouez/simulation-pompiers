public class Case {
    private int Ligne,Colonne;
    private NatureTerrain Nature;
    public Case(int Ligne,int Colonne,NatureTerrain Nature){
        this.Colonne=Colonne;
        this.Ligne=Ligne;
        this.Nature=Nature;
    }
    public NatureTerrain getNature(){
        return Nature;
    }

    public int getColonne() {
        return Colonne;
    }

    public int getLigne() {
        return Ligne;
    }
}
