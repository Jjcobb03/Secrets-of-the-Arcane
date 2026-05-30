package net.jjcobb03.secretsofthearcane.command;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = SecretsOfTheArcane.MOD_ID)
public class CommandEvents {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {

        ModCommands.register(event.getDispatcher());

    }

}
