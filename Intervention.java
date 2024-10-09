public class Intervention extends Evenement
{

    public Robot robot;
    public Incendie incendie;


    Intervention(Robot robot,Incendie incendie,long date)
    {
        super(date);
        this.robot=robot;
        this.incendie=incendie;
    }



    @Override
    
    public void execute()
    {
        robot.deverserEau(incendie.getIntensite());

    }


}