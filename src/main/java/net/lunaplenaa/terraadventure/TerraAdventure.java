package net.lunaplenaa.terraadventure;

import com.mojang.logging.LogUtils;
import net.lunaplenaa.terraadventure.block.ModBlocks;
import net.lunaplenaa.terraadventure.block.entity.ModBlockEntities;
import net.lunaplenaa.terraadventure.entity.ModEntities;
import net.lunaplenaa.terraadventure.entity.client.BiteyRenderer;
import net.lunaplenaa.terraadventure.entity.client.FaeSeedlingRenderer;
import net.lunaplenaa.terraadventure.entity.client.MysteryTraderRenderer;
import net.lunaplenaa.terraadventure.entity.client.SlugNormalRenderer;
import net.lunaplenaa.terraadventure.items.ModCreativeModeTabs;
import net.lunaplenaa.terraadventure.items.ModItems;
import net.lunaplenaa.terraadventure.loot.TaaLootModifiers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TerraAdventure.MODID)
public class TerraAdventure {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "terraadventure";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public TerraAdventure(FMLJavaModLoadingContext context)  {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        TaaLootModifiers.register(modEventBus);

        ModEntities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)  {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.SLUGNORMAL.get(), SlugNormalRenderer::new);
            EntityRenderers.register(ModEntities.INKSPIRITCOMMON.get(), BiteyRenderer::new);
            EntityRenderers.register(ModEntities.FAESEEDLING.get(), FaeSeedlingRenderer::new);
            EntityRenderers.register(ModEntities.MYSTERYTRADER.get(), MysteryTraderRenderer::new);
        }
    }
}
