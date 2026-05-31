package net.jjcobb03.secretsofthearcane.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jjcobb03.secretsofthearcane.block.entity.EssentiaJarBlockEntity;
import net.jjcobb03.secretsofthearcane.item.custom.AbstractEssentiaContainerItem;
import net.jjcobb03.secretsofthearcane.magic.aspect.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("aspect")
                .then(registerBlockCommands())
                .then(registerItemCommands())
        );
    }

    /**
     * Creates and returns the command tree for block-based Essentia commands.
     *
     * Supported commands:
     * <pre>
     * /aspect block info
     * /aspect block add <aspect> <amount>
     * /aspect block extract <aspect> <amount>
     * </pre>
     *
     * @return The root LiteralArgumentBuilder for block commands.
     */
    private static LiteralArgumentBuilder<CommandSourceStack> registerBlockCommands() {

        return Commands.literal("block")

                .then(Commands.literal("info")
                        .executes(ModCommands::blockInfo))

                .then(Commands.literal("add")
                        .then(Commands.argument(
                                        "aspect",
                                        StringArgumentType.word())

                                .then(Commands.argument(
                                                "amount",
                                                IntegerArgumentType.integer(1))

                                        .executes(ModCommands::blockAdd))))

                .then(Commands.literal("extract")
                        .then(Commands.argument(
                                        "aspect",
                                        StringArgumentType.word())

                                .then(Commands.argument(
                                                "amount",
                                                IntegerArgumentType.integer(1))

                                        .executes(ModCommands::blockExtract))));
    }

    /**
     * Creates and returns the command tree for item-based Essentia commands.
     *
     * Supported commands:
     * <pre>
     * /aspect item info
     * /aspect item add <aspect> <amount>
     * /aspect item extract <aspect> <amount>
     * </pre>
     *
     * @return The root LiteralArgumentBuilder for item commands.
     */
    private static LiteralArgumentBuilder<CommandSourceStack> registerItemCommands() {

        return Commands.literal("item")

                .then(Commands.literal("info")
                        .executes(ModCommands::itemInfo))

                .then(Commands.literal("add")
                        .then(Commands.argument(
                                        "aspect",
                                        StringArgumentType.word())

                                .then(Commands.argument(
                                                "amount",
                                                IntegerArgumentType.integer(1))

                                        .executes(ModCommands::itemAdd))))

                .then(Commands.literal("extract")
                        .then(Commands.argument(
                                        "aspect",
                                        StringArgumentType.word())

                                .then(Commands.argument(
                                                "amount",
                                                IntegerArgumentType.integer(1))

                                        .executes(ModCommands::itemExtract))));
    }

    /**
     * Gets the player who executed the command.
     * @param context The command context.
     * @return The player that executed the command.
     * @throws CommandSyntaxException If the command was not executed by a player.
     */
    private static ServerPlayer getPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        return context.getSource().getPlayerOrException();

    }

    /**
     * Retrieves the Aspect specified by the command's aspect argument.
     * @param context The command context.
     * @return The requested Aspect, or null if no matching Aspect exists.
     */
    private static Aspect getAspect(CommandContext<CommandSourceStack> context) {

        String aspectId = StringArgumentType.getString(context, "aspect");
        return ModAspects.getAspect(aspectId);

    }

    /**
     * Retrieves the amount specified by the command's amount argument.
     * @param context The command context.
     * @return The amount provided by the command.
     */
    private static int getAmount(CommandContext<CommandSourceStack> context) {

        return IntegerArgumentType.getInteger(context, "amount");

    }

    /**
     * Gets the Essentia container currently held in the player's main hand.
     * @param player The player executing the command.
     * @return The held Essentia container item, or null if the held item is not
     * an Essentia container.
     */
    private static AbstractEssentiaContainerItem getHeldContainerItem(ServerPlayer player) {

        ItemStack stack = player.getMainHandItem();

        // Item in hand is an Essentia Container
        if (stack.getItem() instanceof AbstractEssentiaContainerItem container) {
            return container;
        }

        // Item in hand is not an Essentia Container
        return null;
    }

    /**
     * Gets the ItemStack currently held in the player's main hand.
     * @param player The player executing the command.
     * @return The ItemStack in the player's main hand.
     */
    private static ItemStack getHeldItem(ServerPlayer player) {

        return player.getMainHandItem();

    }

    /**
     * Gets the Essentia Jar the player is currently looking at.
     * @param player The player executing the command.
     * @return The targeted EssentiaJarBlockEntity, or null if the player is not
     * looking at an Essentia Jar.
     */
    private static EssentiaJarBlockEntity getTargetJar(ServerPlayer player) {

        HitResult hit = player.pick(
                        10.0D,
                        0.0F,
                        false
                );

        // Check if there was a hit
        if (!(hit instanceof BlockHitResult blockHit)) {
            return null;
        }

        BlockEntity blockEntity = player.level().getBlockEntity(blockHit.getBlockPos());

        // Check if the hit was an EssentiaJar
        if (blockEntity instanceof EssentiaJarBlockEntity jar) {
            return jar;
        }

        // Hit block is not an EssentiaJar
        return null;
    }

    /**
     * Displays information about the Essentia currently stored in the targeted
     * Essentia Jar.
     *
     * @param context The command context.
     * @return 1 if successful, otherwise 0.
     * @throws CommandSyntaxException If the command was not executed by a player.
     */
    private static int blockInfo(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        ServerPlayer player = getPlayer(context);

        EssentiaJarBlockEntity jar = getTargetJar(player);

        if (jar == null) {

            context.getSource().sendFailure(
                    Component.literal("You are not looking at an Essentia Jar.")
            );

            return 0;

        }

        EssentiaStorage storage = jar.getStorage();

        context.getSource().sendSuccess(
                () -> Component.literal(
                        "Stored: "
                                + storage.getStoredAmount()
                                + "/"
                                + storage.getCapacity()
                ),
                false
        );

        // Check if empty
        if (storage.getContents().isEmpty()) {
            context.getSource().sendSuccess(
                    () -> Component.literal(
                            "No Essentia stored."
                    ),
                    false
            );

            return 1;
        }

        // List the contents
        for (AspectStack stack : storage.getContents()) {

            context.getSource().sendSuccess(
                    () -> Component.literal(
                            stack.getAspect().getId()
                                    + ": "
                                    + stack.getAmount()
                    ),
                    false
            );
        }

        return 1;
    }

    /**
     * Inserts the specified amount of an Aspect into the targeted Essentia Jar.
     *
     * @param context The command context.
     * @return The amount of Essentia successfully inserted.
     * @throws CommandSyntaxException If the command was not executed by a player.
     */
    private static int blockAdd(
            CommandContext<CommandSourceStack> context)
            throws CommandSyntaxException {

        ServerPlayer player = getPlayer(context);

        EssentiaJarBlockEntity jar =
                getTargetJar(player);

        if (jar == null) {

            context.getSource().sendFailure(
                    Component.literal(
                            "You are not looking at an Essentia Jar."
                    )
            );

            return 0;
        }

        Aspect aspect =
                getAspect(context);

        if (aspect == null) {

            context.getSource().sendFailure(
                    Component.literal(
                            "Unknown Aspect."
                    )
            );

            return 0;
        }

        int amount =
                getAmount(context);

        int inserted =
                jar.getStorage().insert(
                        new AspectStack(
                                aspect,
                                amount
                        ),
                        TransferAction.EXECUTE
                );

        jar.setChanged();

        context.getSource().sendSuccess(
                () -> Component.literal(
                        "Inserted "
                                + inserted
                                + " "
                                + aspect.getId()
                ),
                false
        );

        return inserted;
    }

    /**
     * Extracts the specified amount of an Aspect from the targeted Essentia Jar.
     *
     * @param context The command context.
     * @return The amount of Essentia successfully extracted.
     * @throws CommandSyntaxException If the command was not executed by a player.
     */
    private static int blockExtract(
            CommandContext<CommandSourceStack> context)
            throws CommandSyntaxException {

        ServerPlayer player = getPlayer(context);

        EssentiaJarBlockEntity jar =
                getTargetJar(player);

        if (jar == null) {

            context.getSource().sendFailure(
                    Component.literal(
                            "You are not looking at an Essentia Jar."
                    )
            );

            return 0;
        }

        Aspect aspect =
                getAspect(context);

        if (aspect == null) {

            context.getSource().sendFailure(
                    Component.literal(
                            "Unknown Aspect."
                    )
            );

            return 0;
        }

        int amount =
                getAmount(context);

        AspectStack extracted =
                jar.getStorage().extract(
                        aspect,
                        amount,
                        TransferAction.EXECUTE
                );

        jar.setChanged();

        context.getSource().sendSuccess(
                () -> Component.literal(
                        "Extracted "
                                + extracted.getAmount()
                                + " "
                                + aspect.getId()
                ),
                false
        );

        return extracted.getAmount();
    }

    /**
     * Displays information about the Essentia currently stored in the held
     * Essentia container item.
     *
     * @param context The command context.
     * @return 1 if successful, otherwise 0.
     * @throws CommandSyntaxException If the command was not executed by a player.
     */
    private static int itemInfo(
            CommandContext<CommandSourceStack> context)
            throws CommandSyntaxException {

        ServerPlayer player = getPlayer(context);

        AbstractEssentiaContainerItem container =
                getHeldContainerItem(player);

        if (container == null) {

            context.getSource().sendFailure(
                    Component.literal(
                            "You must be holding an Essentia container."
                    )
            );

            return 0;
        }

        ItemStack itemStack =
                getHeldItem(player);

        EssentiaStorage storage =
                container.getStorage(itemStack);

        context.getSource().sendSuccess(
                () -> Component.literal(
                        "Stored: "
                                + storage.getStoredAmount()
                                + "/"
                                + storage.getCapacity()
                ),
                false
        );

        if (storage.getContents().isEmpty()) {

            context.getSource().sendSuccess(
                    () -> Component.literal(
                            "No Essentia stored."
                    ),
                    false
            );

            return 1;
        }

        for (AspectStack stack : storage.getContents()) {

            context.getSource().sendSuccess(
                    () -> Component.literal(
                            stack.getAspect().getId()
                                    + ": "
                                    + stack.getAmount()
                    ),
                    false
            );
        }

        return 1;
    }

    /**
     * Inserts the specified amount of an Aspect into the held Essentia container
     * item.
     *
     * @param context The command context.
     * @return The amount of Essentia successfully inserted.
     * @throws CommandSyntaxException If the command was not executed by a player.
     */
    private static int itemAdd(
            CommandContext<CommandSourceStack> context)
            throws CommandSyntaxException {

        ServerPlayer player = getPlayer(context);

        AbstractEssentiaContainerItem container =
                getHeldContainerItem(player);

        if (container == null) {

            context.getSource().sendFailure(
                    Component.literal(
                            "You must be holding an Essentia container."
                    )
            );

            return 0;
        }

        Aspect aspect =
                getAspect(context);

        if (aspect == null) {

            context.getSource().sendFailure(
                    Component.literal(
                            "Unknown Aspect."
                    )
            );

            return 0;
        }

        int amount =
                getAmount(context);

        int inserted =
                container.insert(
                        getHeldItem(player),
                        new AspectStack(
                                aspect,
                                amount
                        ),
                        TransferAction.EXECUTE
                );

        context.getSource().sendSuccess(
                () -> Component.literal(
                        "Inserted "
                                + inserted
                                + " "
                                + aspect.getId()
                ),
                false
        );

        return inserted;
    }

    /**
     * Extracts the specified amount of an Aspect from the held Essentia container
     * item.
     *
     * @param context The command context.
     * @return The amount of Essentia successfully extracted.
     * @throws CommandSyntaxException If the command was not executed by a player.
     */
    private static int itemExtract(
            CommandContext<CommandSourceStack> context)
            throws CommandSyntaxException {

        ServerPlayer player = getPlayer(context);

        AbstractEssentiaContainerItem container =
                getHeldContainerItem(player);

        if (container == null) {

            context.getSource().sendFailure(
                    Component.literal(
                            "You must be holding an Essentia container."
                    )
            );

            return 0;
        }

        Aspect aspect =
                getAspect(context);

        if (aspect == null) {

            context.getSource().sendFailure(
                    Component.literal(
                            "Unknown Aspect."
                    )
            );

            return 0;
        }

        int amount =
                getAmount(context);

        AspectStack extracted =
                container.extract(
                        getHeldItem(player),
                        aspect,
                        amount,
                        TransferAction.EXECUTE
                );

        context.getSource().sendSuccess(
                () -> Component.literal(
                        "Extracted "
                                + extracted.getAmount()
                                + " "
                                + aspect.getId()
                ),
                false
        );

        return extracted.getAmount();
    }
}