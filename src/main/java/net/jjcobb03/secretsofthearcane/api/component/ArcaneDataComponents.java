package net.jjcobb03.secretsofthearcane.api.component;

import net.jjcobb03.secretsofthearcane.api.ArcaneApi;
import net.jjcobb03.secretsofthearcane.api.aspect.AspectContents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ArcaneDataComponents {

    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ArcaneApi.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AspectContents>> ASPECT_CONTENTS =
            COMPONENTS.registerComponentType("aspect_contents", builder -> builder
                    .persistent(AspectContents.CODEC)
                    .networkSynchronized(AspectContents.STREAM_CODEC));

    private ArcaneDataComponents () {}

    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }
}
