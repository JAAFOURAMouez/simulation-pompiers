package robot;
import carte.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import simulateur.*;


/**
 * La classe Strategie implémente la stratégie du chef pompier pour coordonner 
 * les actions des robots et éteindre efficacement les incendies.
 */

public class Strategie {
    // Liste des robots, incendies et cases d'eau utilisées par le chef pompier
    private List<Robot> robots;
    private List<Incendie> incendies;
    private List<Case> casesEau;

    /**
     * Coordonne les robots pour éteindre les incendies en assignant le robot optimal
     * à chaque incendie. Elle planifie les déplacements, remplissages et interventions.
     *
     * @param donnes      Instance de DonneeSimulation contenant la carte, les robots, les incendies et les cases d'eau.
     * @param simulateur  Instance de Simulateur utilisée pour planifier les événements.
     */


    public void chefPompier(DonneeSimulation donnes, Simulateur simulateur) {
        robots = donnes.getRobots();
        // Trie les incendies par proximité
        incendies = trierIncendiesParProximite(donnes);
        casesEau = donnes.getCasesEau();
        boolean remplir = false;
        long t=0;

        // Crée une map pour suivre l'état des robots pendant la simulation
        Map<Robot, EtatDetails> etat = new HashMap<>();
        for (Robot robot : robots) {
            etat.put(robot, new EtatDetails(0.0, robot.getPosition(), robot.getNiveauReservoirEau(), 0));
        }
        RechercheChemin r = new RechercheChemin(donnes.getCarte());

        // Parcours de chaque incendie pour assigner un robot pour l'éteindre
        for (int i = 0; i < incendies.size(); i++){
            Robot robotOptimal = null;
            Case destination = donnes.getCarte().getCase(incendies.get(i).getPosition().getLigne(), incendies.get(i).getPosition().getColonne());
            double minTemps = Double.MAX_VALUE;
            int nbFinal = 0;
            // Pour chaque robot, on cherche le meilleur à envoyer pour éteindre l'incendie
            for (Robot robot : robots) {
                robot.setCarte(donnes.getCarte());
                robot.setSimulateur(simulateur);
                EtatDetails details = etat.get(robot); // Récupère l'état actuel du robot
                if (details != null) {
                    t = details.getTempsCour();
                } else {
                    if (robotOptimal != null) {
                        details = new EtatDetails(0.0, robotOptimal.getPosition(), robotOptimal.getNiveauReservoirEau(), t);
                    } else {
                        System.out.println("Erreur : Aucun robot disponible pour l'intervention.");
                        // Handle this case as needed, maybe assign a default value or skip this step
                    }
                    etat.put(robotOptimal, details);
                }
                // Logique pour calculer le temps nécessaire pour éteindre un incendie
                Case depart = details.getCaseAssociee();
                double temps;
                int volIntervention = Math.min(details.getReservoir(), incendies.get(i).getIntensite());

                if (details.getReservoir() >= incendies.get(i).getIntensite()) {
                    // Le robot a assez d'eau, on calcule le chemin et le temps d'intervention
                    ResultatChemin resultat = r.calculerCheminOptimal(depart, destination, robot);
                    temps = resultat.getTempsTotale() + details.getTemps() + incendies.get(i).tempsIntervention(robot, volIntervention);
                    if (temps < minTemps) {
                        minTemps = temps;
                        robotOptimal = robot;
                        remplir = false;
                    }
                } else {
                    // Le robot n'a pas assez d'eau, on cherche l'eau la plus proche pour faire le plein
                    SimpleEntry<Case, Double> closestWaterEntry = plusProche(donnes, depart, robot, casesEau);
                    Case closestWater = closestWaterEntry.getKey();
                    double minEau = closestWaterEntry.getValue();

                    // Calcul du temps pour aller à la source d'eau, faire le plein, puis éteindre le feu
                    ResultatChemin resultatFeu = r.calculerCheminOptimal(closestWater, destination, robot);
                    double tempsRemplissage = robot.getTempsRemplissage(robot.getCapaciteMaxReservoir() - details.getReservoir());
                    double tempsFeu = resultatFeu.getTempsTotale();
                    int nbAllerRetour = (int) Math.ceil((double) incendies.get(i).getIntensite() / robot.getCapaciteMaxReservoir());
                    double tempsTotal = minEau + tempsRemplissage + tempsFeu + details.getTemps() + incendies.get(i).tempsIntervention(robot, volIntervention);
                    
                    // Gestion des allers-retours pour récupérer de l'eau
                    if (nbAllerRetour > 1) {
                        SimpleEntry<Case, Double> closestWaterToFire = plusProche(donnes, incendies.get(i).getPosition(), robot, casesEau);
                        double minEauFeu = closestWaterToFire.getValue();
                        tempsTotal += ((2 * minEauFeu + tempsRemplissage) * (nbAllerRetour - 1));
                    }

                    // Mise à jour du robot à envoyer en fonction du temps le plus court
                    if (tempsTotal < minTemps) {
                        minTemps = tempsTotal;
                        robotOptimal = robot;
                        remplir = true;
                        nbFinal = nbAllerRetour;
                    }
                }
            }
            // Logique de déplacement du robot choisi et d'intervention sur l'incendie  
            int volumerobotOptimal;
            t = etat.get(robotOptimal).getTempsCour();
            if (remplir) {
                // Le robot doit se rendre à l'eau, se remplir et ensuite éteindre l'incendie
                t += robotOptimal.deplacerVersCase(etat.get(robotOptimal).getCaseAssociee(), plusProche(donnes, etat.get(robotOptimal).getCaseAssociee(), robotOptimal, casesEau).getKey(), t + 1);
                t+=robotOptimal.getTempsRemplissage(robotOptimal.getCapaciteMaxReservoir() - etat.get(robotOptimal).getReservoir());
                
                Remplissage remplissageEau = new Remplissage(robotOptimal, t, incendies.get(i).getIntensite() - etat.get(robotOptimal).getReservoir());
                etat.put(robotOptimal, new EtatDetails(minTemps, plusProche(donnes, etat.get(robotOptimal).getCaseAssociee(), robotOptimal, casesEau).getKey(), Math.min(incendies.get(i).getIntensite(), robotOptimal.getCapaciteMaxReservoir()), t));
                simulateur.ajouteEvenement(remplissageEau);

                // Le robot se rend ensuite à l'incendie pour l'éteindre
                t += robotOptimal.deplacerVersCase(etat.get(robotOptimal).getCaseAssociee(), destination, t + 1);
                volumerobotOptimal = Math.min(etat.get(robotOptimal).getReservoir(), incendies.get(i).getIntensite());
                t+=incendies.get(i).tempsIntervention(robotOptimal, volumerobotOptimal);
                Intervention intervention = new Intervention(robotOptimal, incendies.get(i), t);

                // Mise à jour de l'état du robot après l'intervention
                etat.put(robotOptimal, new EtatDetails(minTemps, destination, etat.get(robotOptimal).getReservoir() - Math.min(incendies.get(i).getIntensite(), robotOptimal.getCapaciteMaxReservoir()), t));
                simulateur.ajouteEvenement(intervention);

                // Traitement des allers-retours pour les interventions multiples
                while (nbFinal > 1) {
                    // Même logique que précédemment pour gérer les allers-retours
                    t += robotOptimal.deplacerVersCase(etat.get(robotOptimal).getCaseAssociee(), plusProche(donnes, incendies.get(i).getPosition(), robotOptimal, casesEau).getKey(), t + 1);
                    t+=robotOptimal.getTempsRemplissage(robotOptimal.getCapaciteMaxReservoir() -  etat.get(robotOptimal).getReservoir());

                    remplissageEau= new Remplissage(robotOptimal, t, incendies.get(i).getIntensite() - etat.get(robotOptimal).getReservoir());
                    etat.put(robotOptimal, new EtatDetails(minTemps, plusProche(donnes, incendies.get(i).getPosition(), robotOptimal, casesEau).getKey(), Math.min(incendies.get(i).getIntensite(), robotOptimal.getCapaciteMaxReservoir()), t));
                    simulateur.ajouteEvenement(remplissageEau);
                    t += robotOptimal.deplacerVersCase(etat.get(robotOptimal).getCaseAssociee(), destination, t + 1);
                    volumerobotOptimal = Math.min(etat.get(robotOptimal).getReservoir(), incendies.get(i).getIntensite());
                    t+=incendies.get(i).tempsIntervention(robotOptimal, volumerobotOptimal);
                    intervention= new Intervention(robotOptimal, incendies.get(i), t);

                    etat.put(robotOptimal, new EtatDetails(minTemps, destination, etat.get(robotOptimal).getReservoir() - Math.min(incendies.get(i).getIntensite(), robotOptimal.getCapaciteMaxReservoir()), t));
                    simulateur.ajouteEvenement(intervention);
                    nbFinal--;
                }
            }else {
                t += robotOptimal.deplacerVersCase(etat.get(robotOptimal).getCaseAssociee(), destination, t + 1);
                t+=robotOptimal.getTempsRemplissage(robotOptimal.getCapaciteMaxReservoir() -  etat.get(robotOptimal).getReservoir());
                volumerobotOptimal = Math.min(etat.get(robotOptimal).getReservoir(), incendies.get(i).getIntensite());
                t+=incendies.get(i).tempsIntervention(robotOptimal, volumerobotOptimal);
                Intervention intervention= new Intervention(robotOptimal, incendies.get(i), t);

                etat.put(robotOptimal, new EtatDetails(minTemps, destination, etat.get(robotOptimal).getReservoir() - incendies.get(i).getIntensite(), t));
                simulateur.ajouteEvenement(intervention);
            }
           
        }
    }


