package net.jjcobb03.secretsofthearcane.worldgen;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    // Configured Feature -> Placed Feature -> Biome Modifier

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_TERRA_ORE_KEY = registerKey("terra_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_TERRA_ORE_KEY = registerKey("nether_terra_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_TERRA_ORE_KEY = registerKey("end_terra_ore");

    // Where the configured features are defined
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        // Anything stone ores can spawn in
        RuleTest stoneReplacables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        // Anything deepslate ores can spawn in
        RuleTest deepslateReplacables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        // Can replace Netherrack
        RuleTest netherrackReplacables = new BlockMatchTest(Blocks.NETHERRACK);
        // Can replace End Stone
        RuleTest endStoneReplacables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldTerraOres = List.of(
                OreConfiguration.target(stoneReplacables, ModBlocks.TERRA_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplacables, ModBlocks.TERRA_DEEPSLATE_ORE.get().defaultBlockState())
        );

        // Overworld Terra Ore
        register(context, OVERWORLD_TERRA_ORE_KEY, Feature.ORE, new OreConfiguration(overworldTerraOres, 8));
        // Example Terra Ore in Nether / End
        register(context, NETHER_TERRA_ORE_KEY, Feature.ORE, new OreConfiguration(
                netherrackReplacables, ModBlocks.TERRA_ORE.get().defaultBlockState(), 8));
        register(context, END_TERRA_ORE_KEY, Feature.ORE, new OreConfiguration(
                endStoneReplacables, ModBlocks.TERRA_ORE.get().defaultBlockState(), 8));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(SecretsOfTheArcane.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
