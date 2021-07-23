package com.eerussianguy.moving_sunflowers;

import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType;

import com.mojang.serialization.Codec;

@ParametersAreNonnullByDefault
public class SunflowerBlockPlacer extends BlockPlacer
{
    public static final Codec<SunflowerBlockPlacer> CODEC = Codec.unit(SunflowerBlockPlacer::new);

    @Override
    @Nonnull
    protected BlockPlacerType<?> type() {
        return MSRegistry.SUNFLOWER_PLACER.get();
    }

    @Override
    public void place(LevelAccessor world, BlockPos pos, BlockState state, Random random)
    {
        MovingSunflowerBlock.placeAt(world, state,  pos, 2);
    }
}