     /**
     * Trouve la case d'eau la plus proche pour un robot donné à partir d'une position de départ.
     *
     * @param donnes     Instance de DonneeSimulation contenant les informations sur la carte et les cases d'eau.
     * @param depart     La case de départ du robot.
     * @param robot      Le robot cherchant une source d'eau.
     * @param casesEau   Liste des cases contenant de l'eau.
     * @return           Une paire contenant la case d'eau la plus proche et le temps pour y accéder.
     */

    SimpleEntry<Case, Double> plusProche(DonneeSimulation donnes, Case depart, Robot robot, List<Case> casesEau) {
        double minEau = Double.MAX_VALUE;  // Initialisation du temps minimal pour l'eau la plus proche
        double tempsEau;
        RechercheChemin route = new RechercheChemin(donnes.getCarte());  // Objet pour calculer le chemin optimal
        Case plusProcheeau = new Case(0, 0, NatureTerrain.EAU);  // Case d'eau par défaut
    
        // On parcourt toutes les cases d'eau disponibles
        for (Case caseEau : casesEau) {
            Case destinationEau = donnes.getCarte().getCase(caseEau.getLigne(), caseEau.getColonne());
    
            // Si le robot n'est ni un drone ni un robot à pattes, on vérifie les voisins
            if (!robot.getType().equals("Drone") && !robot.getType().equals("RobotAPattes")) {
                double minVoisin = Double.MAX_VALUE;  // Temps minimal pour atteindre un voisin de la case d'eau
                Case destinVoisine = new Case(0, 0, NatureTerrain.TERRAIN_LIBRE);  // Voisin par défaut
    
                // Vérification de la possibilité de se déplacer vers les voisins (Est, Ouest, Nord, Sud)
                for (Direction direction : Direction.values()) {
                    if (donnes.getCarte().voisinExiste(destinationEau, direction)) {
                        Case voisin = donnes.getCarte().getVoisin(destinationEau, direction);
                        if (robot.peutSeDeplacerSur(voisin.getNature())) {
                            ResultatChemin resultat = route.calculerCheminOptimal(depart, voisin, robot);
                            if (minVoisin > resultat.getTempsTotale()) {
                                minVoisin = resultat.getTempsTotale();
                                destinVoisine = voisin;  // Mémorise le meilleur voisin
                            }
                        }
                    }
                }
    
                tempsEau = minVoisin;  // Temps d'accès à l'eau via le meilleur voisin
                if (tempsEau < minEau) {  // Si c'est le meilleur temps trouvé
                    minEau = tempsEau;
                    plusProcheeau = destinVoisine;  // Met à jour la case d'eau la plus proche
                }
            } else {
                // Pour les drones ou robots à pattes, on calcule directement le temps pour se rendre à la case d'eau
                ResultatChemin resultat = route.calculerCheminOptimal(depart, destinationEau, robot);
                tempsEau = resultat.getTempsTotale();
                if (tempsEau < minEau) {  // Si c'est le meilleur temps trouvé
                    minEau = tempsEau;
                    plusProcheeau = destinationEau;
                }
            }
        }
        return new SimpleEntry<>(plusProcheeau, minEau);  // Retourne la case d'eau la plus proche et le temps pour y accéder
    }

