package io.github.tenhonomedegaz.underwaterplacement.world.feature;

import com.google.common.base.Suppliers;
import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, UnderwaterPlacement.MODID);
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_AQUAMARINE_ORES = Suppliers.memoize(()-> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.GRAVEL), BlockInit.AQUAMARINE_ORE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?,?>> AQUAMARINE_ORE = CONFIGURED_FEATURES.register("aquamarine_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_AQUAMARINE_ORES.get(), 5)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MARINE_HYDRANGEA = CONFIGURED_FEATURES.register("marine_hydrangea",
            ()-> new ConfiguredFeature<>(Feature.FLOWER,
                    new RandomPatchConfiguration(32,8,2, PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(BlockInit.MARINE_HYDRANGEA.get())),
                            BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE))));

    public static void register(IEventBus bus){
        CONFIGURED_FEATURES.register(bus);
    }
}
