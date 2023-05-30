package io.github.tenhonomedegaz.underwaterplacement.world.feature;

import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, UnderwaterPlacement.MODID);


    public static final RegistryObject<PlacedFeature> AQUAMARINE_ORE_PLACED = PLACED_FEATURES.register("aquamarine_ore_placed",
            ()-> new PlacedFeature(ModConfiguredFeatures.AQUAMARINE_ORE.getHolder().get(),
                    commonOrePlacement(15, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(62)))));
    public static final RegistryObject<PlacedFeature> SHELL_ORE_PLACED = PLACED_FEATURES.register("shell_ore_placed",
            ()-> new PlacedFeature(ModConfiguredFeatures.SHELL_ORE.getHolder().get(),
                    commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(40), VerticalAnchor.absolute(70)))));

    public static final RegistryObject<PlacedFeature> LUMINESCENT_MANGROVE_CHECKED = PLACED_FEATURES.register("luminescent_mangrove_checked",
            ()-> new PlacedFeature(ModConfiguredFeatures.LUMINESCENT_MANGROVE.getHolder().get(),List.of(PlacementUtils.filteredByBlockSurvival(BlockInit.LUMINESCENT_MANGROVE_PROPAGULE.get()))));
    public static final RegistryObject<PlacedFeature> TALL_LUMINESCENT_MANGROVE_CHECKED = PLACED_FEATURES.register("tall_luminescent_mangrove_checked",
            ()-> new PlacedFeature(ModConfiguredFeatures.TALL_LUMINESCENT_MANGROVE.getHolder().get(),  List.of(PlacementUtils.filteredByBlockSurvival(BlockInit.LUMINESCENT_MANGROVE_PROPAGULE.get()))));


    public static final RegistryObject<PlacedFeature> LUMINESCENT_MANGROVE_PLACED = PLACED_FEATURES.register("luminescent_mangrove_placed",
            ()-> new PlacedFeature(ModConfiguredFeatures.LUMINESCENT_MANGROVE_SPAWN.getHolder().get(), List.of( InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(120), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.countExtra(0, 0.10F, 1), BiomeFilter.biome())));


    public static final RegistryObject<PlacedFeature> MARINE_HYDRANGEA_PLACED = PLACED_FEATURES.register("marine_hydrangea_placed",
            ()-> new PlacedFeature(ModConfiguredFeatures.MARINE_HYDRANGEA.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(32),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.filteredByBlockSurvival(BlockInit.MARINE_HYDRANGEA.get()), HeightRangePlacement.uniform(VerticalAnchor.absolute(40), VerticalAnchor.absolute(62)), BiomeFilter.biome())));

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static void register(IEventBus bus){
        PLACED_FEATURES.register(bus);
    }
}