     /**
     * Trie les incendies en fonction de leur proximité les uns des autres pour minimiser les déplacements.
     *
     * @param donnes  Instance de DonneeSimulation contenant la liste des incendies.
     * @return        Une liste triée des incendies par ordre de proximité.
     */
    
    public List<Incendie> trierIncendiesParProximite(DonneeSimulation donnes) {
        incendies = donnes.getIncendies();  // Récupère la liste des incendies
    
        if (incendies.isEmpty()) return incendies;  // Si aucun incendie, retourne une liste vide
    
        List<Incendie> incendiesTries = new ArrayList<>();
        Incendie incendieActuel = incendies.get(0);  // Commence par le premier incendie
        incendiesTries.add(incendieActuel);  // Ajoute cet incendie à la liste triée
        List<Incendie> incendiesRestants = new ArrayList<>(incendies);
        incendiesRestants.remove(incendieActuel);  // Enlève cet incendie des incendies restants
    
        // Trie les incendies restants en fonction de leur proximité
        while (!incendiesRestants.isEmpty()) {
            Incendie incendieLePlusProche = trouverIncendieLePlusProche(incendieActuel, incendiesRestants);
            incendiesTries.add(incendieLePlusProche);  // Ajoute l'incendie le plus proche à la liste triée
            incendiesRestants.remove(incendieLePlusProche);  // Enlève l'incendie de la liste des restants
            incendieActuel = incendieLePlusProche;  // L'incendie actuel devient celui qu'on vient de traiter
        }
    
        return incendiesTries;  // Retourne la liste triée des incendies
    }
    

/**
     * Trouve l'incendie le plus proche d'un incendie donné parmi une liste d'incendies restants.
     *
     * @param incendieActuel      L'incendie de référence.
     * @param incendiesRestants   Liste des incendies restants à évaluer.
     * @return                    L'incendie le plus proche de l'incendie de référence.
     */
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

 /**
     * Calcule la distance entre deux cases en utilisant la distance euclidienne.
     *
     * @param c1  Première case.
     * @param c2  Deuxième case.
     * @return    La distance entre les deux cases.
     */
        private double calculerDistance(Case c1, Case c2) {
        int dx = c1.getLigne() - c2.getLigne();
        int dy = c1.getColonne() - c2.getColonne();
        return dx * dx + dy * dy;
    }
} 
