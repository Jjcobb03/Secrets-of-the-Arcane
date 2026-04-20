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

        // Used for example smelting recipes
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

        oreSmelting(recipeOutput, VIS_SMELTABLES, RecipeCategory.MISC, ModItems.VIS_CRYSTAL.get(), 0.25f, 200, "vis crystals");
        oreBlasting(recipeOutput, VIS_SMELTABLES, RecipeCategory.MISC, ModItems.VIS_CRYSTAL.get(), 0.25f, 100, "vis crystals");
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, SecretsOfTheArcane.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
