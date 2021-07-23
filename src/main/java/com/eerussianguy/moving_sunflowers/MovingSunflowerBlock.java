package com.eerussianguy.moving_sunflowers;

import java.util.Random;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

@ParametersAreNonnullByDefault
public class MovingSunflowerBlock extends TallFlowerBlock
{
    private static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 2);

    public MovingSunflowerBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(STAGE, 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (!level.isClientSide && state.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            level.setBlock(pos, state.setValue(STAGE, getStageForTime(level.getDayTime())), 2);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        if (state != null)
        {
            state = state.setValue(STAGE, getStageForTime(context.getLevel().getDayTime()));
        }
        return state;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        level.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(STAGE, getStageForTime(level.getDayTime())), 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(STAGE);
    }

    public static int getStageForTime(long daytime)
    {
        if (daytime < 4000 || daytime > 22000)
        {
            return 0;
        }
        else if (daytime < 8000)
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    public static void placeAt(LevelAccessor level, BlockState state, BlockPos pos, int flags)
    {
        int stage = getStageForTime(level.getLevelData().getDayTime());
        level.setBlock(pos, state.setValue(STAGE, stage).setValue(HALF, DoubleBlockHalf.LOWER), flags);
        level.setBlock(pos.above(), state.setValue(STAGE, stage).setValue(HALF, DoubleBlockHalf.UPPER), flags);
    }
}
