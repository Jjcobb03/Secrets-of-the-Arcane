package net.jjcobb03.secretsofthearcane.block;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "secretsofthearcane" namespace
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(SecretsOfTheArcane.MOD_ID);

    // Custom blocks to register
    public static final DeferredBlock<Block> ROWAN_LOG = registerBlock("rowan_log",
            () -> new Block(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> ROWAN_PLANKS = registerBlock("rowan_planks",
            () -> new Block(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.WOOD)));

    // Registers the Block and the BlockItem
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // Helper method that creates the BlockItem
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Register the Deferred Register to the mod event bus so blocks get registered
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

    }

}
