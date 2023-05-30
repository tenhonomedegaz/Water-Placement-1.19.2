package io.github.tenhonomedegaz.underwaterplacement.world.feature;

import com.google.common.base.Suppliers;
import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.blocks.MarineFlower;
import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import io.github.tenhonomedegaz.underwaterplacement.world.feature.tree.ModAttachedToLeavesDecorator;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, UnderwaterPlacement.MODID);
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_AQUAMARINE_ORES = Suppliers.memoize(()-> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.GRAVEL), BlockInit.AQUAMARINE_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_SHELL_ORES = Suppliers.memoize(()-> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.SAND), BlockInit.SHELL_ORE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?,?>> AQUAMARINE_ORE = CONFIGURED_FEATURES.register("aquamarine_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_AQUAMARINE_ORES.get(), 5)));
    public static final RegistryObject<ConfiguredFeature<?,?>> SHELL_ORE = CONFIGURED_FEATURES.register("shell_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_SHELL_ORES.get(), 4)));


    public static final RegistryObject<ConfiguredFeature<?,?>> LUMINESCENT_MANGROVE = CONFIGURED_FEATURES.register("luminescent_mangrove",
            () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlockInit.LUMINESCENT_MANGROVE_LOG.get()),
                                    new UpwardsBranchingTrunkPlacer(2, 1, 4,
                                            UniformInt.of(1, 4), 0.5F,
                                            UniformInt.of(0, 1),
                                            Registry.BLOCK.getOrCreateTag(BlockInit.Tags.LUMINESCENT_MANGROVE_LOGS_CAN_GROW_THROUGH)),
                                    BlockStateProvider.simple(BlockInit.LUMINESCENT_MANGROVE_LEAVES.get()),
                                    new RandomSpreadFoliagePlacer(ConstantInt.of(3),
                                            ConstantInt.of(0),
                                            ConstantInt.of(2), 70),
                                    Optional.of(new MangroveRootPlacer(
                                            UniformInt.of(1, 3),
                                            BlockStateProvider.simple(BlockInit.LUMINESCENT_MANGROVE_ROOTS.get()),
                                            Optional.of(new AboveRootPlacement(BlockStateProvider.simple(BlockInit.MARINE_HYDRANGEA.get()), 0.5F)),
                                            new MangroveRootPlacement(Registry.BLOCK.getOrCreateTag(BlockInit.Tags.LUMINESCENT_MANGROVE_ROOTS_CAN_GROW_THROUGH),
                                                    HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRAVEL, BlockInit.GRAVELLED_LUMINESCENT_MANGROVE_ROOTS.get()),
                                                    BlockStateProvider.simple(BlockInit.GRAVELLED_LUMINESCENT_MANGROVE_ROOTS.get()), 8, 15, 0.2F))),
                                    new TwoLayersFeatureSize(2, 0, 2)).decorators(List.of(
                                            new LeaveVineDecorator(0.125F),
                                            new ModAttachedToLeavesDecorator(0.14F,1,0,
                                                    BlockStateProvider.simple(BlockInit.MARINE_HYDRANGEA.get().defaultBlockState().setValue(MarineFlower.FACING, Direction.UP)), 1, List.of(Direction.UP)
                                            ))).ignoreVines().build()));

    public static final RegistryObject<ConfiguredFeature<?,?>> TALL_LUMINESCENT_MANGROVE =
            CONFIGURED_FEATURES.register("tall_luminescent_mangrove",() ->
                    new ConfiguredFeature<>(Feature.TREE,
                            new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(BlockInit.LUMINESCENT_MANGROVE_LOG.get()),
                                    new UpwardsBranchingTrunkPlacer(4, 1, 9,
                                            UniformInt.of(1, 6), 0.5F,
                                            UniformInt.of(0, 1),
                                            Registry.BLOCK.getOrCreateTag(BlockInit.Tags.LUMINESCENT_MANGROVE_LOGS_CAN_GROW_THROUGH)),
                                    BlockStateProvider.simple(BlockInit.LUMINESCENT_MANGROVE_LEAVES.get()),
                                    new RandomSpreadFoliagePlacer(ConstantInt.of(3),
                                            ConstantInt.of(0),
                                            ConstantInt.of(2), 70),
                                    Optional.of(new MangroveRootPlacer(
                                            UniformInt.of(3, 7),
                                            BlockStateProvider.simple(BlockInit.LUMINESCENT_MANGROVE_ROOTS.get()),
                                            Optional.of(new AboveRootPlacement(BlockStateProvider.simple(BlockInit.MARINE_HYDRANGEA.get()), 0.5F)),
                                            new MangroveRootPlacement(Registry.BLOCK.getOrCreateTag(BlockInit.Tags.LUMINESCENT_MANGROVE_ROOTS_CAN_GROW_THROUGH),
                                                    HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRAVEL, BlockInit.GRAVELLED_LUMINESCENT_MANGROVE_ROOTS.get()),
                                                    BlockStateProvider.simple(BlockInit.GRAVELLED_LUMINESCENT_MANGROVE_ROOTS.get()), 8, 15, 0.2F))),
                                    new TwoLayersFeatureSize(3, 0, 2)).decorators(List.of(
                            new LeaveVineDecorator(0.125F),
                            new ModAttachedToLeavesDecorator(0.14F,1,0,
                                    BlockStateProvider.simple(BlockInit.MARINE_HYDRANGEA.get().defaultBlockState().setValue(MarineFlower.FACING, Direction.UP)), 1, List.of(Direction.UP)
                            ))).ignoreVines().build()));


    public static final RegistryObject<ConfiguredFeature<?,?>> LUMINESCENT_MANGROVE_SPAWN =
            CONFIGURED_FEATURES.register("luminescent_mangrove_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            ModPlacedFeatures.LUMINESCENT_MANGROVE_CHECKED.getHolder().get(),
                            0.85F)), ModPlacedFeatures.TALL_LUMINESCENT_MANGROVE_CHECKED.getHolder().get())));




    public static final RegistryObject<ConfiguredFeature<?, ?>> MARINE_HYDRANGEA = CONFIGURED_FEATURES.register("marine_hydrangea",
            ()-> new ConfiguredFeature<>(Feature.FLOWER,
                    new RandomPatchConfiguration(32,12,3, PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(BlockInit.MARINE_HYDRANGEA.get().defaultBlockState().setValue(MarineFlower.WATERLOGGED, Boolean.valueOf(true)))),
                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE))));

    public static void register(IEventBus bus){
        CONFIGURED_FEATURES.register(bus);
    }
}
