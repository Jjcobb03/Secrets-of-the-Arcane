package net.jjcobb03.secretsofthearcane.magic.aspect;

/**
 * This is used to hold a specific type of aspect, and an amount.
 */
public class AspectStack {

    private final Aspect aspect;
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

    /**
     * Check if the AspectStack is empty
     * @return - True if empty, false if not
     */
    public boolean isEmpty() {
        return amount <= 0;
    }

    /**
     * Get a copy of this AspectStack
     * @return - A copy of this AspectStack
     */
    public AspectStack copy() {
        return new AspectStack(aspect, amount);
    }

    @Override
    public String toString() {
        return aspect + ": " + amount;
    }
}
