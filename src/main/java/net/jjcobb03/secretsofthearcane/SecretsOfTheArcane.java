package net.jjcobb03.secretsofthearcane;

import net.jjcobb03.secretsofthearcane.block.ModBlocks;
import net.jjcobb03.secretsofthearcane.block.entity.ModBlockEntities;
import net.jjcobb03.secretsofthearcane.component.ModDataComponents;
import net.jjcobb03.secretsofthearcane.item.ModCreativeModeTabs;
import net.jjcobb03.secretsofthearcane.item.ModItems;
import net.jjcobb03.secretsofthearcane.api.aspect.AspectAssignments;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SecretsOfTheArcane.MOD_ID)
public class SecretsOfTheArcane {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "secretsofthearcane";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The tutorial I'm using has deleted the large block of code that has been commented out below

//    // Creates a new food item with the id "secretsofthearcane:example_id", nutrition 1 and saturation 2
//    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
//            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));
//
//    // Creates a creative tab with the id "secretsofthearcane:example_tab" for the example item, that is placed after the combat tab
//    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
//            .title(Component.translatable("itemGroup.secretsofthearcane")) //The language key for the title of your CreativeModeTab
//            .withTabsBefore(CreativeModeTabs.COMBAT)
//            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
//            .displayItems((parameters, output) -> {
//                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
//            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public SecretsOfTheArcane(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (SecretsoftheArcane) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Call the Deferred Register for CreativeModeTabs from ModCreativeModeTabs
        ModCreativeModeTabs.register(modEventBus);
        // Call the Deferred Register for Items from ModItems
        ModItems.register(modEventBus);
        // Call the Deferred Register for Blocks from ModBlocks
        ModBlocks.register(modEventBus);
        // Call the Deferred Register for BlockEntities from ModBlockEntities
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        // Call the AspectAssignments to assign aspect to items
        AspectAssignments.register();
        // Call the Deferred Register for DataComponents from ModDataComponents
        ModDataComponents.DATA_COMPONENTS.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // The tutorial I'm using has deleted the contents of this method
    private void commonSetup(FMLCommonSetupEvent event) {
//        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//
//        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
//            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
//        }
//
//        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());
//
//        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add modded contents to existing creative mode tabs
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Add the test item to the Ingredients creative tab
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.TEST_ITEM);
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.ROWAN_LOG);
        }
    }

    // The tutorial I'm using has deleted the contents of this method

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
    }
}
