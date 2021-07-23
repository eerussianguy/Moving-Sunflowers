package com.eerussianguy.moving_sunflowers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.eerussianguy.moving_sunflowers.MovingSunflowers.MOD_ID;

@Mod(MOD_ID)
public class MovingSunflowers
{
    public static final String MOD_ID = "moving_sunflowers";

    public static final Logger LOGGER = LogManager.getLogger();

    public MovingSunflowers()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MSRegistry.BLOCKS.register(modEventBus);
        MSRegistry.ITEMS.register(modEventBus);
        MSRegistry.BLOCK_PLACER_TYPES.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        ItemBlockRenderTypes.setRenderLayer(MSRegistry.SUNFLOWER.get(), RenderType.cutout());
    }
}
