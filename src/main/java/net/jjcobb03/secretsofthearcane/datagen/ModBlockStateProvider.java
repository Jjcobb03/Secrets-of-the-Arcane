package net.jjcobb03.secretsofthearcane.datagen;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

/**
 * Helps create the blockstate and model .json files for each block
 */
public class ModBlockStateProvider extends BlockStateProvider {

    // Constructor
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SecretsOfTheArcane.MOD_ID, exFileHelper);
    }

    // List of blocks to be registered
    @Override
    protected void registerStatesAndModels() {
        // Simple Blocks
        blockWithItem(ModBlocks.ROWAN_PLANKS);
        blockWithItem(ModBlocks.TERRA_ORE);
        blockWithItem(ModBlocks.TERRA_DEEPSLATE_ORE);

        // Pillar Blocks
        rotatedPillarBlockWithItem((RotatedPillarBlock) ModBlocks.ROWAN_LOG.get());
//        logBlock((RotatedPillarBlock) ModBlocks.ROWAN_LOG.get());
//        simpleBlockItem(
//                ModBlocks.ROWAN_LOG.get(),
//                models().getExistingFile(modLoc("block/rowan_log"))
//        );
    }

    /**
     * Helper method for simple blocks that use the same texture on all 6 faces
     *
     * @param deferredBlock - A DeferredBlock that will be created with the same texture on all 6 faces
     */
    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    /**
     * Helper method for RotatedPillarBlocks that generates blockstates, block model, and item model.
     *
     * @param block - A RotatedPillarBlock that will be created
     */
    private void rotatedPillarBlockWithItem(RotatedPillarBlock block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();

        // Blockstates and Block Model
        logBlock(block);
        // Item Model
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + name)));
    }
    
}
