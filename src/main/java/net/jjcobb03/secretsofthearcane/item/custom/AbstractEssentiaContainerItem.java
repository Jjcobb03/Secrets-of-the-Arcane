package net.jjcobb03.secretsofthearcane.item.custom;

import net.jjcobb03.secretsofthearcane.api.essentia.EssentiaStorage;
import net.jjcobb03.secretsofthearcane.api.essentia.EssentiaStorageAdapter;
import net.jjcobb03.secretsofthearcane.api.essentia.TransferAction;
import net.jjcobb03.secretsofthearcane.component.EssentiaContainerData;
import net.jjcobb03.secretsofthearcane.component.ModDataComponents;
import net.jjcobb03.secretsofthearcane.api.aspect.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public abstract class AbstractEssentiaContainerItem extends Item {

    protected final int capacity;

    protected AbstractEssentiaContainerItem(int capacity, Properties properties) {
        super(properties);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public EssentiaStorage getStorage(ItemStack stack) {

        EssentiaContainerData data = stack.getOrDefault(
                ModDataComponents.ESSENTIA_STORAGE.get(), EssentiaContainerData.EMPTY);

        return EssentiaStorageAdapter.fromData(data, capacity);
    }

    public void saveStorage(ItemStack stack, EssentiaStorage storage) {
        stack.set(ModDataComponents.ESSENTIA_STORAGE.get(), EssentiaStorageAdapter.toData(storage));
    }

    /**
     * Insert Essentia into this itemStack
     * @param stack
     * @param aspectStack
     * @param action
     * @return
     */
    public int insert(ItemStack stack, AspectStack aspectStack, TransferAction action) {

        EssentiaStorage storage = getStorage(stack);

        int inserted = storage.insert(aspectStack, action);

        if (action == TransferAction.EXECUTE && inserted > 0) {
            saveStorage(stack, storage);
        }

        return inserted;
    }

    /**
     * Extract Essentia from this ItemStack
     * @param stack
     * @param aspect
     * @param amount
     * @param action
     * @return
     */
    public AspectStack extract(ItemStack stack, Aspect aspect, int amount, TransferAction action) {

        EssentiaStorage storage = getStorage(stack);

        AspectStack extracted = storage.extract(aspect, amount, action);

        if (action == TransferAction.EXECUTE && extracted.getAmount() > 0) {
            saveStorage(stack, storage);
        }

        return extracted;
    }

    /**
     *
     * @param stack
     * @return
     */
    public Collection<AspectStack> getContents(
            ItemStack stack) {

        return getStorage(stack)
                .getContents();
    }

    /**
     *
     * @param stack
     * @return
     */
    public int getStoredAmount(
            ItemStack stack) {

        return getStorage(stack)
                .getStoredAmount();
    }

    /**
     *
     * @param stack
     * @param aspect
     * @return
     */
    public int getAmount(
            ItemStack stack,
            Aspect aspect) {

        return getStorage(stack).getAmount(aspect);
    }

    /**
     * Print the contents of the item in chat when shift clicked
     * @param player
     * @param stack
     */
    public void printContents(Player player, ItemStack stack) {

        EssentiaStorage storage = getStorage(stack);

        player.sendSystemMessage(Component.literal(
                        "Stored: "
                                + storage.getStoredAmount()
                                + "/"
                                + storage.getCapacity()
                )
        );

        if (storage.getContents().isEmpty()) {

            player.sendSystemMessage(Component.literal("No Essentia stored."));

            return;
        }

        for (AspectStack aspectStack : storage.getContents()) {

            player.sendSystemMessage(Component.literal(
                            aspectStack.getAspect().getId()
                                    + ": "
                                    + aspectStack.getAmount()
                    )
            );
        }
    }

    /* Add a bar to indicate how full the item is */

    /**
     * Makes the bar visible when not empty
     * @param stack
     * @return
     */
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getStoredAmount(stack) > 0;
    }

    /**
     * Calculate the width of the bar
     * @param stack
     * @return
     */
    @Override
    public int getBarWidth(ItemStack stack) {

        int stored = getStoredAmount(stack);

        return Math.round(
                13.0F * stored / getCapacity()
        );
    }

    /**
     * Get the Aspect's corresponding color and make the bar have the same color
     * @param stack
     * @return
     */
    @Override
    public int getBarColor(ItemStack stack) {

        Collection<AspectStack> contents =
                getContents(stack);

        if (contents.size() == 1) {
            return contents.iterator()
                    .next()
                    .getAspect()
                    .getColor();
        }

        return 0x7D4BFF;
    }
}
