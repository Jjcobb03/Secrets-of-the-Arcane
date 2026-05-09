package net.jjcobb03.secretsofthearcane.block.custom;

import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

/**
 * A class for RotatedPillarBlocks that are flammable. Ex: Wood Logs
 */
public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {

    public ModFlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }

    // Label this block as flammable
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    // Set the flammability value to 5
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    // Set the fire spread speed value to 5
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    // Used for stripping logs
    @Override
    public @Nullable BlockState getToolModifiedState(
            BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {

        // If more blocks get added, this should be changed from multiple if statements to a map
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            if(state.is(ModBlocks.ROWAN_LOG)) {
                return ModBlocks.STRIPPED_ROWAN_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if(state.is(ModBlocks.ROWAN_WOOD)) {
                return ModBlocks.STRIPPED_ROWAN_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
