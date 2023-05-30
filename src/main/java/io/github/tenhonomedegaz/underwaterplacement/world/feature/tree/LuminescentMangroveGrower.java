package io.github.tenhonomedegaz.underwaterplacement.world.feature.tree;

import io.github.tenhonomedegaz.underwaterplacement.world.feature.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class LuminescentMangroveGrower extends AbstractTreeGrower {
    private final float tallProbability;

    public LuminescentMangroveGrower(float p_222933_) {
        this.tallProbability = p_222933_;
    }

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_222935_, boolean p_222936_) {
        return p_222935_.nextFloat() < this.tallProbability ? ModConfiguredFeatures.TALL_LUMINESCENT_MANGROVE.getHolder().get() : ModConfiguredFeatures.LUMINESCENT_MANGROVE.getHolder().get();
    }
}