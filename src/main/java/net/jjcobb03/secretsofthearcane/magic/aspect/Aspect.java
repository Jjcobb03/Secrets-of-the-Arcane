package net.jjcobb03.secretsofthearcane.magic.aspect;

/**
 * This defines the structure of an Aspect
 */
public class Aspect {

    // The name of this Aspect
    private final String id;

    // Is this a primal aspect, meaning it has no parents?
    private final boolean primal;

    // What two Aspects combine to make this Aspect
    private final Aspect parent1;
    private final Aspect parent2;

    /**
     * Constructor for Primal Aspects
     * @param id - The name of the aspect
     */
    public Aspect(String id) {
        this.id = id;
        this.primal = true;
        // No parents
        this.parent1 = null;
        this.parent2 = null;
    }

    /**
     * Constructor for Compound Aspects
     * @param id - The name of the Aspect
     * @param parent1 - The 1st component of this Aspect
     * @param parent2 - The 2nd component of this Aspect
     */
    public Aspect(String id, Aspect parent1, Aspect parent2) {
        this.id = id;
        this.primal = false;

        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    /**
     * Check if the aspect is primal
     * @return - True if primal, False if compound
     */
    public boolean isPrimal() {
        return primal;
    }

    /**
     * Check if the aspect is compound
     * @return - True if compound, False if primal
     */
    public boolean isCompound() {
        return !primal;
    }

    /**
     * Helper method to check if one Aspect is a component of another
     * @param aspect - The aspect we want to check
     * @return - True if that aspect is a component of this one, false if not
     */
    public boolean contains(Aspect aspect) {
        // Unsure if I want to keep this
//        if (this == aspect) {
//            return true;
//        }

        // A primal Aspect contains no other components
        if (primal) {
            return false;
        }

        // Check if one of the components is the parameter Aspect
        return parent1 == aspect || parent2 == aspect;
    }

    public String getId() {
        return id;
    }

    /**
     * Get the 1st Aspect used to make this one
     * @return - The 1st Aspect used to make this one
     */
    public Aspect getParent1() {
        return parent1;
    }

    /**
     * Get the 2nd Aspect used to make this one
     * @return - The 2nd Aspect used to make this one
     */
    public Aspect getParent2() {
        return parent2;
    }

    @Override
    public String toString() {
        return id;
    }
}
