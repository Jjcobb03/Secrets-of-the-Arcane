package net.jjcobb03.secretsofthearcane.datagen;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.jjcobb03.secretsofthearcane.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Helps create recipe .json files
 */
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    // Constructor
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        // Used for example smelting recipes. All items in this list should yield the same results when smelted
        List<ItemLike> VIS_SMELTABLES = List.of(
                ModBlocks.TERRA_ORE,
                ModBlocks.TERRA_DEEPSLATE_ORE);

        // Example shaped recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROWAN_LOG.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ModBlocks.ROWAN_PLANKS.get())
                .unlockedBy("has_rowan_planks", has(ModBlocks.ROWAN_PLANKS)).save(recipeOutput);

        // Shapeless Recipe for Rowan Planks
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROWAN_PLANKS.get(), 4)
                .requires(ModBlocks.ROWAN_LOG)
                .unlockedBy("has_rowan_log", has(ModBlocks.ROWAN_LOG)).save(recipeOutput);

        // Furnace recipes
        oreSmelting(recipeOutput, VIS_SMELTABLES, RecipeCategory.MISC, ModItems.VIS_CRYSTAL.get(), 0.25f, 200, "vis crystals");

        // Blasting Furnace recipes
        oreBlasting(recipeOutput, VIS_SMELTABLES, RecipeCategory.MISC, ModItems.VIS_CRYSTAL.get(), 0.25f, 100, "vis crystals");
    }

    // Helper method that covers smelting ores in a furnace
    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    // Helper method that covers blasting ores in a blast furnace
    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    /**
     * Helper method from Kaupenjoe that handles cooking ores. Used by the oreSmelting and oreBlasting helper methods
     * I have attempted to fill out what information I could figure out regarding the variables.
     *
     * @param recipeOutput
     * @param pCookingSerializer
     * @param factory
     * @param pIngredients - The list of items which can be used as ingredients in the recipe
     * @param pCategory - The category this will show up under in the recipe book
     * @param pResult - The item crafted from the recipe
     * @param pExperience - The experience given by the recipe
     * @param pCookingTime - How long the recipe takes to cook
     * @param pGroup
     * @param pRecipeName - The name of the .json file that is output?
     * @param <T>
     */
    protected static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer,
            AbstractCookingRecipe.Factory<T> factory, List<ItemLike> pIngredients,
            RecipeCategory pCategory, ItemLike pResult, float pExperience,
            int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, SecretsOfTheArcane.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
