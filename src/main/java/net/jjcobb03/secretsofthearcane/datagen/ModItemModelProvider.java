package net.jjcobb03.secretsofthearcane.datagen;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

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
        basicItem(ModItems.VIS_CRYSTAL.get());
        basicItem(ModItems.ESSENTIA_VIAL.get());
    }
}
