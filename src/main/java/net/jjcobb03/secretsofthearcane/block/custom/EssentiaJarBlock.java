package net.jjcobb03.secretsofthearcane.block.custom;

import com.mojang.serialization.MapCodec;
import net.jjcobb03.secretsofthearcane.block.entity.EssentiaJarBlockEntity;
import net.jjcobb03.secretsofthearcane.item.custom.AbstractEssentiaContainerItem;
import net.jjcobb03.secretsofthearcane.magic.aspect.AspectStack;
import net.jjcobb03.secretsofthearcane.magic.aspect.EssentiaTransferHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EssentiaJarBlock extends BaseEntityBlock {

    public static final MapCodec<EssentiaJarBlock> CODEC = simpleCodec(EssentiaJarBlock::new);

    public EssentiaJarBlock (Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state) {

        return new EssentiaJarBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult) {

        if (level.isClientSide()) {
            return ItemInteractionResult.SUCCESS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof EssentiaJarBlockEntity jar)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!(stack.getItem() instanceof AbstractEssentiaContainerItem container)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        boolean transferred = tryTransfer(stack, container, jar);

        if (transferred) {
            jar.setChanged();
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    /**
     * Attempts to transfer Essentia between the held container item and the jar.
     * Empty containers pull Essentia from the jar.
     * Containers with Essentia attempt to insert into the jar.
     *
     * @param stack The held ItemStack.
     * @param container The held Essentia container item.
     * @param jar The targeted Essentia Jar.
     * @return True if any Essentia was transferred.
     */
    private static boolean tryTransfer(
            ItemStack stack,
            AbstractEssentiaContainerItem container,
            EssentiaJarBlockEntity jar) {

        int transferred =
                EssentiaTransferHelper
                        .transferItemToStorage(
                                container,
                                stack,
                                jar.getStorage()
                        );

        if (transferred > 0) {
            return true;
        }

        transferred =
                EssentiaTransferHelper
                        .transferStorageToItem(
                                jar.getStorage(),
                                container,
                                stack
                        );

        return transferred > 0;
    }

    /* Print the contents in chat when shift right-clicked */
    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult
    ) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        // Only activate when sneaking
        if (!player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof EssentiaJarBlockEntity jar)) {
            return InteractionResult.PASS;
        }

        // Show how much storage has been used
        player.sendSystemMessage(Component.literal(
                "Stored: " + jar.getStorage().getStoredAmount() + "/" + jar.getStorage().getCapacity())
        );

        // The jar is empty
        if (jar.getStorage().getContents().isEmpty()) {

            player.sendSystemMessage(Component.literal("No Essentia stored."));

            return InteractionResult.SUCCESS;
        }

        // Print every Aspect stored
        for (AspectStack stack : jar.getStorage().getContents()) {

            player.sendSystemMessage(Component.literal(stack.getAspect().getId() + ": " + stack.getAmount()));

        }

        return InteractionResult.SUCCESS;

    }
}
