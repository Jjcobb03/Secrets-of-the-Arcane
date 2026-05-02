package net.jjcobb03.secretsofthearcane.item;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    // Deferred Register for creating a creative mode tab
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SecretsOfTheArcane.MOD_ID);

    // Make a creative mode tab
    public static final Supplier<CreativeModeTab> SECRETS_OF_THE_ARCANE_TAB =
            CREATIVE_MODE_TAB.register("secrets_of_the_arcane_tab",
                    () -> CreativeModeTab.builder()
                            // Determines the tab's icon
                            .icon(() -> new ItemStack(ModItems.TEST_ITEM.get()))
                            // Sets the ID used for translations in the lang folder
                            .title(Component.translatable("creativetab.secretsofthearcane.secrets_of_the_arcane"))
                            .displayItems((itemDisplayParameters, output) -> {
                                // Items that will display in this tab
                                output.accept(ModItems.TEST_ITEM);

                                // Rowan Wood Set
                                output.accept(ModBlocks.ROWAN_LOG);
                                output.accept(ModBlocks.ROWAN_PLANKS);
                                output.accept(ModBlocks.ROWAN_STAIRS);
                                output.accept(ModBlocks.ROWAN_SLAB);
                                output.accept(ModBlocks.ROWAN_PRESSURE_PLATE);
                                output.accept(ModBlocks.ROWAN_BUTTON);
                                output.accept(ModBlocks.ROWAN_FENCE);
                                output.accept(ModBlocks.ROWAN_FENCE_GATE);
                                output.accept(ModBlocks.ROWAN_WALL);
                                output.accept(ModBlocks.ROWAN_DOOR);
                                output.accept(ModBlocks.ROWAN_TRAPDOOR);

                                output.accept(ModBlocks.TERRA_ORE);
                                output.accept(ModBlocks.TERRA_DEEPSLATE_ORE);
                                output.accept(ModItems.ESSENTIA_VIAL);
                                output.accept(ModItems.VIS_CRYSTAL);
                            }).build());

    public static final Supplier<CreativeModeTab> SECRETS_OF_THE_ARCANE_TAB_2 =
            CREATIVE_MODE_TAB.register("secrets_of_the_arcane_tab_2",
                    () -> CreativeModeTab.builder()
                            // Determines the tab's icon
                            .icon(() -> new ItemStack(ModBlocks.ROWAN_LOG.get()))
                            // Places this tab after the selected tab
                            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(
                                    SecretsOfTheArcane.MOD_ID,
                                    "secrets_of_the_arcane_tab"))
                            // Sets the ID used for translations in the lang folder
                            .title(Component.translatable("creativetab.secretsofthearcane.secrets_of_the_arcane_2"))
                            .displayItems((itemDisplayParameters, output) -> {
                                // Items that will display in this tab
                                output.accept(ModBlocks.ROWAN_LOG);
                            }).build());

    // Register the new creative mode tab
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
