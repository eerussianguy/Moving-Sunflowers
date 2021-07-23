package com.eerussianguy.moving_sunflowers;

import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

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
    public void place(IWorld world, BlockPos pos, BlockState state, Random random)
    {
        ((MovingSunflowerBlock)state.getBlock()).placeAt(world, pos, 2);
    }
}
