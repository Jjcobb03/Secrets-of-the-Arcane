package net.jjcobb03.secretsofthearcane.api.aspect;

import net.jjcobb03.secretsofthearcane.api.registry.ArcaneRegistries;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

/**
 * A single Aspect. Instances are registered in ArcaneRegistries.ASPECTS.
 * Aspects are either primal (No components or compound (Made from two Aspects).
 */
public class Aspect {

    // RGB color used for tinting
    private final int color;

    // The potential component Aspects. If both are Null, then the Aspect is primal.
    @Nullable
    private final Supplier<Aspect> componentA;
    @Nullable
    private final Supplier<Aspect> componentB;

    private String descriptionID;

    // Primal Aspect constructor
    public Aspect(int color) {
        this.color = color;
        this.componentA = null;
        this.componentB = null;
    }

    // Compound Aspect constructor
    public Aspect(int color, Supplier<Aspect> componentA, Supplier<Aspect> componentB) {

        // Validate component Aspects
        if (componentA == null || componentB == null) {
            throw new IllegalArgumentException("A compound Aspect needs two components");
        }

        this.color = color;
        this.componentA = componentA;
        this.componentB = componentB;
    }

    /**
     * Gets the Aspect's color.
     * @return The color associated with the Aspect.
     */
    public int getColor() {
        return color;
    }

    /**
     * Checks if the Aspect is Primal
     * @return True if Primal, false if Compound
     */
    public boolean isPrimal() {
        return componentA == null;
    }

    /**
     * Checks if the Aspect is Compound
     * @return True if Compound, false if Primal
     */
    public boolean isCompound() {
        return componentA != null;
    }

    /**
     * Get the component Aspects. Should only be called after registration.
     * @return The two component Aspects if this Aspect is compound, or an empty list if this Aspect is primal.
     */
    public List<Aspect> getComponents() {
        if (isPrimal()) {
            return List.of();
        }
        return List.of(componentA.get(), componentB.get());
    }

    // Translation Key
    public String getDescriptionID () {
        if(this.descriptionID == null) {
            this.descriptionID = Util.makeDescriptionId("aspect", ArcaneRegistries.ASPECTS.getKey(this));
        }
        return this.descriptionID;
    }

    public Component getDisplayName() {
        return Component.translatable(getDescriptionID());
    }

}