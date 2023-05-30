package io.github.tenhonomedegaz.underwaterplacement.world.feature.tree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.material.Fluids;


public class ModAttachedToLeavesDecorator extends AttachedToLeavesDecorator {


    public ModAttachedToLeavesDecorator(float p_225988_, int p_225989_, int p_225990_, BlockStateProvider p_225991_, int p_225992_, List<Direction> p_225993_) {
        super(p_225988_, p_225989_, p_225990_, p_225991_, p_225992_, p_225993_);
    }

    public void place(TreeDecorator.Context p_226000_) {
        Set<BlockPos> set = new HashSet<>();
        RandomSource randomsource = p_226000_.random();

        for(BlockPos blockpos : Util.shuffledCopy(p_226000_.leaves(), randomsource)) {
            Direction direction = Util.getRandom(this.directions, randomsource);
            BlockPos blockpos1 = blockpos.relative(direction);
            if (!set.contains(blockpos1) && randomsource.nextFloat() < this.probability && this.hasRequiredEmptyBlocks(p_226000_, blockpos, direction)) {
                BlockPos blockpos2 = blockpos1.offset(-this.exclusionRadiusXZ, -this.exclusionRadiusY, -this.exclusionRadiusXZ);
                BlockPos blockpos3 = blockpos1.offset(this.exclusionRadiusXZ, this.exclusionRadiusY, this.exclusionRadiusXZ);

                for(BlockPos blockpos4 : BlockPos.betweenClosed(blockpos2, blockpos3)) {
                    set.add(blockpos4.immutable());
                }

                p_226000_.setBlock(blockpos1, this.blockProvider.getState(randomsource, blockpos1));
            }
        }

    }


        private boolean hasRequiredEmptyBlocks(TreeDecorator.Context context, BlockPos blockPos, Direction direction) {
        for(int i = 1; i <= this.requiredEmptyBlocks; ++i) {
            BlockPos blockpos = blockPos.relative(direction, i);
            if (!context.isAir(blockpos)) {
                if (!blockpos.equals(Blocks.WATER)){
                    if (!blockpos.equals(Fluids.WATER)){
                        return false;
                    }
                }

            }
        }

        return true;
    }



}
