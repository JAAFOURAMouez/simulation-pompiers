package carte;

/**
 * Enumération représentant différents types de terrains sur lesquels un robot peut se déplacer.
 * Chaque terrain a des caractéristiques spécifiques qui affectent le déplacement du robot.
 */
public enum NatureTerrain {

    /**
     * Terrain représentant de l'eau, sur lequel un robot ne peut pas se déplacer.
     */
    EAU,

    /**
     * Terrain représentant une forêt, qui pourrait ralentir le robot.
     */
    FORET,

    /**
     * Terrain représentant des roches, qui pourrait rendre le déplacement difficile ou impossible.
     */
    ROCHE,

    /**
     * Terrain libre, sur lequel le robot peut se déplacer sans restriction.
     */
    TERRAIN_LIBRE,

    /**
     * Terrain représentant un habitat, sur lequel le robot peut se déplacer mais avec une vitesse réduite.
     */
    HABITAT
}
