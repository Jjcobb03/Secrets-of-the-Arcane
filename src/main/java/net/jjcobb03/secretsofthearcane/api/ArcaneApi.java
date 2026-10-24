package net.jjcobb03.secretsofthearcane.api;

import net.minecraft.resources.ResourceLocation;

public class ArcaneApi {

    public static final String MOD_ID = "secretsofthearcane";

    private ArcaneApi() {}

    // Shortcut for making IDs under the mod's namespace
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
