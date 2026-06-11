package net.jjcobb03.secretsofthearcane;

import net.jjcobb03.secretsofthearcane.item.ModItems;
import net.jjcobb03.secretsofthearcane.item.custom.SingleAspectEssentiaContainerItem;
import net.jjcobb03.secretsofthearcane.api.aspect.Aspect;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = SecretsOfTheArcane.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = SecretsOfTheArcane.MOD_ID, value = Dist.CLIENT)
public class SecretsoftheArcaneClient {
    public SecretsoftheArcaneClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        SecretsOfTheArcane.LOGGER.info("HELLO FROM CLIENT SETUP");
        SecretsOfTheArcane.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {

            // ONLY tint the liquid layer
            if (tintIndex != 1) {
                return 0xFFFFFFFF; // IMPORTANT: fully opaque white
            }

            if (!(stack.getItem() instanceof SingleAspectEssentiaContainerItem container)) {
                return 0xFFFFFFFF;
            }

            Aspect aspect = container.getStoredAspect(stack);

            // The container is empty, return transparency
            if (aspect == null) {
                return 0x00000000;
            }

            // Tint it the color of the Aspect
            return 0xFF000000 | aspect.getColor(); // force full alpha
        }, ModItems.ESSENTIA_VIAL.get());
    }
}
