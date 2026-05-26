package net.jjcobb03.secretsofthearcane.magic.aspect;

/**
 * This is where the list of Aspects is defined
 */
public class ModAspects {

    // Primal Aspects

    public static final Aspect ORDO = new Aspect("ordo");
    public static final Aspect PERDITIO = new Aspect("perditio");
    public static final Aspect IGNIS = new Aspect("ignis");
    public static final Aspect AQUA = new Aspect("aqua");
    public static final Aspect TERRA = new Aspect("terra");
    public static final Aspect AER = new Aspect("aer");

    // Tier 1 Compound Aspects (Primal + Primal)
    public static final Aspect PERMUTATIO = new Aspect("permutatio", ORDO, PERDITIO);
    public static final Aspect POTENTIA = new Aspect("potentia", ORDO, IGNIS);
    // ORDO + AQUA = ??
    public static final Aspect VITREUS = new Aspect("vitreus", ORDO, TERRA);
    public static final Aspect MOTUS = new Aspect("motus", ORDO, AER);
    public static final Aspect GELUM = new Aspect("gelum", PERDITIO, IGNIS);
    public static final Aspect VENENUM = new Aspect("venenum", PERDITIO, AQUA);
    // PERDITIO + TERRA = ??
    public static final Aspect VACUOS = new Aspect("vacuos", PERDITIO, AER);
    // IGNIS + AQUA = ??
    // IGNIS + TERRA = ??
    public static final Aspect LUX = new Aspect("lux", IGNIS, AER);
    public static final Aspect VICUS = new Aspect("victus", AQUA, TERRA);
    public static final Aspect TEMPESTAS = new Aspect("tempestas", AQUA, AER);

}
