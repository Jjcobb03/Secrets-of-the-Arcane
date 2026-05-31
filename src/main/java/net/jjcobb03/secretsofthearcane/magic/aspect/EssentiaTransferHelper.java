package net.jjcobb03.secretsofthearcane.magic.aspect;

import net.jjcobb03.secretsofthearcane.item.custom.AbstractEssentiaContainerItem;
import net.minecraft.world.item.ItemStack;

public final class EssentiaTransferHelper {

    private EssentiaTransferHelper() {}

    public static int transferItemToStorage(
            AbstractEssentiaContainerItem item, ItemStack itemStack, IEssentiaContainer destination) {

        for (AspectStack stored : item.getContents(itemStack)) {

            // How much can be transferred
            int accepted = destination.insert(stored, TransferAction.SIMULATE);

            if (accepted <= 0) {
                continue;
            }

            AspectStack extracted = item.extract(itemStack, stored.getAspect(), accepted, TransferAction.EXECUTE);

            destination.insert(extracted, TransferAction.EXECUTE);

            return extracted.getAmount();
        }

        return 0;
    }

    public static int transferStorageToItem(
            IEssentiaContainer source, AbstractEssentiaContainerItem item, ItemStack itemStack) {

        for (AspectStack stored : source.getContents()) {

            int accepted = item.insert(itemStack, stored, TransferAction.SIMULATE);

            if (accepted <= 0) {
                continue;
            }

            AspectStack extracted = source.extract(stored.getAspect(), accepted, TransferAction.EXECUTE);

            item.insert(itemStack, extracted, TransferAction.EXECUTE);

            return extracted.getAmount();
        }

        return 0;
    }

}
