public class Carte {
    private int TailleCases;
    private Case[][] cases;
    public Carte(int NbLignes,int NbColonnes){
        this.cases=new Case[NbLignes][NbColonnes];
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
    public Case getCase(int Ligne,int Colonne){
        return cases[Ligne][Colonne];
    }
    public boolean voisinExiste(Case src,Direction Dir){
            int ligne=src.getLigne();
            int colonne=src.getColonne();
            switch (Dir) {
                case NORD:
                    return ligne>0;
                case SUD:
                    return ligne<getNbLignes()-1;
                case OUEST:
                    return colonne<0;
                case EST:
                    return colonne<getNbColonnes()-1;
                default:
                    return false;
            }
    }
    public Case getVoisin(Case src,Direction dir){
        if (!voisinExiste(src, dir)) {
            throw new IllegalArgumentException("Aucun direction dans cette direction");
        }
        switch (dir) {
            case NORD:
                return getCase(src.getLigne()-1, src.getColonne());
            case SUD:
                return getCase(src.getLigne()+1, src.getColonne());
            case OUEST:
                return getCase(src.getLigne(), src.getColonne()-1);
            case EST:
                return getCase(src.getLigne(), src.getColonne()+1);
            default:
                return null;
        }
    }

}
