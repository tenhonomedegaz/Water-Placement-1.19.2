package io.github.tenhonomedegaz.underwaterplacement.init;

import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.blocks.*;
import io.github.tenhonomedegaz.underwaterplacement.blocks.WaterloggedBrewingStand;
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
    public static final ToIntFunction<BlockState> lightLevel7 = BlockState -> 7;
    public static final ToIntFunction<BlockState> lightLevel1 = BlockState -> 1;
    public static final RegistryObject<Block> AQUAMARINE_ORE = register("aquamarine_ore",
            () -> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6f).requiresCorrectToolForDrops().lightLevel(lightLevel7).sound(SoundType.GRAVEL)),
                new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> AQUAMARINE_BLOCK = register("aquamarine_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).strength(5.0f,6.0f).requiresCorrectToolForDrops().sound(SoundType.METAL)),
                new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> PRISMARSTEEL_BLOCK = register("prismarsteel_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> PRISMARINE_BOOKSHELF = register("prismarine_bookshelf",
            ()-> new PrismarineBookshelf(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DIAMOND).requiresCorrectToolForDrops().strength(1.5F, 6.0F)),
                new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> MARINE_GRINDSTONE = register("marine_grindstone",
            () -> new WaterLoggedGrindstone(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.STONE)),
                new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> MARINE_ENCHANTING_TABLE = register("marine_enchanting_table",
            () -> new WaterloggedEnchantingTable(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.STONE).lightLevel(lightLevel7)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> MARINE_ANVIL = register("marine_anvil",
            () -> new WaterloggedAnvil(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)),
                new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> CHIPPED_MARINE_ANVIL = register("chipped_marine_anvil",
            () -> new WaterloggedAnvil(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> DAMAGED_MARINE_ANVIL = register("damaged_marine_anvil",
            () -> new WaterloggedAnvil(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> MARINE_STONECUTTER = register("marine_stonecutter",
            () -> new WaterloggedStonecutter(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> MARINE_BREWING_STAND = register("marine_brewing_stand",
            () -> new WaterloggedBrewingStand(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(0.5F).lightLevel(lightLevel1).noOcclusion()),
            new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<Block> MARINE_LECTERN = register("marine_lectern",
            ()-> new WaterloggedLectern(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.STONE)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));


    public static final RegistryObject<MarineFlower> MARINE_HYDRANGEA = register("marine_hydrangea",
            () -> new MarineFlower(() -> MobEffects.WATER_BREATHING, 300,BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH).lightLevel(lightLevel10).sound(SoundType.WET_GRASS)),
            new Item.Properties().tab(UnderwaterPlacement.TAB));
    public static final RegistryObject<FlowerPotBlock> POTTED_MARINE_HYDRANGEA = BLOCKS.register("potted_marine_hydrangea",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BlockInit.MARINE_HYDRANGEA, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).lightLevel(lightLevel10)));


    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }

    public static class Tags{
        public static final TagKey<Block> NEEDS_PRISMARSTEEL_TOOL = create("mineable/needs_prismarsteel_tool");

        private static TagKey<Block> create(String location){
            return BlockTags.create(new ResourceLocation(UnderwaterPlacement.MODID, location));
        }
    }
}
