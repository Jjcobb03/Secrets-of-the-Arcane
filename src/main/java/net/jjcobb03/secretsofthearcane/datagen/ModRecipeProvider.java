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
                ModBlocks.TERRA_DEEPSLATE_ORE
        );

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

        // Stair recipes
        stairBuilder(ModBlocks.ROWAN_STAIRS.get(), Ingredient.of(ModBlocks.ROWAN_PLANKS))
                .group("rowan_wood").unlockedBy("has_rowan_planks", has(ModBlocks.ROWAN_PLANKS)).save(recipeOutput);

        // Slab recipes
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROWAN_SLAB.get(), ModBlocks.ROWAN_PLANKS.get());

        // button recipes
        buttonBuilder(ModBlocks.ROWAN_BUTTON.get(), Ingredient.of(ModBlocks.ROWAN_PLANKS.get())).group("rowan_wood")
                .unlockedBy("has_rowan_planks", has(ModBlocks.ROWAN_PLANKS.get())).save(recipeOutput);

        // Pressure Plate recipes
        pressurePlate(recipeOutput, ModBlocks.ROWAN_PRESSURE_PLATE.get(), ModBlocks.ROWAN_PLANKS.get());

        // Fence recipes
        fenceBuilder(ModBlocks.ROWAN_FENCE.get(), Ingredient.of(ModBlocks.ROWAN_PLANKS.get())).group("rowan_wood")
                .unlockedBy("has_rowan_planks", has(ModBlocks.ROWAN_PLANKS.get())).save(recipeOutput);

        // Fence Gate recipes
        fenceGateBuilder(ModBlocks.ROWAN_FENCE_GATE.get(), Ingredient.of(ModBlocks.ROWAN_PLANKS.get())).group("rowan_wood")
                .unlockedBy("has_rowan_planks", has(ModBlocks.ROWAN_PLANKS.get())).save(recipeOutput);

        // Wall recipes
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROWAN_WALL.get(), ModBlocks.ROWAN_PLANKS.get());

        // Door recipes
        doorBuilder(ModBlocks.ROWAN_DOOR.get(), Ingredient.of(ModBlocks.ROWAN_PLANKS.get())).group("rowan_wood")
                .unlockedBy("has_rowan_planks", has(ModBlocks.ROWAN_PLANKS.get())).save(recipeOutput);

        // Trapdoor recipes
        trapdoorBuilder(ModBlocks.ROWAN_TRAPDOOR.get(), Ingredient.of(ModBlocks.ROWAN_PLANKS.get())).group("rowan_wood")
                .unlockedBy("has_rowan_planks", has(ModBlocks.ROWAN_PLANKS.get())).save(recipeOutput);
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
     * Helper method from Kaupenjoe's tutorial for generating cooking recipes (smelting, blasting, etc.).
     * This is used by oreSmelting and oreBlasting to avoid duplicating logic.
     *
     * @param recipeOutput - The output handler that writes the generated recipe JSON files
     * @param pCookingSerializer - Defines the type of cooking recipe being created
     *                             (e.g., SMELTING_RECIPE, BLASTING_RECIPE)
     * @param factory - Factory used to construct the specific recipe type (ties into the serializer)
     * @param pIngredients - List of possible input items for the recipe (e.g., different ore variants)
     *                       A separate recipe will be generated for each item in this list
     * @param pCategory - Determines which tab/category the recipe appears under in the recipe book
     *                    (e.g., MISC, FOOD, BUILDING_BLOCKS)
     * @param pResult - The output item produced by the recipe
     * @param pExperience - Amount of experience granted when the recipe is completed
     * @param pCookingTime - Time (in ticks) required to complete the recipe
     *                       (e.g., 200 for smelting, 100 for blasting)
     * @param pGroup - Optional grouping string for recipes in the recipe book
     *                 Recipes with the same group are visually grouped together
     * @param pRecipeName - Suffix used in the generated recipe file name to distinguish variants
     *                      (e.g., "_from_smelting", "_from_blasting")
     * @param <T> - The specific type of AbstractCookingRecipe being generated
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
