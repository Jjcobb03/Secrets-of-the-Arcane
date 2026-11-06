package net.jjcobb03.secretsofthearcane.item.custom;

import net.jjcobb03.secretsofthearcane.api.aspect.SimpleAspectContainer;
import net.jjcobb03.secretsofthearcane.api.item.EssentiaContainerItem;

// Holds 16 Essentia of a single Aspect
public class EssentiaVialItem extends EssentiaContainerItem {

    public static final int CAPACITY = 16;

    public EssentiaVialItem(Properties properties) {
        super(properties);
    }

    @Override
    public SimpleAspectContainer createEmptyStorage() {
        return SimpleAspectContainer.singleAspect(CAPACITY);
    }

}
