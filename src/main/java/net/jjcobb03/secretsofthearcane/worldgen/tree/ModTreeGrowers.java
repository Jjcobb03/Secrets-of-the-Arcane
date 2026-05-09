package net.jjcobb03.secretsofthearcane.worldgen.tree;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.jjcobb03.secretsofthearcane.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {

    public static final TreeGrower ROWAN = new TreeGrower(SecretsOfTheArcane.MOD_ID + ":rowan",
            Optional.empty(), Optional.of(ModConfiguredFeatures.ROWAN_KEY), Optional.empty());
}
