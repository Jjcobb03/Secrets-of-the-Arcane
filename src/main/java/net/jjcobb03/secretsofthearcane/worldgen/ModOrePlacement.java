package net.jjcobb03.secretsofthearcane.worldgen;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * Public version of existing methods that Minecraft uses for placing ore, but made as private methods
 */
public class ModOrePlacement {

    public static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
        return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
    }

    /**
     *
     * @param pCount - The amount of veins per chunk (?)
     * @param pHeightRange
     * @return
     */
    public static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}
