package net.jjcobb03.secretsofthearcane.aspect;

import net.jjcobb03.secretsofthearcane.api.ArcaneApi;
import net.jjcobb03.secretsofthearcane.api.aspect.Aspect;
import net.jjcobb03.secretsofthearcane.api.registry.ArcaneRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAspects {

    public static final DeferredRegister<Aspect> ASPECTS =
            DeferredRegister.create(ArcaneRegistries.ASPECT_KEY, ArcaneApi.MOD_ID);

    // TODO: Add the missing Aspects after testing

    /**
     * An Aspect cannot be declared before one of its component Aspects
     */

    // The Primal Aspects
    public static final DeferredHolder<Aspect, Aspect> AER =
            ASPECTS.register("aer", () -> new Aspect(0xFFFF7E));
    public static final DeferredHolder<Aspect, Aspect> IGNIS =
            ASPECTS.register("ignis", () -> new Aspect(0xFF5A01));
    public static final DeferredHolder<Aspect, Aspect> ORDO =
            ASPECTS.register("ordo", () -> new Aspect(0xD5D4EC));

    // Compound Aspects
    public static final DeferredHolder<Aspect, Aspect> LUX =
            ASPECTS.register("lux", () -> new Aspect(0xFFF663, AER, IGNIS));
    public static final DeferredHolder<Aspect, Aspect> POTENTIA =
            ASPECTS.register("potentia", () -> new Aspect(0xC0FFFF, ORDO, IGNIS));

    public static void register(IEventBus eventBus) {
        ASPECTS.register(eventBus);
    }
}
