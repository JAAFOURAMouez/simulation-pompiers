package robot;
import carte.*;
import java.awt.image.RescaleOp;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;
import javax.print.DocFlavor;
import javax.print.attribute.standard.Destination;
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
        long tempsGlobal = 0;
        // initialiser une structure qui prend liste des robots et voir s ils sont occupes ou pas
        Map<Robot, EtatDetails> etat = new HashMap<>();
        for (Robot robot : robots) {
            etat.put(robot , new EtatDetails(0.0, robot.getPosition(), robot.getNiveauReservoirEau(), 0));
        }
        RechercheChemin r = new RechercheChemin(donnes.getCarte());
        for (int i = 0; i < incendies.size(); i++) {
            Robot eteindre = robots.get(0);
            //destination vers le feu
            Case destination=donnes.getCarte().getCase(incendies.get(i).getPosition().getLigne(),incendies.get(i).getPosition().getColonne());
            double temps ;
            double min=Double.MAX_VALUE;
            int nbAllerRetour ;
            int nbfinal = 0 ;
            Case plusProcheeau  =new Case(0,0,NatureTerrain.EAU);
            Case plusProcheeaufeu  =new Case(0,0,NatureTerrain.EAU);


            for (Robot robot : robots) {
                robot.setCarte(donnes.getCarte());
                robot.setSimulateur(simulateur);
                EtatDetails details = etat.get(robot);   

                Case depart= details.getCaseAssociee();

                if (details.getreservoir() >= incendies.get(i).getIntensite())
                {
                    //On doit chercher le plus proche chemin vers l'eau pour remplir le reservoir et recalculer le temps necessaie
                    ResultatChemin resultat=r.calculerCheminOptimal(depart,destination,robot);
                    int volIntervention=Math.min(details.getreservoir() , incendies.get(i).getIntensite());
                    temps=resultat.getTempsTotal() + details.gettemps()+incendies.get(i).tempsIntervention(robot, volIntervention);
                    System.out.println(temps);
                    if (temps < min){
                        min =temps;
                        eteindre=robot;
                        remplir=false;
                    }
                }else{
                    int volIntervention=Math.min(details.getreservoir() , incendies.get(i).getIntensite());
                    plusProcheeau=plusProche(donnes, depart, robot, casesEau).getKey();
                    Double minEau=plusProche(donnes, depart, robot, casesEau).getValue();
                    //plus cours chemin du robot a feu
                    ResultatChemin resultatFeu=r.calculerCheminOptimal(plusProcheeau,destination,robot);

                    double tempsRemplissage=robot.getTempsTotal(robot.getCapaciteMaxReservoir()-details.getreservoir());
                    //ALLER DE LA POSITION INIT DE L EAU SUR LE TRAJET VERS LE FEU
                    double tempsFeu=resultatFeu.getTempsTotal();        
                    nbAllerRetour = (int) Math.ceil((double) incendies.get(i).getIntensite() / robot.getCapaciteMaxReservoir());
                    double tempsTotal=minEau+tempsRemplissage+tempsFeu+details.gettemps()+incendies.get(i).tempsIntervention(robot, volIntervention);
                    if(nbAllerRetour>1){
                        plusProcheeaufeu=plusProche(donnes, incendies.get(i).getPosition(), robot, casesEau).getKey();
                        System.out.println(plusProcheeaufeu.getColonne()+" "+plusProcheeaufeu.getLigne());
                        //ALLER DE LA POSITION DE L'EAU VERS LE FEU
                        Double minEauFeu=plusProche(donnes, incendies.get(i).getPosition(), robot, casesEau).getValue();
                        tempsTotal+=((2*minEauFeu+tempsRemplissage)*(nbAllerRetour-1));
                    }
                    if (tempsTotal < min){
                        min =tempsTotal;
                        eteindre=robot;
                        remplir=true;
                        nbfinal = nbAllerRetour;
                    }               
                }
            }
            // System.out.println(plusProcheeau.getLigne()+ " " + plusProcheeau.getColonne());
            t = etat.get(eteindre).gettempsCour();
            if (remplir){

                    // System.out.println(eteindre.getType());
                    t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), plusProcheeau, t+1);
                    Remplissage re = new Remplissage(eteindre,t, incendies.get(i).getIntensite() - etat.get(eteindre).getreservoir());
                    etat.put(eteindre, new EtatDetails(min, plusProcheeau, Math.min(incendies.get(i).getIntensite(),eteindre.getCapaciteMaxReservoir()),t));
                    simulateur.ajouteEvenement(re);
                    t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t+1);
                    Intervention in = new Intervention(eteindre, incendies.get(i),t);
                    etat.put(eteindre, new EtatDetails(min, destination, etat.get(eteindre).getreservoir() - Math.min(incendies.get(i).getIntensite(),eteindre.getCapaciteMaxReservoir()) ,t ));
                    simulateur.ajouteEvenement(in);
                    while (nbfinal>1) {
                        t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), plusProcheeaufeu, t+1);
                        re = new Remplissage(eteindre,t, incendies.get(i).getIntensite() - etat.get(eteindre).getreservoir());
                        etat.put(eteindre, new EtatDetails(min, plusProcheeaufeu, Math.min(incendies.get(i).getIntensite(),eteindre.getCapaciteMaxReservoir()),t));
                        simulateur.ajouteEvenement(re);
                        t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t+1);
                        in = new Intervention(eteindre, incendies.get(i),t);
                        etat.put(eteindre, new EtatDetails(min, destination, etat.get(eteindre).getreservoir() - Math.min(incendies.get(i).getIntensite(),eteindre.getCapaciteMaxReservoir()) ,t ));
                        simulateur.ajouteEvenement(in);
                        nbfinal--;
                    }
            }else{   
                t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t+1);
                Intervention in = new Intervention(eteindre, incendies.get(i), t);
                etat.put(eteindre, new EtatDetails(min, destination, etat.get(eteindre).getreservoir() - incendies.get(i).getIntensite() ,t));
                // System.out.println(destination.getLigne()+ " " + destination.getColonne());
                simulateur.ajouteEvenement(in);
            }
            tempsGlobal += t;
            }
    }


    SimpleEntry<Case, Double> plusProche(DonneeSimulation donnes,Case depart, Robot robot,List<Case> casesEau){
        double minEau=Double.MAX_VALUE;
        double tempsEau;
        RechercheChemin r = new RechercheChemin(donnes.getCarte());
        Case plusProcheeau=new Case(0,0,NatureTerrain.EAU);
        for (Case caseEau : casesEau){
            Case destinationEau=donnes.getCarte().getCase(caseEau.getLigne(),caseEau.getColonne());
            if (!robot.getType().equals("Drone") && !robot.getType().equals("RobotAPattes"))
            {
                double minVoisin=Double.MAX_VALUE;
                Case destinVoisine=new Case(0, 0, NatureTerrain.TERRAIN_LIBRE);
                if (donnes.getCarte().voisinExiste(destinationEau, Direction.EST)){
                Case voisinEst=donnes.getCarte().getVoisin(destinationEau, Direction.EST);
                    if (robot.peutSeDeplacerSur(voisinEst.getNature()) ){
                        ResultatChemin resultatE=r.calculerCheminOptimal(depart,voisinEst,robot); 
                        if(minVoisin> resultatE.getTempsTotal()){
                            minVoisin=resultatE.getTempsTotal();
                            destinVoisine=voisinEst;
                        }      
                    }
                }
                if (donnes.getCarte().voisinExiste(destinationEau, Direction.OUEST)){
                    Case voisinOuest=donnes.getCarte().getVoisin(destinationEau, Direction.OUEST);
                    if (robot.peutSeDeplacerSur(voisinOuest.getNature()))
                    {
                        ResultatChemin resultatO=r.calculerCheminOptimal(depart,voisinOuest,robot); 
                        if(minVoisin> resultatO.getTempsTotal()){
                            minVoisin=resultatO.getTempsTotal();
                            destinVoisine=voisinOuest;

                        }      
                    }
                }
                if (donnes.getCarte().voisinExiste(destinationEau, Direction.NORD)){
                    Case voisinNord=donnes.getCarte().getVoisin(destinationEau, Direction.NORD);
                    if (robot.peutSeDeplacerSur(voisinNord.getNature()) )
                    {
                        ResultatChemin resultatN=r.calculerCheminOptimal(depart,voisinNord,robot); 
                        if(minVoisin> resultatN.getTempsTotal()){
                            minVoisin=resultatN.getTempsTotal();
                            destinVoisine=voisinNord;
                        }
                    }
                }
                if (donnes.getCarte().voisinExiste(destinationEau, Direction.SUD)){
                    Case voisinSud=donnes.getCarte().getVoisin(destinationEau, Direction.SUD);
                    if (robot.peutSeDeplacerSur(voisinSud.getNature()) )
                    {
                        ResultatChemin resultatS=r.calculerCheminOptimal(depart,voisinSud,robot); 
                        if(minVoisin> resultatS.getTempsTotal()){
                            minVoisin=resultatS.getTempsTotal();
                            destinVoisine=voisinSud;
                        }
                    }
                }
                tempsEau=minVoisin;
                if (tempsEau < minEau){
                    minEau =tempsEau;
                    plusProcheeau=destinVoisine;
                }
            }else{
                ResultatChemin resultat=r.calculerCheminOptimal(depart,destinationEau,robot); 
                tempsEau=resultat.getTempsTotal();
                if (tempsEau < minEau )
                {
                    minEau =tempsEau;
                    plusProcheeau=destinationEau;
                }
            }
        }
        return new SimpleEntry<>(plusProcheeau,minEau);
    }
}







