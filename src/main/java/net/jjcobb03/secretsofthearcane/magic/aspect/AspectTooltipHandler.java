package net.jjcobb03.secretsofthearcane.magic.aspect;

import net.jjcobb03.secretsofthearcane.SecretsOfTheArcane;
import net.jjcobb03.secretsofthearcane.api.aspect.AspectStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(
        modid = SecretsOfTheArcane.MOD_ID
)
public class AspectTooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {

        // Get the aspects of the item so they can be added to the tooltip
        List<AspectStack> aspects = AspectRegistry.getAspects(event.getItemStack());

        if (aspects.isEmpty()) {
            // There are no Aspects to show
            return;
        }

        List<Component> tooltip = event.getToolTip();

        // Handles the tooltip
        if(Screen.hasShiftDown()) {
            // Show the tooltip
            tooltip.add(Component.literal("Aspects: "));
            // Make sure it gets every aspect assigned to it
            for (AspectStack aspect : aspects) {
                tooltip.add(Component.literal(aspect.getAspect().getId() + ": " + aspect.getAmount()));
            }
        } else {
            // Inform the player they must hold shift to see the Aspects
            tooltip.add(Component.literal("Hold shift for Aspects"));
        }

    }


}
