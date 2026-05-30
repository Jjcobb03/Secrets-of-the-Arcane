package net.jjcobb03.secretsofthearcane.block.custom;

import com.mojang.serialization.MapCodec;
import net.jjcobb03.secretsofthearcane.block.entity.EssentiaJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
}
