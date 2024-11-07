package robot;
import carte.Incendie;

public class Intervention extends Evenement
{

    public Robot robot;
    public Incendie incendie;


    public Intervention(Robot robot,Incendie incendie,long date)
    {
        super(date);
        this.robot=robot;
        this.incendie=incendie;
    }



    @Override
    
    public void execute()
    {
        int vol=incendie.getIntensite();
        int reservoirEau=robot.getNiveauReservoirEau();
        robot.deverserEau(vol);

        incendie.eteindre(Math.min(reservoirEau,vol));

    }


}