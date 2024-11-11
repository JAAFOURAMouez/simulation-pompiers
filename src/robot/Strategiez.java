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
        // initialiser une structure qui prend liste des robots et voir s ils sont occupes ou pas
        Map<Robot, EtatDetails> etat = new HashMap<>();
        for (Robot robot : robots) {
            etat.put(robot , new EtatDetails(0.0, robot.getPosition(), robot.getNiveauReservoirEau() ));
        }
        //le max d intencite pour remplir le max
        int intesnsiteMAX=0;
        for(Incendie inc:incendies){
            if(intesnsiteMAX<inc.getIntensite()){
                intesnsiteMAX=inc.getIntensite();  
            }
        }
        RechercheChemin r = new RechercheChemin(donnes.getCarte());
        for (int i = 0; i < incendies.size(); i++) {
            {
            Robot eteindre = robots.get(0);
            Case plusProcheeau=new Case(0,0,NatureTerrain.EAU);
            //destination vers le feu
            Case destination=donnes.getCarte().getCase(incendies.get(i).getPosition().getLigne(),incendies.get(i).getPosition().getColonne());
            double temps = 0;
            double min=Double.MAX_VALUE;
            for (Robot robot : robots) {
                robot.setCarte(donnes.getCarte());
                robot.setSimulateur(simulateur);
                EtatDetails details = etat.get(robot);   
                    
                //System.out.println(robot.getType());
                if (robot.getCapaciteMaxReservoir() < incendies.get(i).getIntensite())
                {
                    // a modifier
                    continue;
                }
                double minEau=Double.MAX_VALUE;
                double tempsEau=0;
                Case depart= details.getCaseAssociee();

                for (Case caseEau : casesEau){
                    Case destinationEau=donnes.getCarte().getCase(caseEau.getLigne(),caseEau.getColonne());
                    if (robot.getType()!="Drone "|| robot.getType()!="RobotAPattes")
                    {
                        
                        double minVoisin=Double.MAX_VALUE;
                        Case destinVoisine=new Case(0, 0, NatureTerrain.TERRAIN_LIBRE);
                        Case voisinEst=donnes.getCarte().getVoisin(destinationEau, Direction.EST);
                        if (robot.peutSeDeplacerSur(voisinEst.getNature()) )
                        {
                            System.out.println("est");
                            ResultatChemin resultatE=r.calculerCheminOptimal(depart,voisinEst,robot); 
                            if(minVoisin> resultatE.getTempsTotal()){
                                System.err.println("minest");
                                minVoisin=resultatE.getTempsTotal();
                                destinVoisine.setColonne(voisinEst.getColonne());
                                destinVoisine.setLigne(  voisinEst.getLigne());
                                destinVoisine.setNature( voisinEst.getNature());
                            }      
                        }
                        Case voisinOuest=donnes.getCarte().getVoisin(destinationEau, Direction.OUEST);
                        if (robot.peutSeDeplacerSur(voisinOuest.getNature()))
                        {
                            System.out.println("ouest");
                            ResultatChemin resultatO=r.calculerCheminOptimal(depart,voisinOuest,robot); 
                            if(minVoisin> resultatO.getTempsTotal()){
                                System.err.println("minouest");
                                minVoisin=resultatO.getTempsTotal();
                                destinVoisine.setColonne(voisinOuest.getColonne());
                                destinVoisine.setLigne(voisinOuest.getLigne());
                                destinVoisine.setNature(voisinOuest.getNature());
                            }      
                        }
                        Case voisinNord=donnes.getCarte().getVoisin(destinationEau, Direction.NORD);
                        if (robot.peutSeDeplacerSur(voisinNord.getNature()) )
                        {
                            System.out.println(voisinNord.getLigne());
                            System.out.println("nord");
                            ResultatChemin resultatN=r.calculerCheminOptimal(depart,voisinNord,robot); 
                            if(minVoisin> resultatN.getTempsTotal()){
                                System.err.println("minnord");
                                minVoisin=resultatN.getTempsTotal();
                                destinVoisine.setColonne(voisinNord.getColonne());
                                destinVoisine.setLigne(  voisinNord.getLigne());
                                destinVoisine.setNature( voisinNord.getNature());
                            }
                        }
                        Case voisinSud=donnes.getCarte().getVoisin(destinationEau, Direction.SUD);
                        if (robot.peutSeDeplacerSur(voisinSud.getNature()) )
                        {
                            System.out.println("sud");
                            ResultatChemin resultatS=r.calculerCheminOptimal(depart,voisinSud,robot); 
                            if(minVoisin> resultatS.getTempsTotal()){
                                minVoisin=resultatS.getTempsTotal();
                                destinVoisine.setColonne(voisinSud.getColonne());
                                destinVoisine.setLigne(  voisinSud.getLigne());
                                destinVoisine.setNature( voisinSud.getNature());
                            }
                        }
                        System.out.println("ligne" +destinVoisine.getLigne());
                        System.out.println("colonne" +destinVoisine.getColonne());

                        ResultatChemin resultat=r.calculerCheminOptimal(depart,destinVoisine,robot); 
                        tempsEau=resultat.getTempsTotal();
                        System.out.println(tempsEau);
                        if (tempsEau < minEau )
                        {
                            minEau =tempsEau;
                            plusProcheeau=destinVoisine;
                        }
                    }else{
                        System.out.println("ligne" +destinationEau.getLigne()+ "colonne" +destinationEau.getColonne());
                        ResultatChemin resultat=r.calculerCheminOptimal(depart,destinationEau,robot); 
                        tempsEau=resultat.getTempsTotal();
                        System.out.println(tempsEau);
                        if (tempsEau < minEau )
                        {
                            
                            minEau =tempsEau;
                            plusProcheeau=destinationEau;
                        }
                    }
                }
                
                double tempsRemplissage=robot.getTempsTotal(Math.min(intesnsiteMAX, robot.getCapaciteMaxReservoir())-robot.getNiveauReservoirEau());
                ResultatChemin resultatFeu=r.calculerCheminOptimal(plusProcheeau,destination,robot);
                double tempsFeu=resultatFeu.getTempsTotal();
                System.out.println("feu"+tempsFeu);
                System.out.println("eau"+tempsEau);
                System.out.println("remp"+tempsRemplissage);
                System.out.println(details.gettemps());
                double tempsTotal=tempsFeu+tempsEau;
                System.out.println(tempsTotal);
                tempsTotal+=tempsRemplissage+details.gettemps();
                System.out.println(tempsTotal);
                if (tempsTotal < min)
                {
                    min =tempsTotal;
                    eteindre=robot;
                    remplir=true;
                }               
                if (details.getreservoir() >= incendies.get(i).getIntensite())
                {
                    //On doit chercher le plus proche chemin vers l'eau pour remplir le reservoir et recalculer le temps necessaie
                    ResultatChemin resultat=r.calculerCheminOptimal(depart,destination,robot);
                    temps=resultat.getTempsTotal() + details.gettemps();
                    System.out.println(temps);
                    if (temps < min)
                    {
                        min =temps;
                        eteindre=robot;
                        remplir=false;
                    }
                }
            }
            System.err.println(min);
            // System.out.println(plusProcheeau.getLigne()+ " " + plusProcheeau.getColonne());
            Case case1=new Case(0, 3, NatureTerrain.TERRAIN_LIBRE);
            if (remplir)
            {
                System.out.println("yess");
            // System.out.println(eteindre.getType());
                t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), plusProcheeau, t+1);
                Remplissage re = new Remplissage(eteindre,t, Math.min(intesnsiteMAX, eteindre.getCapaciteMaxReservoir()));
                etat.put(eteindre, new EtatDetails(min, plusProcheeau, Math.min(intesnsiteMAX, eteindre.getCapaciteMaxReservoir())));
                simulateur.ajouteEvenement(re);
                t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t+1);
                Intervention in = new Intervention(eteindre, incendies.get(i),t);
                etat.put(eteindre, new EtatDetails(min, destination, etat.get(eteindre).getreservoir() - incendies.get(i).getIntensite() ));
                simulateur.ajouteEvenement(in);
            }
            else
            {
                t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t+1);
                Intervention in = new Intervention(eteindre, incendies.get(i), t);
                etat.put(eteindre, new EtatDetails(min, destination, etat.get(eteindre).getreservoir() - incendies.get(i).getIntensite() ));
                // System.out.println(destination.getLigne()+ " " + destination.getColonne());
                simulateur.ajouteEvenement(in);
            }   
        }
        }
    }
}







