
import java.util.LinkedList;
import java.util.Queue;


public abstract class Robot{
    private Carte carte;
    private Case position;
    private int reservoirEau;
    protected double vitesse;
    
    public Robot(Case position,int reservoirEau,int vitesse){
        this.position=position;
        this.reservoirEau=reservoirEau;
        this.vitesse=vitesse;
    }
    public Case getPosition(){
        return position;
    }
    public double getVitesse()
    {
        return vitesse;

    }

    public void setPosition(Case pos){
        this.position=pos;
    }
    void deverserEau(int vol){
        if (vol>reservoirEau)
            reservoirEau=0;
            
            
        else reservoirEau-=vol;
    }
    public int getNiveauReservoirEau(){
        return reservoirEau;
    }

    //implementation du BFS algorithm

   public Case trouverPositionProche() {
        // Directions possibles : haut, bas, gauche, droite
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int startX = position.getLigne();
        int startY = position.getColonne();

        Queue<Case> queue = new LinkedList<>();
        queue.add(position);

        // Tableau pour marquer les positions visitées
        boolean[][] visite = new boolean[carte.getNbLignes()][carte.getNbColonnes()];
        visite[startX][startY] = true;

        while (!queue.isEmpty()) {
            Case current = queue.poll();
            int x = current.getLigne();
            int y = current.getColonne();

            if (current.getNature() == NatureTerrain.EAU) {
                return current; 
            }

            // Explore les cases adjacentes
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (estValide(newX, newY) && !visite[newX][newY]) {
                    visite[newX][newY] = true;
                    Case voisin = carte.getCase(newX, newY);
                    queue.add(voisin);
                }
            }
        }

        // Aucun chemin trouvé qui respecte la condition
        return null;
    }

    // Vérifie si la position est valide dans la carte
    private boolean estValide(int x, int y) {
        return (x >= 0 && x < carte.getNbLignes() && y >= 0 && y < carte.getNbColonnes());
    }



    public void remplirEau(){
        Case position=trouverPositionProche();
        if (reservoirEau==0) //. Lorsqu’il est vide, il doit aller remplir
        {
            reservoirEau=getCapaciteMaxReservoir();

        }
        
    }
    public void deplacer(Direction direction,Carte carte){
        Case nouvellePosition=carte.getVoisin(position, direction);
        if (peutSeDeplacerSur(nouvellePosition.getNature()))
            this.position=nouvellePosition;
        else 
            throw new IllegalArgumentException("Le robot ne peut pas se déplacer sur ce type de terrain.");
    }

    public abstract int getCapaciteMaxReservoir();
    public abstract boolean peutSeDeplacerSur(NatureTerrain terrain);
    public abstract void setVitesseSur(NatureTerrain terrain);

}