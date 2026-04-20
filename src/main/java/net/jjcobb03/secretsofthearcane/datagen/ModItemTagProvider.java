package net.jjcobb03.secretsofthearcane.datagen;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Helps assign tags to items
 */
public class ModItemTagProvider extends ItemTagsProvider {

    // Constructor
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SecretsOfTheArcane.MOD_ID, existingFileHelper);
    }

    // Add tags to items
    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // Add the minecraft:planks tag
        tag(ItemTags.PLANKS)
                .add(ModBlocks.ROWAN_PLANKS.get().asItem());
        
        // Add the minecraft:logs tag
        tag(ItemTags.LOGS)
                .add(ModBlocks.ROWAN_LOG.get().asItem());

        // Add the minecraft:logs_that_burn tag
        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROWAN_LOG.get().asItem());
    }
}
