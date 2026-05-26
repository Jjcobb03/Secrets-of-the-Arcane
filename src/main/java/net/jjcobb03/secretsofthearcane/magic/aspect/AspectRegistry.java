package net.jjcobb03.secretsofthearcane.magic.aspect;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains the methods needed to assign aspects to an item
 */
public class AspectRegistry {

    // Holds all the information regarding which aspects are assigned to an item, and how much of those aspects
    private static final Map<Item, List<AspectStack>> ITEM_ASPECTS = new HashMap<>();

    /**
     * Used to assign a set of Aspects to an Item
     * @param item - The item that the Aspects will be assigned to
     * @param aspects - A list of AspectStacks that will be assigned to the item
     */
    public static void register(Item item, AspectStack... aspects) {
        ITEM_ASPECTS.put(item, List.of(aspects));
    }

    /**
     * Used to get the list of aspects from an Item
     * @param stack - The Itemstack that is being checked
     * @return - A List<AspectStack> containing every aspect in the item
     */
    public static List<AspectStack> getAspects(ItemStack stack) {
        return ITEM_ASPECTS.getOrDefault(stack.getItem(), List.of());
    }
}
