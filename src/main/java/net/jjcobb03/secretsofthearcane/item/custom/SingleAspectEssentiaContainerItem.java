package net.jjcobb03.secretsofthearcane.item.custom;

import net.jjcobb03.secretsofthearcane.api.aspect.Aspect;
import net.jjcobb03.secretsofthearcane.api.aspect.AspectStack;
import net.jjcobb03.secretsofthearcane.api.essentia.TransferAction;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public abstract class SingleAspectEssentiaContainerItem extends AbstractEssentiaContainerItem {

    protected SingleAspectEssentiaContainerItem(int capacity, Properties properties) {
        super(capacity, properties);
    }

    /**
     * Gets the Aspect currently stored in this container.
     *
     * @param stack The ItemStack to inspect.
     * @return The stored Aspect, or null if empty.
     */
    public Aspect getStoredAspect(ItemStack stack) {

        Collection<AspectStack> contents =
                getContents(stack);

        if (contents.isEmpty()) {
            return null;
        }

        return contents.iterator()
                .next()
                .getAspect();
    }

    @Override
    public int insert(ItemStack stack, AspectStack aspectStack, TransferAction action) {

        Aspect storedAspect = getStoredAspect(stack);

        // Container is empty, and may accept any Aspect
        if (storedAspect == null) {
            return super.insert(stack, aspectStack, action);
        }

        // Check if existing Aspect matches
        if (storedAspect == aspectStack.getAspect()) {
            return super.insert(stack, aspectStack, action);
        }

        // Aspect doesn't match, and can't fit
        return 0;
    }
}
