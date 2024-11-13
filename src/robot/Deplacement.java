package robot;
import carte.*;
public class Deplacement extends Evenement
{

    // classe qui gere le deplacement d'un robot selon la direction donnee

    protected Robot robot;
    public Direction direction;
    public Carte carte;


    public Deplacement(Carte carte,Robot robot,Direction direction,long date)
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
    @Override
    public String toString() {
        return "Deplacement Event [Robot: " + robot + ", Direction: " + direction + ", Date: " + getDate() + "]";
    }


}