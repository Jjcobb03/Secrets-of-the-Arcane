package net.jjcobb03.secretsofthearcane.magic.aspect;

/**
 * This is where the list of Aspects is defined
 */
public class ModAspects {

    // TODO Add localization for Aspect names

    // Primal Aspects
    public static final Aspect ORDO = new Aspect("ordo", 0xE8D8FF);
    public static final Aspect PERDITIO = new Aspect("perditio", 0x444444);
    public static final Aspect IGNIS = new Aspect("ignis", 0xFF5A1F);
    public static final Aspect AQUA = new Aspect("aqua", 0x3FA9FF);
    public static final Aspect TERRA = new Aspect("terra", 0x5D8B3D);
    public static final Aspect AER = new Aspect("aer", 0xD6EFFF);

    // Tier 1 Compound Aspects (Primal + Primal)
    public static final Aspect PERMUTATIO = new Aspect("permutatio", 0x444444, ORDO, PERDITIO);
    public static final Aspect POTENTIA = new Aspect("potentia", 0x444444, ORDO, IGNIS);
    // ORDO + AQUA = ??
    public static final Aspect VITREUS = new Aspect("vitreus", 0x444444, ORDO, TERRA);
    public static final Aspect MOTUS = new Aspect("motus", 0x444444, ORDO, AER);
    public static final Aspect GELUM = new Aspect("gelum", 0x444444, PERDITIO, IGNIS);
    public static final Aspect VENENUM = new Aspect("venenum", 0x444444, PERDITIO, AQUA);
    // PERDITIO + TERRA = ??
    public static final Aspect VACUOS = new Aspect("vacuos", 0x444444, PERDITIO, AER);
    // IGNIS + AQUA = ??
    // IGNIS + TERRA = ??
    public static final Aspect LUX = new Aspect("lux", 0x444444, IGNIS, AER);
    public static final Aspect VICUS = new Aspect("victus", 0x444444, AQUA, TERRA);
    public static final Aspect TEMPESTAS = new Aspect("tempestas", 0x444444, AQUA, AER);

}
