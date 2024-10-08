public class Incendie {
    private Case position;
    private int intensite;
    public Incendie(Case position, int intensite){
        this.position=position;
        this.intensite=intensite;
    }
    public void eteindre(int quantiteEau){
        if(quantiteEau>0){
            this.intensite-=quantiteEau;
            if(this.intensite<0){
                this.intensite=0;
            }
        }
    }
    public boolean estEteint(){
        return this.intensite==0;
    }
    public Case getPosition(){
        return position;
    }
    public int getIntensite(){
        return intensite;
    }
    public void setIntensite(int intensite){
        this.intensite=intensite;
    }
}
