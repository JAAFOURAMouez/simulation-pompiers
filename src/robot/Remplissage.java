package robot;

public class Remplissage extends Evenement
{

    private final Robot robot;
    private final int vol;

    Remplissage(Robot robot,long date, int vol)
    {
        super(date);
        this.robot=robot;
        this.vol = vol;
    }



    @Override
    
    public void execute()
    {
        robot.remplirEau(vol);

    }


}