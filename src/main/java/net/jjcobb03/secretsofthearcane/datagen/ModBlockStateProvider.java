package net.jjcobb03.secretsofthearcane.datagen;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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

        stairsBlock(ModBlocks.ROWAN_STAIRS.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()));
        slabBlock(ModBlocks.ROWAN_SLAB.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()), blockTexture(ModBlocks.ROWAN_PLANKS.get()));

        buttonBlock(ModBlocks.ROWAN_BUTTON.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()));
        pressurePlateBlock(ModBlocks.ROWAN_PRESSURE_PLATE.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()));

        fenceBlock(ModBlocks.ROWAN_FENCE.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()));
        fenceGateBlock(ModBlocks.ROWAN_FENCE_GATE.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()));
        wallBlock(ModBlocks.ROWAN_WALL.get(), blockTexture(ModBlocks.ROWAN_PLANKS.get()));

        // renderType: "cutout" due to holes in the doors
        doorBlockWithRenderType(ModBlocks.ROWAN_DOOR.get(), modLoc("block/rowan_door_bottom"), modLoc("block/rowan_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.ROWAN_TRAPDOOR.get(), modLoc("block/rowan_trapdoor"), true, "cutout");

        blockItem(ModBlocks.ROWAN_STAIRS);
        blockItem(ModBlocks.ROWAN_SLAB);
        blockItem(ModBlocks.ROWAN_PRESSURE_PLATE);
        blockItem(ModBlocks.ROWAN_FENCE_GATE);
        blockItem(ModBlocks.ROWAN_TRAPDOOR, "_bottom");

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

    /**
     * Helper method for creating Block Item Models
     *
     * @param deferredBlock - The block getting an item model
     */
    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(
                "secretsofthearcane:block/" + deferredBlock.getId().getPath()));
    }

    /**
     * Helper method for creating Block Item Models
     *
     * @param deferredBlock - The block getting an item model
     * @param appendix - additional file path text
     */
    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(
                "secretsofthearcane:block/" + deferredBlock.getId().getPath() + appendix));
    }
    
}
