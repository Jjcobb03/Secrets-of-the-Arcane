package net.jjcobb03.secretsofthearcane.api.item;

import net.jjcobb03.secretsofthearcane.api.ArcaneApi;
import net.jjcobb03.secretsofthearcane.api.aspect.AspectContents;
import net.jjcobb03.secretsofthearcane.api.aspect.AspectStack;
import net.jjcobb03.secretsofthearcane.api.aspect.IAspectContainer;
import net.jjcobb03.secretsofthearcane.api.aspect.ItemAspectContainer;
import net.jjcobb03.secretsofthearcane.api.aspect.SimpleAspectContainer;
import net.jjcobb03.secretsofthearcane.api.component.ArcaneDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Base class for items that store aspects. Subclasses only define their capacity rules.
 * Provides the fill bar, the liquid color used for tinting, and a tooltip.
 */
public abstract class AspectContainerItem extends Item {

    // Item model predicate: 1 when the item holds anything, 0 when empty. Used to swap in the "filled" model
    public static final ResourceLocation FILLED_PROPERTY = ArcaneApi.id("filled");

    private int cachedCapacity = -1;

    protected AspectContainerItem(Properties properties) {
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

    // How a stored amount is shown to the player. Vis-style items can override this to divide by VisUnits.VIS_SCALE
    protected String formatAmount(int amount) {
        return String.valueOf(amount);
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
                    .append(Component.literal(" x" + formatAmount(aspectStack.amount()))));
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }
}