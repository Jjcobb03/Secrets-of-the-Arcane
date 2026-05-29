package net.jjcobb03.secretsofthearcane.magic.aspect;

import net.minecraft.world.item.ItemStack;

import java.util.Collection;

/**
 * Used for items that store Essentia
 */
public interface IEssentiaContainer {

    /**
     * Gets the maximum capacity of the container
     * @return - The max capacity as an int
     */
    int getCapacity();

    /**
     * Gets the total units of Aspect stored
     * @return - The total storage currently used as an int
     */
    int getStoredAmount();

    /**
     * See how much of a specific Aspect is being stored
     * @param aspect - The Aspect that is being checked
     * @return - The amount of units of storage that is occupied by the selected Aspect
     */
    int getAmount(Aspect aspect);

    /**
     * Gets the total contents of the container
     * @return - Every stored Aspect as a Collection of AspectStacks
     */
    Collection<AspectStack> getContents();

    /**
     * Check to see if the type of Aspect can be stored in the container. Does not check capacity.
     * @param aspect - The Aspect to be checked
     * @return - True if the Aspect can be stored, false if not
     */
    boolean acceptsAspect(Aspect aspect);

    /**
     * Attempts to insert Essentia into the container
     * @param stack - The AspectStack to insert
     * @param action - Whether to execute or simulate the insertion.
     * @return - The amount that was accepted
     */
    int insert(AspectStack stack, TransferAction action);

    /**
     * Attempts to extract Essentia from this container
     * @param aspect - The Aspect to extract
     * @param amount - The desired amount to extract
     * @param action - Whether to execute or simulate the extraction
     * @return - The extracted AspectStack
     */
    AspectStack extract(Aspect aspect, int amount, TransferAction action);

    /**
     * Checks if the container is empty
     * @return - True if empty, False if not
     */
    default boolean isEmpty() {
        return getStoredAmount() <= 0;
    }

    /**
     * Checks if the container is full
     * @return - True if full, False if empty
     */
    default boolean isFull() {
        return getStoredAmount() >= getCapacity();
    }
}
