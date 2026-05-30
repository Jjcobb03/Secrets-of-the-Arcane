package net.jjcobb03.secretsofthearcane.block.entity;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SecretsOfTheArcane.MOD_ID);

    public static final Supplier<BlockEntityType<EssentiaJarBlockEntity>> ESSENTIA_JAR =
            BLOCK_ENTITIES.register("essentia_jar", () -> BlockEntityType.Builder.of(
                    EssentiaJarBlockEntity::new, ModBlocks.ESSENTIA_JAR.get()).build(null));
}
