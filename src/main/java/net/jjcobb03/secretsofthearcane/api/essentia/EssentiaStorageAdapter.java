package net.jjcobb03.secretsofthearcane.api.essentia;

import net.jjcobb03.secretsofthearcane.api.aspect.Aspect;
import net.jjcobb03.secretsofthearcane.api.aspect.AspectStack;
import net.jjcobb03.secretsofthearcane.magic.aspect.ModAspects;
import net.jjcobb03.secretsofthearcane.component.EssentiaContainerData;

import java.util.HashMap;
import java.util.Map;

import static net.jjcobb03.secretsofthearcane.SecretsOfTheArcane.LOGGER;

public final class EssentiaStorageAdapter {

    private  EssentiaStorageAdapter() {
    }

    /**
     * EssentiaStorage -> EssentiaContainerData
     * @param storage - The EssentiaStorage to be converted
     * @return - The converted EssentiaContainerData
     */
    public static EssentiaContainerData toData(EssentiaStorage storage) {

        Map<String, Integer> contents = new HashMap<>();

        for (AspectStack stack : storage.getContents()) {

            contents.put(
                    stack.getAspect().getId(),
                    stack.getAmount()
            );
        }

        return new EssentiaContainerData(contents);
    }

    /**
     * Data -> Storage
     * @param data - The data to be converted
     * @param capacity - The capacity of the storage to be created
     * @return - The converted EssentiaStorage
     */
    public static EssentiaStorage fromData(EssentiaContainerData data, int capacity) {

        EssentiaStorage storage = new EssentiaStorage(capacity);

        for (Map.Entry<String, Integer> entry : data.contents().entrySet()) {

            Aspect aspect = ModAspects.getAspect(entry.getKey());

            // Log the error
            if (aspect == null) {
                LOGGER.warn(
                        "Unknown aspect '{}' found in EssentiaContainerData",
                        entry.getKey()
                );
                continue;
            }

            storage.insert(new AspectStack(aspect, entry.getValue()), TransferAction.EXECUTE);
        }

        return storage;
    }
}
