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
        setDefaultState(getStateContainer().getBaseState().with(STAGE, 0).with(HALF, DoubleBlockHalf.LOWER));
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (!world.isRemote() && state.get(HALF) == DoubleBlockHalf.UPPER)
        {
            world.setBlockState(pos, state.with(STAGE, getStageForTime(world.getDayTime())), 2);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        if (state != null)
        {
            state = state.with(STAGE, getStageForTime(context.getWorld().getDayTime()));
        }
        return state;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(STAGE, getStageForTime(worldIn.getDayTime())), 3);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
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
        int stage = getStageForTime(world.getWorldInfo().getDayTime());
        world.setBlockState(pos, getDefaultState().with(STAGE, stage).with(HALF, DoubleBlockHalf.LOWER), flags);
        world.setBlockState(pos.up(), getDefaultState().with(STAGE, stage).with(HALF, DoubleBlockHalf.UPPER), flags);
    }
}
