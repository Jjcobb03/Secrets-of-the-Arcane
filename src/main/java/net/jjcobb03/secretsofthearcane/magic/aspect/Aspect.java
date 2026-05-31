package net.jjcobb03.secretsofthearcane.magic.aspect;

/**
 * This defines the structure of an Aspect
 */
public class Aspect {

    // The name of this Aspect
    private final String id;

    // The color used for rendering textures
    private final int color;

    // Is this a primal aspect, meaning it has no parents?
    private final boolean primal;

    // What two Aspects combine to make this Aspect
    private final Aspect parent1;
    private final Aspect parent2;

    /**
     * Constructor for Primal Aspects
     * @param id - The name of the aspect
     * @param color - The color for the aspect in Hexadecimal
     */
    public Aspect(String id, int color) {
        this.id = id;
        this.color = color;
        this.primal = true;
        // No parents
        this.parent1 = null;
        this.parent2 = null;
    }

    /**
     * Constructor for Compound Aspects
     * @param id - The name of the Aspect
     * @param color - The color for the aspect in Hexadecimal
     * @param parent1 - The 1st component of this Aspect
     * @param parent2 - The 2nd component of this Aspect
     */
    public Aspect(String id, int color, Aspect parent1, Aspect parent2) {
        this.id = id;
        this.color = color;
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

    public int getColor() {
        return color;
    }

    public int getRed() {
        // Bitwise operation to get the red bits
        return (color >> 16) & 255;
    }

    public int getGreen() {
        // Bitwise operation to get the green bits
        return (color >> 8) & 255;
    }

    public int getBlue() {
        // Bitwise operation to get the blue bits
        return color & 255;
    }

    @Override
    public String toString() {
        return id;
    }
}
