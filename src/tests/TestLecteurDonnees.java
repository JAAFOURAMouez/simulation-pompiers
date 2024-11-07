package tests;
import java.io.FileNotFoundException;
import simulateur.*;
public class TestLecteurDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            LectureDonnee.lire(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        }
    }
}
