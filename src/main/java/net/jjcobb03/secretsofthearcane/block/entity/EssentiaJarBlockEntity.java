package net.jjcobb03.secretsofthearcane.block.entity;

import net.jjcobb03.secretsofthearcane.api.aspect.SimpleAspectContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EssentiaJarBlockEntity extends BlockEntity {

    // Placeholder capacity, tune this once the design settles
    public static final int CAPACITY = 64;

    private final SimpleAspectContainer storage = SimpleAspectContainer.singleAspect(CAPACITY);

    public EssentiaJarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENTIA_JAR.get(), pos, state);
        storage.setChangeListener(this::onContentsChanged);
    }

    public SimpleAspectContainer getStorage() {
        return storage;
    }

    // Marks the block for saving, and tells nearby clients so they can show the new contents
    private void onContentsChanged() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("aspects", storage.save());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        storage.load(tag.getList("aspects", Tag.TAG_COMPOUND));
    }

    // Client sync: send the same data we save
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}