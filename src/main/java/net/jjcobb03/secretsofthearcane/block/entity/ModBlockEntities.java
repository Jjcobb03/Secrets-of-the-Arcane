package net.jjcobb03.secretsofthearcane.block.entity;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, SecretsOfTheArcane.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EssentiaJarBlockEntity>> ESSENTIA_JAR =
            BLOCK_ENTITIES.register("essentia_jar", () ->
                    BlockEntityType.Builder.of(EssentiaJarBlockEntity::new, ModBlocks.ESSENTIA_JAR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}