package net.jjcobb03.secretsofthearcane.datagen;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.jjcobb03.secretsofthearcane.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

/**
 * Helps provide the item model .json files
 */
public class ModItemModelProvider extends ItemModelProvider {

    // Constructor
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SecretsOfTheArcane.MOD_ID, existingFileHelper);
    }

    // Register Items
    @Override
    protected void registerModels() {

        // Basic items with simple textures/models
        basicItem(ModItems.VIS_CRYSTAL.get());
        basicItem(ModItems.ESSENTIA_VIAL.get());

        basicItem(ModBlocks.ROWAN_DOOR.asItem());

        buttonItem(ModBlocks.ROWAN_BUTTON, ModBlocks.ROWAN_PLANKS);
        fenceItem(ModBlocks.ROWAN_FENCE, ModBlocks.ROWAN_PLANKS);
        wallItem(ModBlocks.ROWAN_WALL, ModBlocks.ROWAN_PLANKS);

    }

    /**
     * Helper method from Kaupenjoe's tutorial. Creates an item model for buttons
     *
     * @param block - The button to make an item of
     * @param baseBlock - The material the button uses for its texture
     */
    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(SecretsOfTheArcane.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    /**
     * Helper method from Kaupenjoe's tutorial. Creates an item model for fences
     *
     * @param block - The fence to make an item of
     * @param baseBlock - The material the fence uses for its texture
     */
    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(SecretsOfTheArcane.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    /**
     * Helper method from Kaupenjoe's tutorial. Creates an item model for walls
     *
     * @param block - The wall to make an item of
     * @param baseBlock - The material the wall uses for its texture
     */
    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(SecretsOfTheArcane.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }
}
