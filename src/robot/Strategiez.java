package robot;
import carte.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import simulateur.*;


public class Strategiez {
    private List<Robot> robots;
    private List<Incendie> incendies;
    private List<Case> casesEau;

    public void chefPompier(DonneeSimulation donnes, Simulateur simulateur) {
        robots = donnes.getRobots();
        incendies = trierIncendiesParProximite(donnes);
        for (Incendie incendie : incendies) {
            System.out.println("Incendie à la position: (" + incendie.getPosition().getLigne() + ", " + incendie.getPosition().getColonne() + ")");
        }
        casesEau = donnes.getCasesEau();
        boolean remplir = false;
        long t=0;

        Map<Robot, EtatDetails> etat = new HashMap<>();
        for (Robot robot : robots) {
            etat.put(robot, new EtatDetails(0.0, robot.getPosition(), robot.getNiveauReservoirEau(), 0));
        }

        RechercheChemin r = new RechercheChemin(donnes.getCarte());

        for (int i = 0; i < incendies.size(); i++){
            Robot eteindre = null;
            Case destination = donnes.getCarte().getCase(incendies.get(i).getPosition().getLigne(), incendies.get(i).getPosition().getColonne());
            double minTemps = Double.MAX_VALUE;
            int nbFinal = 0;

            for (Robot robot : robots) {
                robot.setCarte(donnes.getCarte());
                robot.setSimulateur(simulateur);
                EtatDetails details = etat.get(robot);
                if (details != null) {
                    t = details.gettempsCour();
                } else {
                    // Si l'état du robot n'a pas été trouvé dans le map, tu peux gérer le cas, par exemple :
                    System.out.println("L'état du robot n'a pas été trouvé.");
                    // Ou bien initialiser `details` ici, si nécessaire :
                    details = new EtatDetails(0.0, eteindre.getPosition(), eteindre.getNiveauReservoirEau(), t);
                    etat.put(eteindre, details);
                }
                Case depart = details.getCaseAssociee();
                double temps;
                int volIntervention = Math.min(details.getreservoir(), incendies.get(i).getIntensite());

                if (details.getreservoir() >= incendies.get(i).getIntensite()) {
                    ResultatChemin resultat = r.calculerCheminOptimal(depart, destination, robot);
                    temps = resultat.getTempsTotal() + details.gettemps() + incendies.get(i).tempsIntervention(robot, volIntervention);
                    if (temps < minTemps) {
                        minTemps = temps;
                        eteindre = robot;
                        remplir = false;
                    }
                } else {
                    SimpleEntry<Case, Double> closestWaterEntry = plusProche(donnes, depart, robot, casesEau);
                    Case closestWater = closestWaterEntry.getKey();
                    double minEau = closestWaterEntry.getValue();
                    
                    ResultatChemin resultatFeu = r.calculerCheminOptimal(closestWater, destination, robot);
                    double tempsRemplissage = robot.getTempsTotal(robot.getCapaciteMaxReservoir() - details.getreservoir());
                    double tempsFeu = resultatFeu.getTempsTotal();
                    int nbAllerRetour = (int) Math.ceil((double) incendies.get(i).getIntensite() / robot.getCapaciteMaxReservoir());
                    double tempsTotal = minEau + tempsRemplissage + tempsFeu + details.gettemps() + incendies.get(i).tempsIntervention(robot, volIntervention);
                    
                    if (nbAllerRetour > 1) {
                        SimpleEntry<Case, Double> closestWaterToFire = plusProche(donnes, incendies.get(i).getPosition(), robot, casesEau);
                        double minEauFeu = closestWaterToFire.getValue();
                        tempsTotal += ((2 * minEauFeu + tempsRemplissage) * (nbAllerRetour - 1));
                    }

                    if (tempsTotal < minTemps) {
                        minTemps = tempsTotal;
                        eteindre = robot;
                        remplir = true;
                        nbFinal = nbAllerRetour;
                    }
                }
            }

            t = etat.get(eteindre).gettempsCour();
            if (remplir) {
                t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), plusProche(donnes, etat.get(eteindre).getCaseAssociee(), eteindre, casesEau).getKey(), t + 1);
                Remplissage re = new Remplissage(eteindre, t, incendies.get(i).getIntensite() - etat.get(eteindre).getreservoir());
                etat.put(eteindre, new EtatDetails(minTemps, plusProche(donnes, etat.get(eteindre).getCaseAssociee(), eteindre, casesEau).getKey(), Math.min(incendies.get(i).getIntensite(), eteindre.getCapaciteMaxReservoir()), t));
                simulateur.ajouteEvenement(re);
                t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t + 1);
                Intervention in = new Intervention(eteindre, incendies.get(i), t);
                etat.put(eteindre, new EtatDetails(minTemps, destination, etat.get(eteindre).getreservoir() - Math.min(incendies.get(i).getIntensite(), eteindre.getCapaciteMaxReservoir()), t));
                simulateur.ajouteEvenement(in);

