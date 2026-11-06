package net.jjcobb03.secretsofthearcane;

import net.jjcobb03.secretsofthearcane.api.item.AspectContainerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
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

        event.enqueueWork(() -> {
            for (Item item : BuiltInRegistries.ITEM) {
                if (item instanceof AspectContainerItem container) {
                    // Drives the empty/filled model swap
                    ItemProperties.register(item, AspectContainerItem.FILLED_PROPERTY,
                            (stack, level, entity, seed) -> container.getContents(stack).isEmpty() ? 0f : 1f);
                }
            }
        });
    }

    // Tints the liquid layer of Essentia Containers
    @SubscribeEvent
    static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof AspectContainerItem container) {
                event.register((stack, tintIndex) ->
                        tintIndex == 0 ? (0xFF000000 | container.getLiquidColor(stack)) : -1, item);
            }
        }
    }
}
