package net.jjcobb03.secretsofthearcane.api.registry;

import net.jjcobb03.secretsofthearcane.api.ArcaneApi;
import net.jjcobb03.secretsofthearcane.api.aspect.Aspect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

/**
 * Holds every custom registry used by the mod. Addons can use these keys with their own DeferredRegisters
 */
public class ArcaneRegistries {

    // The key that addons pass to DeferredRegister.create()
    public static final ResourceKey<Registry<Aspect>> ASPECT_KEY =
            ResourceKey.createRegistryKey(ArcaneApi.id("aspect"));

    // The Aspect Registry
    public static final Registry<Aspect> ASPECTS =
            new RegistryBuilder<>(ASPECT_KEY).sync(true).create();

    private ArcaneRegistries() {}

    public static void onNewRegistry(NewRegistryEvent event) {
        event.register(ASPECTS);
    }
}