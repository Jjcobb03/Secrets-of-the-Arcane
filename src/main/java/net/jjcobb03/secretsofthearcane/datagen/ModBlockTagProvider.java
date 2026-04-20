package net.jjcobb03.secretsofthearcane.datagen;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Helps assign tags to blocks
 */
public class ModBlockTagProvider extends BlockTagsProvider {

    // Constructor
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SecretsOfTheArcane.MOD_ID, existingFileHelper);
    }

    // Adds tags to blocks
    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // Blocks that are best harvested with a pickaxe
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.TERRA_ORE.get())
                .add(ModBlocks.TERRA_DEEPSLATE_ORE.get());

        // Blocks that are best harvested with an axe
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.ROWAN_LOG.get())
                .add(ModBlocks.ROWAN_PLANKS.get());

        tag(BlockTags.LOGS)
                .add(ModBlocks.ROWAN_LOG.get());

    }
}
