package net.jjcobb03.secretsofthearcane.magic.aspect;

/**
 * This is used to hold a specific type of aspect, and an amount.
 */
public class AspectStack {

    private final Aspect aspect;
    // Unsure as to whether or not this should remain a final
    private final int amount;

    /**
     * Used to hold a certain amount of a specific Aspect
     * @param aspect - What Aspect this stack represents
     * @param amount - How much of the chosen Aspect this stack holds
     */
    public AspectStack(Aspect aspect, int amount) {
        this.aspect = aspect;
        this.amount = amount;
    }

    /**
     * Get the Aspect this stack represents
     * @return - The Aspect this stack represents
     */
    public Aspect getAspect() {
        return aspect;
    }

    /**
     * Get the amount of Aspect in this stack
     * @return - The amount of Aspect in this stack
     */
    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return aspect + ": " + amount;
    }
}
