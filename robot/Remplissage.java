package robot;

public class Remplissage extends Evenement
{

    public Robot robot;


    Remplissage(Robot robot,long date)
    {
        super(date);
        this.robot=robot;
    }



    @Override
    
    public void execute()
    {
        robot.remplirEau();

    }


}