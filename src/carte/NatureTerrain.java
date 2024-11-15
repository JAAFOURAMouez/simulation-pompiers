package carte;

/**
 * Enumération représentant les différents types de terrains possibles sur la carte.
 * Chaque valeur correspond à un type de terrain qui peut influencer le comportement des robots sur la carte.
 * Les types de terrain sont :
 * <ul>
 * <li><b>EAU</b> : Terrain d'eau.</li>
 * <li><b>FORET</b> : Terrain forestier.</li>
 * <li><b>ROCHE</b> : Terrain rocheux.</li>
 * <li><b>TERRAIN_LIBRE</b> : Terrain dégagé, sans obstacles.</li>
 * <li><b>HABITAT</b> : Terrain où se trouvent des habitats humains ou des structures.</li>
 * </ul>
 */
public enum NatureTerrain {

    /**
     * Terrain d'eau. Ce type de terrain peut avoir une influence sur certains types de robots (par exemple, les drones peuvent naviguer dessus, mais d'autres robots non).
     */
    EAU,

    /**
     * Terrain forestier. Certaines machines peuvent se déplacer dans ce type de terrain mais avec des ralentissements (par exemple, les robots à chenilles).
     */
    FORET,

    /**
     * Terrain rocheux. Les robots peuvent avoir des difficultés à se déplacer sur ce type de terrain (par exemple, les robots à roues et à chenilles peuvent avoir des problèmes).
     */
    ROCHE,

    /**
     * Terrain dégagé, sans obstacles. Les robots peuvent se déplacer librement et efficacement sur ce type de terrain.
     */
    TERRAIN_LIBRE,

    /**
     * Terrain où des habitats humains ou des structures sont présents. Certains robots peuvent avoir une difficulté à naviguer dans ces zones.
     */
    HABITAT
}
