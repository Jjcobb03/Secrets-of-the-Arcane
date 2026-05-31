package net.jjcobb03.secretsofthearcane.block.entity;

import net.jjcobb03.secretsofthearcane.magic.aspect.EssentiaStorage;
import net.jjcobb03.secretsofthearcane.magic.aspect.SingleAspectEssentiaStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EssentiaJarBlockEntity extends BlockEntity {

    // The part that holds our Essentia
    private final SingleAspectEssentiaStorage storage = new SingleAspectEssentiaStorage(50);

    public EssentiaJarBlockEntity(BlockPos pos, BlockState state) {

        super(ModBlockEntities.ESSENTIA_JAR.get(), pos, state);

    }

    public EssentiaStorage getStorage() {
        return storage;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {

        super.saveAdditional(tag, registries);

        tag.put("EssentiaStorage", storage.save());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {

        super.loadAdditional(tag, registries);

        if (tag.contains("EssentiaStorage")) {
            storage.load(tag.getCompound("EssentiaStorage"));
        }
    }
}
