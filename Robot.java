
import java.util.*;

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

   public List<Case> trouverCheminVersEau() {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int startX = position.getLigne();
        int startY = position.getColonne();

        Queue<Case> queue = new LinkedList<>();
        Map<Case, Case> precedents = new HashMap<>();
        queue.add(position);
        precedents.put(position, null);

        boolean[][] visite = new boolean[carte.getNbLignes()][carte.getNbColonnes()];
        visite[startX][startY] = true;

        Case destination = null;

        // Parcours BFS
        while (!queue.isEmpty()) {
            Case current = queue.poll();
            int x = current.getLigne();
            int y = current.getColonne();

            // Si on trouve une case avec de l'eau, on arrête la recherche
            if (current.getNature() == NatureTerrain.EAU) {
                destination = current;
                break;
            }

            // Explore les cases adjacentes
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (estValide(newX, newY) && !visite[newX][newY]) {
                    Case voisin = carte.getCase(newX, newY);
                    visite[newX][newY] = true;
                    queue.add(voisin);
                    precedents.put(voisin, current);
                }
            }
        }

        // Si aucune case contenant de l'eau n'a été trouvée, retourner null
        if (destination == null) {
            return null;
        }

        // Reconstruire le chemin à partir de la destination
        List<Case> chemin = new ArrayList<>();
        for (Case at = destination; at != null; at = precedents.get(at)) {
            chemin.add(at);
        }
        Collections.reverse(chemin); // Le chemin est reconstruit en partant de la destination, il faut le renverser

        return chemin;
    }
    // Vérifie si la position est valide dans la carte
    private boolean estValide(int x, int y) {
        return (x >= 0 && x < carte.getNbLignes() && y >= 0 && y < carte.getNbColonnes());
    }



    public void remplirEau() {
        List<Case> chemin = trouverCheminVersEau();
        if (chemin == null) {
            System.out.println("Aucune source d'eau trouvée.");
            reservoirEau = 0;
        } else {
            for (int i = 1; i < chemin.size(); i++) {
                Case prochainePosition = chemin.get(i);
                Direction direction = determinerDirection(position, prochainePosition);
                deplacer(direction, carte);
            }
            if (position.getNature() == NatureTerrain.EAU && reservoirEau == 0) {
                reservoirEau = getCapaciteMaxReservoir();
            }
        }
    }

    private Direction determinerDirection(Case actuelle, Case prochaine) {
        int dx = prochaine.getLigne() - actuelle.getLigne();
        int dy = prochaine.getColonne() - actuelle.getColonne();

        if (dx == -1) return Direction.NORD;
        if (dx == 1) return Direction.SUD;
        if (dy == -1) return Direction.EST;
        if (dy == 1) return Direction.OUEST;

        throw new IllegalArgumentException("La direction est invalide.");
    }

        

        
    
    public void deplacer(Direction direction,Carte carte){

        Case nouvellePosition=carte.getVoisin(position, direction);
        if (peutSeDeplacerSur(nouvellePosition.getNature()))
        {

        
            this.position=nouvellePosition;
        }
        else {

        
            throw new IllegalArgumentException("Le robot ne peut pas se déplacer sur ce type de terrain.");
        }

    }

    public abstract int getCapaciteMaxReservoir();
    public abstract boolean peutSeDeplacerSur(NatureTerrain terrain);
    public abstract void setVitesseSur(NatureTerrain terrain);

}