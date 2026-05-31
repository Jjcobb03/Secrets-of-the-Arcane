package net.jjcobb03.secretsofthearcane.component;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {

    // The Register for data components
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, SecretsOfTheArcane.MOD_ID);

    // Essentia Container Data Component
    public static final Supplier<DataComponentType<EssentiaContainerData>>
            ESSENTIA_STORAGE = DATA_COMPONENTS.registerComponentType(
                    "essentia_storage",
            builder -> builder.persistent(EssentiaContainerData.CODEC)
    );
}
