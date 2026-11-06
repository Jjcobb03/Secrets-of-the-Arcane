package net.jjcobb03.secretsofthearcane.api.aspect;

import net.jjcobb03.secretsofthearcane.api.ArcaneApi;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

public final class AspectCapabilities {

    // Lets blocks expose an IAspectContainer. The Direction is the side being accessed (null = no particular side)
    public static final BlockCapability<IAspectContainer, @Nullable Direction> ASPECT_CONTAINER =
            BlockCapability.createSided(ArcaneApi.id("aspect_container"), IAspectContainer.class);

    private AspectCapabilities() {}
}