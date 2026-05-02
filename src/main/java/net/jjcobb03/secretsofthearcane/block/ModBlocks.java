package net.jjcobb03.secretsofthearcane.block;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "secretsofthearcane" namespace
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(SecretsOfTheArcane.MOD_ID);

    // Custom blocks to register

    /**
     * Rowan Wood set, with some notes on how each block type works
     */

    // Rowan Log, uses RotatedPillarBlock since it can be placed directionally
    public static final DeferredBlock<Block> ROWAN_LOG = registerBlock("rowan_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.WOOD)));
    // Rowan Planks, uses Block because there's nothing special about it
    public static final DeferredBlock<Block> ROWAN_PLANKS = registerBlock("rowan_planks",
            () -> new Block(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.WOOD)));
    // Rowan Stairs, uses StairBlock, needs to reference an existing block's blockstate(?)
    public static final DeferredBlock<StairBlock> ROWAN_STAIRS = registerBlock("rowan_stairs",
            () -> new StairBlock(ModBlocks.ROWAN_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(1f)));
    // Rowan Slab, uses SlabBlock
    public static final DeferredBlock<SlabBlock> ROWAN_SLAB = registerBlock("rowan_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1f)));
    // Rowan Pressure Plate, uses PressurePlateBlock, needs BlockSetType
    public static final DeferredBlock<PressurePlateBlock> ROWAN_PRESSURE_PLATE = registerBlock("rowan_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.of().strength(1f)));
    // Rowan Button, uses ButtonBlock, needs BlockSetType, ticks to stay pressed, and should have no noCollission
    public static final DeferredBlock<ButtonBlock> ROWAN_BUTTON = registerBlock("rowan_button",
            () -> new ButtonBlock(BlockSetType.OAK, 20,
                    BlockBehaviour.Properties.of().strength(1f).noCollission()));
    // Rowan Fence, uses FenceBlock
    public static final DeferredBlock<FenceBlock> ROWAN_FENCE = registerBlock("rowan_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(1f)));
    // Rowan Fence Gate, uses FenceGateBlock and needs WoodType
    public static final DeferredBlock<FenceGateBlock> ROWAN_FENCE_GATE = registerBlock("rowan_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK ,BlockBehaviour.Properties.of().strength(1f)));
    // Rowan Wall (temporary), uses WallBlock
    public static final DeferredBlock<WallBlock> ROWAN_WALL = registerBlock("rowan_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(1f)));
    // Rowan Door, uses DoorBlock, needs BlockSetType. noOcclusion() allows for light to pass through
    public static final DeferredBlock<DoorBlock> ROWAN_DOOR = registerBlock("rowan_door",
            () -> new DoorBlock(BlockSetType.OAK ,BlockBehaviour.Properties.of().strength(1f).noOcclusion()));
    // Rowan Trap Door, uses TrapDoorBlock, needs BlockSetType
    public static final DeferredBlock<TrapDoorBlock> ROWAN_TRAPDOOR = registerBlock("rowan_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK ,BlockBehaviour.Properties.of().strength(1f).noOcclusion()));

    // Vis Crystal Ores
    public static final DeferredBlock<Block> TERRA_ORE = registerBlock("terra_ore",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> TERRA_DEEPSLATE_ORE = registerBlock("terra_deepslate_ore",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1f).sound(SoundType.DEEPSLATE)));

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
