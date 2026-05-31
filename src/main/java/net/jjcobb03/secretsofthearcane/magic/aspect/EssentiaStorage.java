package net.jjcobb03.secretsofthearcane.magic.aspect;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

public class EssentiaStorage implements IEssentiaContainer {

    private final int capacity;

    protected final Map<Aspect, Integer> contents;

    public EssentiaStorage(int capacity) {
        this.capacity = capacity;
        this.contents = new HashMap<>();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getStoredAmount() {
        // Count the total units of storage used
        return contents.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public int getAmount(Aspect aspect) {
        return contents.getOrDefault(aspect, 0);
    }

    @Override
    public Collection<AspectStack> getContents() {
        // Convert our mapped Aspects to a List of AspectStacks
        return contents.entrySet().stream().map(
                entry -> new AspectStack(entry.getKey(), entry.getValue())).toList();
    }

    @Override
    public boolean acceptsAspect(Aspect aspect) {
        // Specific Implementations will override this
        return true;
    }

    @Override
    public int insert(AspectStack stack, TransferAction action) {

        // Check if that type of Aspect can be inserted
        if (!acceptsAspect(stack.getAspect())) {
            // Unable to accept the Aspect, nothing gets inserted
            return 0;
        }

        // Calculate how much gets inserted
        int availableSpace = capacity - getStoredAmount();

        // Check if there's storage space
        if (availableSpace <= 0) {
            return 0;
        }

        // Calculate the amount transferred
        int accepted = Math.min(stack.getAmount(), availableSpace);

        // Add the Essentia to the container
        if (action == TransferAction.EXECUTE) {
            // Save current amount for addition
            int currentAmount = contents.getOrDefault(stack.getAspect(), 0);
            // Calculate the new contents
            contents.put(stack.getAspect(), currentAmount + accepted);
        }

        // Return the amount that was accepted
        return accepted;
    }

    @Override
    public AspectStack extract(Aspect aspect, int amount, TransferAction action) {

        // See how much of the desired Aspect is currently available
        int storedAmount = contents.getOrDefault(aspect, 0);

        // Check if the desired type has any stored
        if (storedAmount <= 0) {
            // Return an empty AspectStack of the proper type
            return new AspectStack(aspect, 0);
        }

        // How much is extracted
        int extracted = min(storedAmount, amount);

        // Extract the Essentia
        if (action == TransferAction.EXECUTE) {

            int remaining = storedAmount - extracted;

            if (remaining <= 0) {
                // If all of an Aspect was removed, remove that Aspect from contents
                contents.remove(aspect);
            } else {
                // Update contents with the new value
                contents.put(aspect, remaining);
            }
        }

        // Return the extracted amount as an AspectStack
        return new AspectStack(aspect, extracted);
    }


    /**
     * Save the data
     * @return - A CompoundTag containing information about the stored Essentia
     */
    public CompoundTag save() {

        CompoundTag tag = new CompoundTag();

        ListTag aspectList = new ListTag();

        for (Map.Entry<Aspect, Integer> entry : contents.entrySet()) {

            CompoundTag aspectTag = new CompoundTag();

            aspectTag.putString("Aspect", entry.getKey().getId());

            aspectTag.putInt("Amount", entry.getValue());

            aspectList.add(aspectTag);
        }

        tag.put("Essentia", aspectList);

        return tag;
    }

    /**
     * Loads data from a CompoundTag
     * @param tag - The Tag which the data is loaded from
     */
    public void load(CompoundTag tag) {

        contents.clear();

        ListTag aspectList = tag.getList("Essentia", CompoundTag.TAG_COMPOUND);

        for (int i = 0; i < aspectList.size(); i++) {

            CompoundTag aspectTag = aspectList.getCompound(i);

            String aspectId = aspectTag.getString("Aspect");

            int amount = aspectTag.getInt("Amount");

            Aspect aspect = ModAspects.getAspect(aspectId);

            // Add the aspect and amount to the container's contents
            if (aspect != null && amount > 0) {
                contents.put(aspect, amount);
            }
        }
    }

    /**
     * Returns true if the container currently has no stored Essentia.
     */
    public boolean isEmpty() {
        return contents.isEmpty();
    }

    /**
     * Gets the first stored Aspect.
     *
     * @return The stored Aspect, or null if empty.
     */
    public Aspect getStoredAspect() {

        if (contents.isEmpty()) {
            return null;
        }

        return contents.keySet()
                .iterator()
                .next();
    }

}
