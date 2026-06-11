package net.jjcobb03.secretsofthearcane.api.aspect;

import net.jjcobb03.secretsofthearcane.magic.aspect.AspectRegistry;
import net.jjcobb03.secretsofthearcane.magic.aspect.ModAspects;
import net.minecraft.world.item.Items;

/**
 * This is where Aspects are assigned to an item
 */
public class AspectAssignments {

    /**
     * Here's where the aspect to item assignments are made
     */
    public static void register() {

        AspectRegistry.register(Items.COAL,
                new AspectStack(ModAspects.IGNIS, 4),
                new AspectStack(ModAspects.PERDITIO, 2));

    }


}
