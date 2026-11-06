package net.jjcobb03.secretsofthearcane.api.aspect;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

/**
 * What an item stores
 *
 * @param stacks
 */
public record AspectContents(List<AspectStack> stacks) {

    public static final AspectContents EMPTY = new AspectContents(List.of());

    public static Codec<AspectContents> CODEC =
            AspectStack.CODEC.listOf().xmap(AspectContents::new, AspectContents::stacks);

    public static final StreamCodec<RegistryFriendlyByteBuf, AspectContents> STREAM_CODEC =
            AspectStack.STREAM_CODEC.apply(ByteBufCodecs.list()).map(AspectContents::new, AspectContents::stacks);

    public AspectContents {
        stacks = List.copyOf(stacks);
    }

    public boolean isEmpty() {
        return stacks.isEmpty();
    }

    public int totalAmount() {
        int total = 0;

        for (AspectStack stack : stacks) {
            total += stack.amount();
        }

        return total;
    }

    // Somewhat experimental, and may be replaced by something that gets the color of the Aspect using the most storage
    // RGB average of the stored aspects' colors, weighted by amount. A single aspect gives its exact color
    public int getBlendedColor() {
        long r = 0, g = 0, b = 0, weight = 0;
        for (AspectStack stack : stacks) {
            int color = stack.aspect().getColor();
            int w = stack.amount();
            r += (long) ((color >> 16) & 0xFF) * w;
            g += (long) ((color >> 8) & 0xFF) * w;
            b += (long) (color & 0xFF) * w;
            weight += w;
        }
        if (weight == 0) {
            return 0xFFFFFF;
        }
        return ((int) (r / weight) << 16) | ((int) (g / weight) << 8) | (int) (b / weight);
    }
}
