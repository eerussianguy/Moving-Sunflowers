package com.eerussianguy.moving_sunflowers;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.eerussianguy.moving_sunflowers.MovingSunflowers.MOD_ID;

@SuppressWarnings("unused")
public class MSRegistry
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<BlockPlacerType<?>> BLOCK_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, MOD_ID);

    public static final RegistryObject<Block> SUNFLOWER = BLOCKS.register("sunflower", () -> new MovingSunflowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).randomTicks()));
    public static final RegistryObject<Item> SUNFLOWER_ITEM = ITEMS.register("sunflower", () -> new DoubleHighBlockItem(SUNFLOWER.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockPlacerType<SunflowerBlockPlacer>> SUNFLOWER_PLACER = BLOCK_PLACER_TYPES.register("sunflower", () -> new BlockPlacerType<>(SunflowerBlockPlacer.CODEC));
}
