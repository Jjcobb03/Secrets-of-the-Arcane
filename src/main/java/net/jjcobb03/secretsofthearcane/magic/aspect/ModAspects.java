package net.jjcobb03.secretsofthearcane.magic.aspect;

import java.util.HashMap;
import java.util.Map;

/**
 * This is where the list of Aspects is defined
 */
public class ModAspects {

    // TODO Add localization for Aspect names

    // Map used for looking up Aspects. <id, Aspect>
    private static final Map<String, Aspect> ASPECTS = new HashMap<>();

    // Primal Aspects
    public static final Aspect ORDO = register(new Aspect("ordo", 0xE8D8FF));
    public static final Aspect PERDITIO = register (new Aspect("perditio", 0x444444));
    public static final Aspect IGNIS = register(new Aspect("ignis", 0xFF5A1F));
    public static final Aspect AQUA = register(new Aspect("aqua", 0x3FA9FF));
    public static final Aspect TERRA = register(new Aspect("terra", 0x5D8B3D));
    public static final Aspect AER = register(new Aspect("aer", 0xD6EFFF));

    // Tier 1 Compound Aspects (Primal + Primal)
    public static final Aspect PERMUTATIO = register(new Aspect("permutatio", 0x444444, ORDO, PERDITIO));
    public static final Aspect POTENTIA = register(new Aspect("potentia", 0x444444, ORDO, IGNIS));
    // ORDO + AQUA = ??
    public static final Aspect VITREUS = register(new Aspect("vitreus", 0x444444, ORDO, TERRA));
    public static final Aspect MOTUS = register(new Aspect("motus", 0x444444, ORDO, AER));
    public static final Aspect GELUM = register(new Aspect("gelum", 0x444444, PERDITIO, IGNIS));
    public static final Aspect VENENUM = register(new Aspect("venenum", 0x444444, PERDITIO, AQUA));
    // PERDITIO + TERRA = ??
    public static final Aspect VACUOS = register(new Aspect("vacuos", 0x444444, PERDITIO, AER));
    // IGNIS + AQUA = ??
    // IGNIS + TERRA = ??
    public static final Aspect LUX = register(new Aspect("lux", 0x444444, IGNIS, AER));
    public static final Aspect VICUS = register(new Aspect("victus", 0x444444, AQUA, TERRA));
    public static final Aspect TEMPESTAS = register(new Aspect("tempestas", 0x444444, AQUA, AER));


    /**
     * Helper method that adds an Aspect to the lookup map, and returns the added Aspect
     * @param aspect - The Aspect to be added to the map
     * @return - The Aspect that was added to the map
     */
    private static Aspect register(Aspect aspect) {
        // Add the Aspect to the map
        ASPECTS.put(aspect.getId(), aspect);
        // Return the Aspect so it can be accessed up via ModAspects.AspectNameHere
        return aspect;
    }

    public static Aspect getAspect(String id) {
        return ASPECTS.get(id);
    }

}
