public class Deplacement extends Evenement
{

    // classe qui gere le deplacement d'un robot selon la direction donnee

    public Robot robot;
    public Direction direction;
    public Carte carte;


    Deplacement(Carte carte,Robot robot,Direction direction,long date)
    {
        super(date);
        this.robot=robot;
        this.direction=direction;
        this.carte=carte;
    }




    @Override
    
    public void execute()
    {

        robot.deplacer(direction, carte);

    }


}