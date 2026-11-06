package net.jjcobb03.secretsofthearcane;

import net.jjcobb03.secretsofthearcane.api.aspect.AspectCapabilities;
import net.jjcobb03.secretsofthearcane.block.entity.ModBlockEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = SecretsOfTheArcane.MOD_ID)
public class ModCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Every side of the jar exposes the same container
        event.registerBlockEntity(AspectCapabilities.ASPECT_CONTAINER,
                ModBlockEntities.ESSENTIA_JAR.get(),
                (jar, side) -> jar.getStorage());
    }
}