package io.github.tenhonomedegaz.underwaterplacement.init;

import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.blocks.WaterLoggedGrindstone;
import io.github.tenhonomedegaz.underwaterplacement.blocks.WaterloggedAnvil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;


public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UnderwaterPlacement.MODID);
    public static final ToIntFunction<BlockState> lightLevel10 = BlockState -> 10;
    public static final RegistryObject<Block> EXAMPLE_BLOCK = register("example_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f,6.0f).requiresCorrectToolForDrops()),
                new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> EXAMPLE_BLOCK2 = register("example_block2",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f,6.0f).requiresCorrectToolForDrops()),
                new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> PRISMARSTEEL_GRINDSTONE = register("prismarsteel_grindstone",
            () -> new WaterLoggedGrindstone(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.STONE)),
                new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> PRISMARSTEEL_ANVIL = register("prismarsteel_anvil",
            () -> new WaterloggedAnvil(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)),
                new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> CHIPPED_PRISMARSTEEL_ANVIL = register("chipped_prismarsteel_anvil",
            () -> new WaterloggedAnvil(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> DAMAGED_PRISMARSTEEL_ANVIL = register("damaged_prismarsteel_anvil",
            () -> new WaterloggedAnvil(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));


    public static final RegistryObject<FlowerBlock> EXAMPLE_FLOWER = register("example_flower",
            () -> new FlowerBlock(MobEffects.WATER_BREATHING, 300,BlockBehaviour.Properties.copy(Blocks.DANDELION).lightLevel(lightLevel10)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));

    public static final RegistryObject<FlowerPotBlock> EXAMPLE_FLOWER_POT = BLOCKS.register("example_flower_pot",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BlockInit.EXAMPLE_FLOWER, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).lightLevel(lightLevel10)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }

    public static class Tags{
        public static final TagKey<Block> NEEDS_EXAMPLE_TOOL = create("mineable/needs_example_tool");

        private static TagKey<Block> create(String location){
            return BlockTags.create(new ResourceLocation(UnderwaterPlacement.MODID, location));
        }
    }
}
