package net.jjcobb03.secretsofthearcane.api.aspect;

import net.jjcobb03.secretsofthearcane.api.component.ArcaneDataComponents;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ItemAspectContainer implements IAspectContainer {

    private final ItemStack stack;
    private final SimpleAspectContainer storage;

    // 'emptyStorage' supplies the capacity rules; its contents get replaced with what's on the stack
    public ItemAspectContainer(ItemStack stack, SimpleAspectContainer emptyStorage) {
        this.stack = stack;
        this.storage = emptyStorage;
        this.storage.setContents(stack.getOrDefault(
                ArcaneDataComponents.ASPECT_CONTENTS.get(), AspectContents.EMPTY).stacks());
        this.storage.setChangeListener(this::writeBack);
    }

    private void writeBack() {
        List<AspectStack> contents = storage.getContents();
        if (contents.isEmpty()) {
            // Removing the component keeps empty containers identical (and stackable) with fresh ones
            stack.remove(ArcaneDataComponents.ASPECT_CONTENTS.get());
        } else {
            stack.set(ArcaneDataComponents.ASPECT_CONTENTS.get(), new AspectContents(contents));
        }
    }

    @Override
    public int insert(AspectStack toInsert, boolean simulate) {
        return storage.insert(toInsert, simulate);
    }

    @Override
    public int extract(Aspect aspect, int max, boolean simulate) {
        return storage.extract(aspect, max, simulate);
    }

    @Override
    public int getAmount(Aspect aspect) {
        return storage.getAmount(aspect);
    }

    @Override
    public int getTotalAmount() {
        return storage.getTotalAmount();
    }

    @Override
    public List<AspectStack> getContents() {
        return storage.getContents();
    }
}
