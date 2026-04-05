package net.jjcobb03.secretsofthearcane.item;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    // Create a Deferred Register to hold Items which will all be registered under the "secretsofthearcane" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SecretsOfTheArcane.MOD_ID);

    // Test item that will eventually be removed or replaced
    public static final DeferredItem<Item> TEST_ITEM = ITEMS.register("test_item",
            () -> new Item(new Item.Properties()));

    // Register the Deferred Register to the mod event bus so items get registered
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
