package net.jjcobb03.secretsofthearcane.datagen;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// Tutorial has this written as below, but the ommited part has been labeled deprecated
// @EventBusSubscriber(modid = SecretsOfTheArcane.MOD_ID, bus = EventBusSubscriber.Bus.MOD)

@EventBusSubscriber(modid = SecretsOfTheArcane.MOD_ID)
public class DataGenerators {

    @SubscribeEvent
    public static void GatherData(GatherDataEvent event) {
        // Holds Providers to run when datagen is executed
        DataGenerator generator = event.getGenerator();
        // Tells the providers where to write generated files, and how to structure them
        PackOutput packOutput = generator.getPackOutput();
        // Ensures referenced files exist, and helps prevent broken references and overwriting files
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        // Registry access system for datagen
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Call ModBlockLootTableProvider
        generator.addProvider(event.includeServer(), new LootTableProvider(
                packOutput,
                Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new,
                        LootContextParamSets.BLOCK)),
                lookupProvider));

        // Call ModRecipeProvider
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));

        // Call ModBlockTagProvider
        BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);

        // Call ModItemTagProvider
        generator.addProvider(event.includeServer(), new ModItemTagProvider(
                packOutput,
                lookupProvider,
                blockTagsProvider.contentsGetter(),
                existingFileHelper));

        // Call ModItemModelProvider
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        // Call ModBlockStateProvider
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
    }
}
