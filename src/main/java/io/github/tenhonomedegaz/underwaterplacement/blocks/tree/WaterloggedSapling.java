package io.github.tenhonomedegaz.underwaterplacement.blocks.tree;

import io.github.tenhonomedegaz.underwaterplacement.world.feature.tree.LuminescentMangroveGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.grower.MangroveTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class WaterloggedSapling extends SaplingBlock implements SimpleWaterloggedBlock {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final float GROW_TALL_MANGROVE_PROBABILITY = 0.85F;

    public WaterloggedSapling(BlockBehaviour.Properties p_221449_) {
        super(new LuminescentMangroveGrower(0.85F), p_221449_);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, Integer.valueOf(0)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos pos){
        BlockPos blockPos = pos.below();
        return this.mayPlaceOn(levelReader.getBlockState(blockPos), levelReader, blockPos);
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_221484_) {
        p_221484_.add(STAGE).add(WATERLOGGED);
    }

    protected boolean mayPlaceOn(BlockState p_221496_, BlockGetter p_221497_, BlockPos p_221498_) {
        return super.mayPlaceOn(p_221496_, p_221497_, p_221498_) || p_221496_.is(Blocks.CLAY) || p_221496_.is(Blocks.GRAVEL) || p_221496_.is(Blocks.SAND) || p_221496_.is(BlockTags.DIRT) || p_221496_.is(Blocks.FARMLAND);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_221456_) {
        FluidState fluidstate = p_221456_.getLevel().getFluidState(p_221456_.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(p_221456_).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }


    public VoxelShape getShape(BlockState p_56008_, BlockGetter p_56009_, BlockPos p_56010_, CollisionContext p_56011_) {
        return SHAPE;
    }

    public BlockState updateShape(BlockState p_221477_, Direction p_221478_, BlockState p_221479_, LevelAccessor p_221480_, BlockPos p_221481_, BlockPos p_221482_) {
        if (p_221477_.getValue(WATERLOGGED)) {
            p_221480_.scheduleTick(p_221481_, Fluids.WATER, Fluids.WATER.getTickDelay(p_221480_));
        }

        return p_221478_ == Direction.UP && !p_221477_.canSurvive(p_221480_, p_221481_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_221477_, p_221478_, p_221479_, p_221480_, p_221481_, p_221482_);
    }

    public FluidState getFluidState(BlockState p_221494_) {
        return p_221494_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_221494_);
    }

    public void randomTick(BlockState p_222011_, ServerLevel p_222012_, BlockPos p_222013_, RandomSource p_222014_) {
        if (!p_222012_.isAreaLoaded(p_222013_, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (p_222012_.getMaxLocalRawBrightness(p_222013_.above()) >= 9 && p_222014_.nextInt(7) == 0) {
            this.advanceTree(p_222012_, p_222013_, p_222011_, p_222014_);
        }
    }

    public boolean isValidBonemealTarget(BlockGetter p_55991_, BlockPos p_55992_, BlockState p_55993_, boolean p_55994_) {
        return true;
    }

    public boolean isBonemealSuccess(Level p_222006_, RandomSource p_222007_, BlockPos p_222008_, BlockState p_222009_) {
        return (double)p_222006_.random.nextFloat() < 0.45D;
    }

    public void performBonemeal(ServerLevel p_221996_, RandomSource p_221997_, BlockPos p_221998_, BlockState p_221999_) {
        this.advanceTree(p_221996_, p_221998_, p_221999_, p_221997_);
    }

}