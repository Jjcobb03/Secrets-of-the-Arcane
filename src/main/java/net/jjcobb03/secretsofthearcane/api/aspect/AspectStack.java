package net.jjcobb03.secretsofthearcane.api.aspect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jjcobb03.secretsofthearcane.api.registry.ArcaneRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.Objects;

/**
 * An aspect plus an amount. Used for both essentia and vis; what the amount means depends on the container
 * (essentia: 1 per essentia, vis: see VisUnits). Immutable, so changes return a new stack.
 */
public record AspectStack(Aspect aspect, int amount) {

    // Saved to disk as a JSON:
    // {"aspect": "secretsofthearcane:ignis", "amount": 5}
    public static final Codec<AspectStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ArcaneRegistries.ASPECTS.byNameCodec().fieldOf("aspect").forGetter(AspectStack::aspect),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(AspectStack::amount)
    ).apply(instance, AspectStack::new));

    // Sent over the network. Aspects are written as registry ids, amounts as variable-length ints
    public static final StreamCodec<RegistryFriendlyByteBuf, AspectStack> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(ArcaneRegistries.ASPECT_KEY), AspectStack::aspect,
            ByteBufCodecs.VAR_INT, AspectStack::amount,
            AspectStack::new
    );

    // Validates on construction (this also runs when decoding, so bad data fails early)
    public AspectStack {
        Objects.requireNonNull(aspect, "AspectStack requires a non-null Aspect");
        if (amount < 0) {
            throw new IllegalArgumentException("Aspect amount cannot be negative: " + amount);
        }
    }

    public boolean isEmpty() {
        return amount == 0;
    }

    public AspectStack withAmount(int newAmount) {
        return new AspectStack(aspect, newAmount);
    }

    // Adds to the amount (negative values subtract, but the result must stay at or above 0)
    public AspectStack add(int extra) {
        return new AspectStack(aspect, amount + extra);
    }

    // Removes up to 'removed' from the amount, stopping at 0 instead of throwing
    public AspectStack subtract(int removed) {
        return new AspectStack(aspect, Math.max(0, amount - removed));
    }

    // True if both stacks hold the same aspect, regardless of amount (used when merging into containers)
    public boolean isSameAspect(AspectStack other) {
        return aspect == other.aspect;
    }
}