package net.jjcobb03.secretsofthearcane.api.aspect;

/**
 * Helper class for how Vis is stored in an AspectStack.
 * Vis is stored as whole "Units" where VIS_SCALE is the value equal to 1 unit in game
 */
public class VisUnits {

    // The value of 1 unit of Vis. Changing this will break existing saves.
    public static final int VIS_SCALE = 100;

    private VisUnits() {}

    /**
     * Applies a Vis discount.
     *
     * @param cost The initial cost.
     * @param discountPercent The discount, clamped between 0 and 100.
     * @return the cost after applying the discount.
     */
    public static int applyDiscount(int cost, int discountPercent) {
        int clamped = Math.max(0, Math.min(100, discountPercent));
        return (int) (((long) cost * (100 - clamped) + 99) / 100);
    }
}
