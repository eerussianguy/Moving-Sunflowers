package com.eerussianguy.moving_sunflowers;

import java.util.Random;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@ParametersAreNonnullByDefault
public class MovingSunflowerBlock extends TallFlowerBlock
{
    private static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 2);

    public MovingSunflowerBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(STAGE, 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (!world.isClientSide && state.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            world.setBlock(pos, state.setValue(STAGE, getStageForTime(world.getDayTime())), 2);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        if (state != null)
        {
            state = state.setValue(STAGE, getStageForTime(context.getLevel().getDayTime()));
        }
        return state;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(STAGE, getStageForTime(worldIn.getDayTime())), 3);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
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

    @Override
    public void placeAt(IWorld world, BlockPos pos, int flags)
    {
        int stage = getStageForTime(world.getLevelData().getDayTime());
        world.setBlock(pos, defaultBlockState().setValue(STAGE, stage).setValue(HALF, DoubleBlockHalf.LOWER), flags);
        world.setBlock(pos.above(), defaultBlockState().setValue(STAGE, stage).setValue(HALF, DoubleBlockHalf.UPPER), flags);
    }
}
