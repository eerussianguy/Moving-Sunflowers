package com.eerussianguy.moving_sunflowers;

import java.util.Optional;

import com.google.gson.JsonElement;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.serialization.JsonOps;

import static com.eerussianguy.moving_sunflowers.MovingSunflowers.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler
{
    @SubscribeEvent
    public static void onBiomeLoad(final BiomeLoadingEvent event)
    {
        if (event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).removeIf(cf -> (cf.get().config instanceof DecoratedFeatureConfig) && serializeAndCompareFeature(cf.get(), Features.PATCH_SUNFLOWER)))
        {
            MovingSunflowers.LOGGER.info("Removed vanilla sunflower feature");
        }
        if (event.getName() != null && event.getName().compareNamespaced(new ResourceLocation("minecraft", "sunflower_plains")) == 0)
        {
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> MSConfiguredFeatures.MOVING_SUNFLOWER_PATCH);
            MovingSunflowers.LOGGER.info("Added modded sunflower feature");
        }
    }

    /**
     * @author TelepathicGrunt
     */
    private static boolean serializeAndCompareFeature(ConfiguredFeature<?, ?> found, ConfiguredFeature<?, ?> registered)
    {
        Optional<JsonElement> foundSerialized = ConfiguredFeature.CODEC.encode(found, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();
        Optional<JsonElement> registeredSerialized = ConfiguredFeature.CODEC.encode(registered, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();

        if (!foundSerialized.isPresent() || !registeredSerialized.isPresent()) return false;
        return foundSerialized.equals(registeredSerialized);
    }
}
