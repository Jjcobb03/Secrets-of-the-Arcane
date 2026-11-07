package net.jjcobb03.secretsofthearcane.api.item;

import net.jjcobb03.secretsofthearcane.api.ArcaneApi;
import net.jjcobb03.secretsofthearcane.api.aspect.*;
import net.jjcobb03.secretsofthearcane.api.component.ArcaneDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * Base class for items that store Essentia. Subclasses only define their capacity rules.
 * Provides the fill bar, the liquid color used for tinting, and a tooltip.
 */
public abstract class EssentiaContainerItem extends Item {

    // Item model predicate: 1 when the item holds anything, 0 when empty. Used to swap in the "filled" model
    public static final ResourceLocation FILLED_PROPERTY = ArcaneApi.id("filled");

    private int cachedCapacity = -1;

    protected EssentiaContainerItem(Properties properties) {
        super(properties);
    }

    // A fresh, empty container with this item's capacity rules
    public abstract SimpleAspectContainer createEmptyStorage();

    public AspectContents getContents(ItemStack stack) {
        return stack.getOrDefault(ArcaneDataComponents.ASPECT_CONTENTS.get(), AspectContents.EMPTY);
    }

    // Use this for anything that moves aspects in or out, including transfers
    public IAspectContainer getContainer(ItemStack stack) {
        return new ItemAspectContainer(stack, createEmptyStorage());
    }

    // 0.0 (empty) to 1.0 (full)
    public float getFillFraction(ItemStack stack) {
        if (cachedCapacity < 0) {
            cachedCapacity = createEmptyStorage().getCapacity();
        }
        if (cachedCapacity == 0 || cachedCapacity == SimpleAspectContainer.UNLIMITED) {
            return 0f;
        }
        return Math.min(1f, (float) getContents(stack).totalAmount() / cachedCapacity);
    }

    // Color of the liquid, as RGB. Used for tinting the item and the bar
    public int getLiquidColor(ItemStack stack) {
        return getContents(stack).getBlendedColor();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !getContents(stack).isEmpty();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        // The bar is 13 pixels wide, same as the durability bar
        return Math.max(1, Math.round(13.0F * getFillFraction(stack)));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return getLiquidColor(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        for (AspectStack aspectStack : getContents(stack).stacks()) {
            int color = aspectStack.aspect().getColor();
            tooltip.add(Component.empty()
                    .append(aspectStack.aspect().getDisplayName().copy().withStyle(style -> style.withColor(color)))
                    .append(Component.literal(" x" + aspectStack.amount())));
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }

    // Handles transferring Essentia on right click
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        IAspectContainer target = level.getCapability(
                AspectCapabilities.ASPECT_CONTAINER, context.getClickedPos(), context.getClickedFace());
        if (target == null) {
            return InteractionResult.PASS; // not a container block, let normal interaction continue
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }

        ItemStack held = context.getItemInHand();
        // Work on a single vial so a stack of several isn't all changed at once
        ItemStack working = held.getCount() > 1 ? held.copyWithCount(1) : held;
        IAspectContainer self = getContainer(working);

        // Empty vials draw, filled vials pour. Sneaking flips it
        boolean draw = getContents(working).isEmpty();
        if (player.isShiftKeyDown()) {
            draw = !draw;
        }

        int moved = draw
                ? AspectTransfer.transferAll(target, self)
                : AspectTransfer.transferAll(self, target);

        if (moved > 0) {
            level.playSound(null, context.getClickedPos(),
                    draw ? SoundEvents.BOTTLE_FILL : SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);

            // If we split one off, take it out of the stack and give the changed vial back
            if (working != held) {
                held.shrink(1);
                if (!player.getInventory().add(working)) {
                    player.drop(working, false);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}