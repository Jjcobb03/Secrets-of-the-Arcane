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
                .add(ModBlocks.ROWAN_WOOD.get())
                .add(ModBlocks.STRIPPED_ROWAN_LOG.get())
                .add(ModBlocks.STRIPPED_ROWAN_WOOD.get())
                .add(ModBlocks.ROWAN_PLANKS.get())
                .add(ModBlocks.ROWAN_STAIRS.get())
                .add(ModBlocks.ROWAN_SLAB.get())
                .add(ModBlocks.ROWAN_DOOR.get())
                .add(ModBlocks.ROWAN_TRAPDOOR.get())
                .add(ModBlocks.ROWAN_FENCE.get())
                .add(ModBlocks.ROWAN_FENCE_GATE.get())
                .add(ModBlocks.ROWAN_WALL.get())
                .add(ModBlocks.ROWAN_PRESSURE_PLATE.get())
                .add(ModBlocks.ROWAN_BUTTON.get());

        // Blocks that are considered logs
        tag(BlockTags.LOGS)
                .add(ModBlocks.ROWAN_LOG.get())
                .add(ModBlocks.STRIPPED_ROWAN_LOG.get())
                .add(ModBlocks.ROWAN_WOOD.get())
                .add(ModBlocks.STRIPPED_ROWAN_WOOD.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROWAN_LOG.get())
                .add(ModBlocks.STRIPPED_ROWAN_LOG.get())
                .add(ModBlocks.ROWAN_WOOD.get())
                .add(ModBlocks.STRIPPED_ROWAN_WOOD.get());

        // Fence Blocks
        tag(BlockTags.FENCES)
                .add(ModBlocks.ROWAN_FENCE.get());

        // Fence Gate Blocks
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ROWAN_FENCE_GATE.get());

        // Wall Blocks
        tag(BlockTags.WALLS)
                .add(ModBlocks.ROWAN_WALL.get());

    }
}
