package com.eerussianguy.moving_sunflowers;

import java.util.Optional;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratedFeatureConfiguration;
import net.minecraft.data.worldgen.Features;
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
        if (event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).removeIf(cf -> (cf.get().config instanceof DecoratedFeatureConfiguration) && serializeAndCompareFeature(cf.get(), Features.PATCH_SUNFLOWER)))
        {
            MovingSunflowers.LOGGER.info("Removed vanilla sunflower feature");
        }
        if (event.getName() != null && event.getName().compareNamespaced(new ResourceLocation("minecraft", "sunflower_plains")) == 0)
        {
            event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> MSConfiguredFeatures.MOVING_SUNFLOWER_PATCH);
            MovingSunflowers.LOGGER.info("Added modded sunflower feature");
        }
    }

    /**
     * @author TelepathicGrunt
     */
    private static boolean serializeAndCompareFeature(ConfiguredFeature<?, ?> found, ConfiguredFeature<?, ?> registered)
    {
        Optional<JsonElement> foundSerialized = ConfiguredFeature.DIRECT_CODEC.encode(found, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();
        Optional<JsonElement> registeredSerialized = ConfiguredFeature.DIRECT_CODEC.encode(registered, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();

        if (foundSerialized.isEmpty() || registeredSerialized.isEmpty()) return false;
        return foundSerialized.equals(registeredSerialized);
    }
}
