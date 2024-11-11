package robot;
import carte.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import simulateur.*;


public class Strategiez {
    private List<Robot> robots;
    private List<Incendie> incendies;
    private List<Case> casesEau;
    public void chefPompier(DonneeSimulation donnes,Simulateur simulateur)
    {
        robots=donnes.getRobots();
        incendies=donnes.geIncendies();
        casesEau=donnes.getCasesEau();
        boolean remplir=false;
        long t = simulateur.getDateSimulation()-1;
        Map<Robot, EtatDetails> etat = new HashMap<>();
        for (Robot robot : robots) {
            etat.put(robot , new EtatDetails(0.0, robot.getPosition(), robot.getNiveauReservoirEau() ));
        }

        RechercheChemin r = new RechercheChemin(donnes.getCarte());
        for (Incendie incendie :incendies )
        {
        Robot eteindre = robots.get(0);
        Case plusProcheeau=new Case(0,0,NatureTerrain.EAU);
        Case destination=donnes.getCarte().getCase(incendie.getPosition().getLigne(),incendie.getPosition().getColonne());
        double temps = 0;
        double min=Double.MAX_VALUE;
        for (Robot robot : robots) {
            robot.setCarte(donnes.getCarte());
            robot.setSimulateur(simulateur);
            EtatDetails details = etat.get(robot);   
                
            //System.out.println(robot.getType());
            if (robot.getCapaciteMaxReservoir() < incendie.getIntensite()  )
            {
                continue;
            }
            Case depart= details.getCaseAssociee();
            if (details.getreservoir() >= incendie.getIntensite())
            {
                //On doit chercher le plus proche chemin vers l'eau pour remplir le reservoir et recalculer le temps necessaie
                ResultatChemin resultat=r.calculerCheminOptimal(depart,destination,robot);
                temps=resultat.getTempsTotal() + details.gettemps();
                //System.out.println(temps);
                if (temps < min)
                {
                    min =temps;
                    eteindre=robot;
                    remplir=false;
                }
            }
            else
            
            {       
                
                double minEau=Double.MAX_VALUE;
                double tempsEau=0;
                for (Case caseEau : casesEau)
                { 
                    Case destinationEau=donnes.getCarte().getCase(caseEau.getLigne(),caseEau.getColonne());
                    ResultatChemin resultat=r.calculerCheminOptimal(depart,destinationEau,robot); 
                    tempsEau=resultat.getTempsTotal();
                    
                    if (tempsEau < minEau )
                    {
                        
                        minEau =tempsEau;
                        plusProcheeau=destinationEau;
                    }
                }
                double tempsRemplissage=robot.getTempsTotal(incendie.getIntensite() - details.getreservoir());
                ResultatChemin resultatFeu=r.calculerCheminOptimal(plusProcheeau,destination,robot);
                double tempsFeu=resultatFeu.getTempsTotal();
                double tempsTotal=tempsFeu+tempsEau+tempsRemplissage+details.gettemps();
                if (tempsTotal < min)
                {
                    min =tempsTotal;
                    eteindre=robot;
                    remplir=true;
                }

  

            }


        }
        // System.out.println(plusProcheeau.getLigne()+ " " + plusProcheeau.getColonne());
        if (remplir)
        {
           // System.out.println(eteindre.getType());
            t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), plusProcheeau, t+1);
            Remplissage re = new Remplissage(eteindre,t, incendie.getIntensite() - etat.get(eteindre).getreservoir());
            etat.put(eteindre, new EtatDetails(min, plusProcheeau, incendie.getIntensite()));
            simulateur.ajouteEvenement(re);
            t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t+1);
            Intervention in = new Intervention(eteindre, incendie,t);
            etat.put(eteindre, new EtatDetails(min, destination, etat.get(eteindre).getreservoir() - incendie.getIntensite() ));
            simulateur.ajouteEvenement(in);
        }
        else
        {   
            t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t+1);
            Intervention in = new Intervention(eteindre, incendie, t);
            etat.put(eteindre, new EtatDetails(min, destination, etat.get(eteindre).getreservoir() - incendie.getIntensite() ));
            // System.out.println(destination.getLigne()+ " " + destination.getColonne());

            simulateur.ajouteEvenement(in);
        }
        
        }
        }
    }








