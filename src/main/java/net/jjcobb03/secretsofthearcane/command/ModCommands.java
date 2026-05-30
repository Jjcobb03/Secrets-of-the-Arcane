package net.jjcobb03.secretsofthearcane.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.jjcobb03.secretsofthearcane.block.entity.EssentiaJarBlockEntity;
import net.jjcobb03.secretsofthearcane.magic.aspect.Aspect;
import net.jjcobb03.secretsofthearcane.magic.aspect.AspectStack;
import net.jjcobb03.secretsofthearcane.magic.aspect.ModAspects;
import net.jjcobb03.secretsofthearcane.magic.aspect.TransferAction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("aspect")
                        .requires(source -> source.hasPermission(2))

                        // /aspect add <aspect> <amount>
                        .then(
                                Commands.literal("add")
                                        .then(
                                                Commands.argument(
                                                                "aspect",
                                                                StringArgumentType.word())
                                                        .then(
                                                                Commands.argument(
                                                                                "amount",
                                                                                IntegerArgumentType.integer(1))
                                                                        .executes(context -> {

                                                                            String aspectId =
                                                                                    StringArgumentType.getString(
                                                                                            context,
                                                                                            "aspect");

                                                                            int amount =
                                                                                    IntegerArgumentType.getInteger(
                                                                                            context,
                                                                                            "amount");

                                                                            Aspect aspect =
                                                                                    ModAspects.getAspect(aspectId);

                                                                            if (aspect == null) {
                                                                                context.getSource().sendFailure(
                                                                                        Component.literal(
                                                                                                "Unknown aspect: "
                                                                                                        + aspectId));
                                                                                return 0;
                                                                            }

                                                                            ServerPlayer player =
                                                                                    context.getSource()
                                                                                            .getPlayerOrException();

                                                                            HitResult hit =
                                                                                    player.pick(
                                                                                            10.0D,
                                                                                            0.0F,
                                                                                            false);

                                                                            if (!(hit instanceof BlockHitResult blockHit)) {
                                                                                context.getSource().sendFailure(
                                                                                        Component.literal(
                                                                                                "You must be looking at an Essentia Jar."));
                                                                                return 0;
                                                                            }

                                                                            BlockEntity blockEntity =
                                                                                    player.level()
                                                                                            .getBlockEntity(
                                                                                                    blockHit.getBlockPos());

                                                                            if (!(blockEntity instanceof EssentiaJarBlockEntity jar)) {
                                                                                context.getSource().sendFailure(
                                                                                        Component.literal(
                                                                                                "You must be looking at an Essentia Jar."));
                                                                                return 0;
                                                                            }

                                                                            int inserted =
                                                                                    jar.getStorage().insert(
                                                                                            new AspectStack(
                                                                                                    aspect,
                                                                                                    amount),
                                                                                            TransferAction.EXECUTE);

                                                                            jar.setChanged();

                                                                            context.getSource().sendSuccess(
                                                                                    () -> Component.literal(
                                                                                            "Inserted "
                                                                                                    + inserted
                                                                                                    + " "
                                                                                                    + aspect.getId()
                                                                                                    + " essentia."),
                                                                                    true);

                                                                            return inserted;
                                                                        })
                                                        )
                                        )
                        )

                        // /aspect extract
                        .then(
                                Commands.literal("extract")
                                        .then(
                                                Commands.argument(
                                                                "aspect",
                                                                StringArgumentType.word())
                                                        .then(
                                                                Commands.argument(
                                                                                "amount",
                                                                                IntegerArgumentType.integer(1))
                                                                        .executes(context -> {

                                                                            String aspectId =
                                                                                    StringArgumentType.getString(
                                                                                            context,
                                                                                            "aspect");

                                                                            int amount =
                                                                                    IntegerArgumentType.getInteger(
                                                                                            context,
                                                                                            "amount");

                                                                            Aspect aspect =
                                                                                    ModAspects.getAspect(aspectId);

                                                                            if (aspect == null) {
                                                                                context.getSource().sendFailure(
                                                                                        Component.literal(
                                                                                                "Unknown aspect: "
                                                                                                        + aspectId));
                                                                                return 0;
                                                                            }

                                                                            ServerPlayer player =
                                                                                    context.getSource()
                                                                                            .getPlayerOrException();

                                                                            HitResult hit =
                                                                                    player.pick(
                                                                                            10.0D,
                                                                                            0.0F,
                                                                                            false);

                                                                            if (!(hit instanceof BlockHitResult blockHit)) {
                                                                                context.getSource().sendFailure(
                                                                                        Component.literal(
                                                                                                "You must be looking at an Essentia Jar."));
                                                                                return 0;
                                                                            }

                                                                            BlockEntity blockEntity =
                                                                                    player.level()
                                                                                            .getBlockEntity(
                                                                                                    blockHit.getBlockPos());

                                                                            if (!(blockEntity instanceof EssentiaJarBlockEntity jar)) {
                                                                                context.getSource().sendFailure(
                                                                                        Component.literal(
                                                                                                "You must be looking at an Essentia Jar."));
                                                                                return 0;
                                                                            }

                                                                            AspectStack extracted =
                                                                                    jar.getStorage().extract(
                                                                                            aspect,
                                                                                            amount,
                                                                                            TransferAction.EXECUTE);

                                                                            jar.setChanged();

                                                                            context.getSource().sendSuccess(
                                                                                    () -> Component.literal(
                                                                                            "Extracted "
                                                                                                    + extracted.getAmount()
                                                                                                    + " "
                                                                                                    + aspect.getId()
                                                                                                    + " essentia."),
                                                                                    true);

                                                                            return extracted.getAmount();
                                                                        })
                                                        )
                                        )
                        )

                        // /aspect info
                        .then(
                                Commands.literal("info")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            HitResult hit =
                                                    player.pick(
                                                            10.0D,
                                                            0.0F,
                                                            false);

                                            if (!(hit instanceof BlockHitResult blockHit)) {
                                                context.getSource().sendFailure(
                                                        Component.literal(
                                                                "You must be looking at an Essentia Jar."));
                                                return 0;
                                            }

                                            BlockEntity blockEntity =
                                                    player.level()
                                                            .getBlockEntity(
                                                                    blockHit.getBlockPos());

                                            if (!(blockEntity instanceof EssentiaJarBlockEntity jar)) {
                                                context.getSource().sendFailure(
                                                        Component.literal(
                                                                "You must be looking at an Essentia Jar."));
                                                return 0;
                                            }

                                            context.getSource().sendSuccess(
                                                    () -> Component.literal(
                                                            "Stored: "
                                                                    + jar.getStorage().getStoredAmount()
                                                                    + "/"
                                                                    + jar.getStorage().getCapacity()),
                                                    false);

                                            if (jar.getStorage().getContents().isEmpty()) {

                                                context.getSource().sendSuccess(
                                                        () -> Component.literal(
                                                                "No Essentia stored."),
                                                        false);

                                                return 1;
                                            }

                                            for (AspectStack stack :
                                                    jar.getStorage().getContents()) {

                                                context.getSource().sendSuccess(
                                                        () -> Component.literal(
                                                                stack.getAspect().getId()
                                                                        + ": "
                                                                        + stack.getAmount()),
                                                        false);
                                            }

                                            return 1;
                                        })
                        )
        );
    }
}