                while (nbFinal > 1) {
                    t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), plusProche(donnes, incendies.get(i).getPosition(), eteindre, casesEau).getKey(), t + 1);
                    re = new Remplissage(eteindre, t, incendies.get(i).getIntensite() - etat.get(eteindre).getreservoir());
                    etat.put(eteindre, new EtatDetails(minTemps, plusProche(donnes, incendies.get(i).getPosition(), eteindre, casesEau).getKey(), Math.min(incendies.get(i).getIntensite(), eteindre.getCapaciteMaxReservoir()), t));
                    simulateur.ajouteEvenement(re);
                    t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t + 1);
                    in = new Intervention(eteindre, incendies.get(i), t);
                    etat.put(eteindre, new EtatDetails(minTemps, destination, etat.get(eteindre).getreservoir() - Math.min(incendies.get(i).getIntensite(), eteindre.getCapaciteMaxReservoir()), t));
                    simulateur.ajouteEvenement(in);
                    nbFinal--;
                }
            } else {
                t += eteindre.deplacerVersCase(etat.get(eteindre).getCaseAssociee(), destination, t + 1);
                Intervention in = new Intervention(eteindre, incendies.get(i), t);
                etat.put(eteindre, new EtatDetails(minTemps, destination, etat.get(eteindre).getreservoir() - incendies.get(i).getIntensite(), t));
                simulateur.ajouteEvenement(in);
            }
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
    // Méthode pour trier les incendies par proximité les uns des autres
    public List<Incendie> trierIncendiesParProximite(DonneeSimulation donnes) {
        incendies = donnes.getIncendies();

        if (incendies.isEmpty()) return incendies;

        List<Incendie> incendiesTries = new ArrayList<>();
        Incendie incendieActuel = incendies.get(0);  // On commence par le premier incendie
        incendiesTries.add(incendieActuel);
        List<Incendie> incendiesRestants = new ArrayList<>(incendies);
        incendiesRestants.remove(incendieActuel);

        while (!incendiesRestants.isEmpty()) {
            // Trouver l'incendie le plus proche
            Incendie incendieLePlusProche = trouverIncendieLePlusProche(incendieActuel, incendiesRestants);
            incendiesTries.add(incendieLePlusProche);
            incendiesRestants.remove(incendieLePlusProche);
            incendieActuel = incendieLePlusProche;
        }

        return incendiesTries;
    }

    // Méthode pour trouver l'incendie le plus proche
    private Incendie trouverIncendieLePlusProche(Incendie incendieActuel, List<Incendie> incendiesRestants) {
        double distanceMin = Double.MAX_VALUE;
        Incendie incendieLePlusProche = null;

        for (Incendie incendie : incendiesRestants) {
            double distance = calculerDistance(incendieActuel.getPosition(), incendie.getPosition());
            if (distance < distanceMin) {
                distanceMin = distance;
                incendieLePlusProche = incendie;
            }
        }
        return incendieLePlusProche;
    }

    // Méthode pour calculer la distance euclidienne entre deux cases (incendies)
    private double calculerDistance(Case c1, Case c2) {
        int dx = c1.getLigne() - c2.getLigne();
        int dy = c1.getColonne() - c2.getColonne();
        return Math.sqrt(dx * dx + dy * dy);
    }
} 
