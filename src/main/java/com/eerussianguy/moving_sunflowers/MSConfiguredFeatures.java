package com.eerussianguy.moving_sunflowers;

import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

import static com.eerussianguy.moving_sunflowers.MovingSunflowers.MOD_ID;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class MSConfiguredFeatures
{
    public static final ConfiguredFeature<?, ?> MOVING_SUNFLOWER_PATCH = createConfiguredFeature("sunflower_patch", Feature.RANDOM_PATCH.configured((
        new RandomPatchConfiguration.GrassConfigurationBuilder(
            new SimpleStateProvider(MSRegistry.SUNFLOWER.get().defaultBlockState()),
            new SunflowerBlockPlacer())).tries(64).noProjection().build()).decorated(Features.Decorators.ADD_32)
            .decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(10));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF createConfiguredFeature(String name, CF configuredFeature)
    {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(MOD_ID, name), configuredFeature);
        return configuredFeature;
    }
}
