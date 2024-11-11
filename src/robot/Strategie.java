package robot;
import carte.*;
import simulateur.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import javax.swing.CellRendererPane;


public class Strategie {
    private List<Robot> robots;
    private List<Incendie> incendies;
    private List<Case> casesEau;
    void chefPompier(DonneeSimulation donnes)
    {
        robots=donnes.getRobots();
        incendies=donnes.geIncendies();
        casesEau=donnes.getCasesEau();
        

        RechercheChemin r = new RechercheChemin(donnes.getCarte());
        for (Incendie incendie : incendies)
        {
            Robot eteindre = robots.get(0);

            for (Robot robot : robots) {
                double min=Double.MAX_VALUE;
                if (robot.getCapaciteMaxReservoir() < incendie.getIntensite())
                {
                    continue;
                }
                Case depart=robot.getPosition();
                if (robot.getNiveauReservoirEau() >= incendie.getIntensite())
                {
                    //On doit chercher le plus proche chemin vers l'eau pour remplir le reservoir et recalculer le temps necessaie
                    Case destination=incendie.getPosition();

                    ResultatChemin resultat=r.calculerCheminOptimal(depart,destination,robot);
                    double temps=resultat.getTempsTotal();

                    if (temps < min)
                    {
                        min =temps;
                        eteindre=robot;

                    }

                }
                else
                {   

                    double minEau=Double.MAX_VALUE;
                    Case plusProcheeau=new Case(0,0,NatureTerrain.EAU);
                    double tempsEau=0;
                    for (Case caseEau : casesEau)
                    {

                    
                    Case destinationEau=caseEau;
                    ResultatChemin resultat=r.calculerCheminOptimal(depart,destinationEau,robot); 
                    tempsEau=resultat.getTempsTotal();
                    if (tempsEau < minEau )
                    {
                        minEau =tempsEau;
                        plusProcheeau=destinationEau;
                    }
                    }
                    Case destinationFeu=incendie.getPosition();

                    ResultatChemin resultatFeu=r.calculerCheminOptimal(depart,destinationFeu,robot);
                    double tempsFeu=resultatFeu.getTempsTotal();
                    double tempsTotal=tempsFeu+tempsEau;
                    if (tempsTotal < min)
                    {
                        min =tempsTotal;
                        eteindre=robot;
                    }



  

                }

            }

            }
        }

    }


    



