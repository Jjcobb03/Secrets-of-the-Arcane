package net.jjcobb03.secretsofthearcane.datagen;

import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.jjcobb03.secretsofthearcane.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

/**
 * Helps create block loot tables without the need to manually create a json file each time.
 */
public class ModBlockLootTableProvider extends BlockLootSubProvider {

    // Constructor
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    // Generates the Loot Tables
    @Override
    protected void generate() {
        // Blocks that drop themselves
        dropSelf(ModBlocks.ROWAN_LOG.get());
        dropSelf(ModBlocks.ROWAN_PLANKS.get());
        dropSelf(ModBlocks.ROWAN_STAIRS.get());
        dropSelf(ModBlocks.ROWAN_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.ROWAN_BUTTON.get());
        dropSelf(ModBlocks.ROWAN_FENCE.get());
        dropSelf(ModBlocks.ROWAN_FENCE_GATE.get());
        dropSelf(ModBlocks.ROWAN_WALL.get());
        dropSelf(ModBlocks.ROWAN_TRAPDOOR.get());

        // Unique due to double slab dropping 2 slabs
        add(ModBlocks.ROWAN_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ROWAN_SLAB.get()));

        // Unique for being a multiblock with 1 drop
        add(ModBlocks.ROWAN_DOOR.get(),
                block -> createDoorTable(ModBlocks.ROWAN_DOOR.get()));



        // Blocks that drop a different item from themselves (EX: Ores)
        // Drops a single crystal
        add(ModBlocks.TERRA_ORE.get(),
                block -> createOreDrop(ModBlocks.TERRA_ORE.get(), ModItems.VIS_CRYSTAL.get()));
        // Drops 1-3 crystals
        add(ModBlocks.TERRA_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(
                        ModBlocks.TERRA_DEEPSLATE_ORE.get(),
                        ModItems.VIS_CRYSTAL.get(),
                        1, 3));
    }

    /**
     * Helper method from Kaupenjoe's datagen tutorial
     * @param pBlock - The block being mined
     * @param item - The item that should be dropped
     * @param minDrops - The minimum amount of drops this should provide
     * @param maxDrops - The maximum amount of drops this can provide
     * @return A LootTable.Builder. Honestly not entirely sure what exactly that does
     */
    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    /**
     * Gets a list of all blocks on the BLOCKS DeferredRegister.
     * Every block on the register needs a loot table unless created with .noLootTable()
     *
     * @return every block on the BLOCKS DeferredRegister as an Iterable<Block>
     */
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
