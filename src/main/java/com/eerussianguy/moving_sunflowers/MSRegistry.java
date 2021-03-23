package com.eerussianguy.moving_sunflowers;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.eerussianguy.moving_sunflowers.MovingSunflowers.MOD_ID;

@SuppressWarnings("unused")
public class MSRegistry
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<BlockPlacerType<?>> BLOCK_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, MOD_ID);

    public static final RegistryObject<Block> SUNFLOWER = BLOCKS.register("sunflower", () -> new MovingSunflowerBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT).tickRandomly()));
    public static final RegistryObject<Item> SUNFLOWER_ITEM = ITEMS.register("sunflower", () -> new TallBlockItem(SUNFLOWER.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<BlockPlacerType<SunflowerBlockPlacer>> SUNFLOWER_PLACER = BLOCK_PLACER_TYPES.register("sunflower", () -> new BlockPlacerType<>(SunflowerBlockPlacer.CODEC));
}
