package net.jjcobb03.secretsofthearcane.api.aspect;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * General purpose AspectContainer
 */
public class SimpleAspectContainer implements IAspectContainer {

    public static final int UNLIMITED = Integer.MAX_VALUE;

    // Used for containers with multiple Aspects
    private final int totalCapacity;
    // The maximum amount of a single type of Aspect that can be stored
    private final int perAspectCapacity;
    // The maximum amount of Aspects that can be stored
    private final int maxAspectTypes;

    // Used for when Aspects will have different capacities.
    // Ex: a container that holds 100 Ignis, but 50 Aqua
    @Nullable
    private final Map<Aspect, Integer> aspectLimits;

    // Will only hold Aspects with a total above 0
    private final Map<Aspect, Integer> contents = new LinkedHashMap<>();
    private int total = 0;

    /**
     * Main constructor used by the factory constructors
     *
     * @param totalCapacity - The maximum units this container can hold.
     * @param perAspectCapacity - The maximum amount of units this container can hold of each Aspect
     * @param maxAspectTypes - The maximum amount of Aspects this container can hold
     * @param aspectLimits - A map of Aspects to Integers containing the maximum amount of each Aspect that can be held
     */
    private SimpleAspectContainer(int totalCapacity, int perAspectCapacity,
                                  int maxAspectTypes, @Nullable Map<Aspect, Integer> aspectLimits) {
        // Validate inputs
        if (totalCapacity < 0 || perAspectCapacity < 0 || maxAspectTypes < 0) {
            throw new IllegalArgumentException("Container limits must be non-negative");
        }

        this.totalCapacity = totalCapacity;
        this.perAspectCapacity = perAspectCapacity;
        this.maxAspectTypes = maxAspectTypes;
        this.aspectLimits = aspectLimits == null ? null : Map.copyOf(aspectLimits);
    }

    /**
     * Creates a SimpleAspectContainer that is limited by capacity, but not Aspect Types
     * Ex: A container that stores 500 total units of any amount of Aspects
     *
     * @param totalCapacity - The max capacity of the AspectContainer
     * @return - a SimpleAspectContainer with the specified capacity that can hold any amount of Aspects
     */
    public static SimpleAspectContainer totalPool(int totalCapacity) {
        return new SimpleAspectContainer(totalCapacity, UNLIMITED, UNLIMITED, null);
    }

    /**
     * Creates a SimpleAspectContainer that has a specific capacity of a specific amount of Aspect types
     * Ex: a container that can hold 64 units of 5 different types of Aspects
     *
     * @param perAspectCapacity - How much of each Aspect the container can hold
     * @param maxAspectTypes - How many different Aspects the container can hold
     * @return - a SimpleAspectContainer that has the specified capacity
     */
    public static SimpleAspectContainer perAspect(int perAspectCapacity, int maxAspectTypes) {
        return new SimpleAspectContainer(UNLIMITED, perAspectCapacity, maxAspectTypes, null);
    }

    /**
     * Creates a SimpleAspectContainer that holds a single Aspect
     * Ex: a container that holds 64 of a single Aspect
     *
     * @param capacity - The Capacity of the single Aspect this container holds
     * @return - a SimpleAspectContainer that holds one Aspect and has the specified capacity
     */
    public static SimpleAspectContainer singleAspect(int capacity) {
        return new SimpleAspectContainer(UNLIMITED, capacity, 1, null);
    }

    /**
     * Creates a SimpleAspectContainer with everything specified. Passes the inputs to the main constructor.
     *
     * @return - a SimpleAspectContainer with the specified capacities
     */
    public static SimpleAspectContainer custom(int totalCapacity, int perAspectCapacity, int maxAspectTypes) {
        return new SimpleAspectContainer(totalCapacity, perAspectCapacity, maxAspectTypes, null);
    }

    /**
     * Creates a SimpleAspectContainer that can have unique limits for each Aspect
     * @param limits - a Map containing all the specified limits.
     * @return - a SimpleAspetContainer that has the specified storage limits
     */
    public static SimpleAspectContainer withLimits(Map<Aspect, Integer> limits) {
        return new SimpleAspectContainer(UNLIMITED, UNLIMITED, UNLIMITED, limits);
    }

    @Override
    public int insert(AspectStack stack, boolean simulate) {

        int requested = stack.amount();

        // Can't insert a negative amount
        if (requested <= 0) {
            return 0;
        }

        Aspect aspect = stack.aspect();
        int current = getAmount(aspect);

        // Check for a free Aspect type slot
        if (current == 0 && contents.size() >= maxAspectTypes) {
            return 0;
        }

        // The most this container may hold of the selected aspect.
        // With a limits map, each aspect has its own number. Unlisted aspects get 0, and are refused.
        // Without one, every aspect shares the single perAspectCapacity value.
        int aspectCap = aspectLimits != null
                ? aspectLimits.getOrDefault(aspect, 0)
                : perAspectCapacity;

        // Determine how much is accepted
        int space = Math.min(aspectCap - current, totalCapacity - total);
        int accepted = Math.max(0, Math.min(requested, space));

        // If we are not simulating, perform the transfer
        if (!simulate && accepted > 0) {
            contents.merge(aspect, accepted, Integer::sum);

            // Update the total contents
            total += accepted;
        }

        // Return how much can be accepted
        return accepted;
    }

    @Override
    public int extract(Aspect aspect, int max, boolean simulate) {
        int current = getAmount(aspect);
        int removed = Math.min(Math.max(max, 0), current);

        // If we aren't simulating, extract the contents
        if (!simulate && removed > 0) {
            int remaining = current - removed;
            if (remaining == 0) {
                // Remove the Aspect from the container
                contents.remove(aspect);
            } else {
                contents.put(aspect, remaining);
            }

            // Update the total contents
            total -= removed;
        }

        // Return how much can be extracted
        return removed;
    }

    /**
     * Checks how much of a specific Aspect is stored
     *
     * @param aspect - the Aspect to check
     * @return - how much of the specified Aspect is stored
     */
    @Override
    public int getAmount(Aspect aspect) {
        return contents.getOrDefault(aspect, 0);
    }

    @Override
    public int getTotalAmount() {
        return total;
    }

    @Override
    public List<AspectStack> getContents() {
        List<AspectStack> result = new ArrayList<>();
        contents.forEach((aspect, amount) -> result.add(new AspectStack(aspect, amount)));
        return result;
    }
}
