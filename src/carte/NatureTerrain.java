package carte;
public enum NatureTerrain {
    /*Type de robot	        EAU	           FORET	       ROCHE	    TERRAIN_LIBRE	    HABITAT
    Drone	                Peut	       Peut	           Peut	            Peut	          Peut
    Robot à roues	    Ne peut pas	    Ne peut pas	    Ne peut pas	        Peut	          Peut
    Robot à chenilles	Ne peut pas	    Peut(ralenti)	Ne peut pas	        Peut	          Peut
    Robot à pattes	    Ne peut pas	       Peut	        Peut(ralenti) 	    Peut	          Peut 
    */
    EAU,            
    FORET,          
    ROCHE,
    TERRAIN_LIBRE,
    HABITAT
}