package io.github.tenhonomedegaz.underwaterplacement.blocks;

import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MarineFlower extends FlowerBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    protected final VoxelShape northAabb;
    protected final VoxelShape southAabb;
    protected final VoxelShape eastAabb;
    protected final VoxelShape westAabb;
    protected final VoxelShape upAabb;
    protected final VoxelShape downAabb;
    private final MobEffect suspiciousStewEffect;
    private final int effectDuration;

    private final java.util.function.Supplier<MobEffect> suspiciousStewEffectSupplier;

    public MarineFlower(Supplier<MobEffect> effectSupplier, int i, Properties properties) {
        super(effectSupplier, i, properties);
        this.suspiciousStewEffect = null;
        this.suspiciousStewEffectSupplier = effectSupplier;
        this.effectDuration = i;
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.UP));
        this.upAabb = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
        this.downAabb = Block.box(2.0D, 3.0D, 2.0D, 14.0D, 16.0D, 14.0D) ;
        this.northAabb = Block.box(2.0D, 2.0D, 3.0D, 14.0D, 14.0D, 16.0D);
        this.southAabb = Block.box(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 13.0D);
        this.eastAabb = Block.box(0.0D, 2.0D, 2.0D, 13.0D, 14.0D, 14.0D);
        this.westAabb = Block.box( 3.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D);
    }

    public MobEffect getSuspiciousStewEffect() {
        if (true) return this.suspiciousStewEffectSupplier.get();
        return this.suspiciousStewEffect;
    }

    public int getEffectDuration() {
        if (this.suspiciousStewEffect == null && !this.suspiciousStewEffectSupplier.get().isInstantenous()) return this.effectDuration * 20;
        return this.effectDuration;
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        Direction direction = blockState.getValue(FACING);
        switch (direction) {
            case NORTH:
                return this.northAabb;
            case SOUTH:
                return this.southAabb;
            case EAST:
                return this.eastAabb;
            case WEST:
                return this.westAabb;
            case DOWN:
                return this.downAabb;
            case UP:
            default:
                return this.upAabb;
        }
    }


    public boolean canSurvive(BlockState blockState, LevelReader reader, BlockPos blockPos) {
        Direction direction = blockState.getValue(FACING);
        BlockPos blockpos = blockPos.relative(direction.getOpposite());
        return (reader.getBlockState(blockpos).isFaceSturdy(reader, blockpos, direction) || reader.getBlockState(blockpos).is(BlockInit.LUMINESCENT_MANGROVE_LEAVES.get()) || reader.getBlockState(blockpos).is(BlockInit.LUMINESCENT_MANGROVE_ROOTS.get()));
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, LevelAccessor accessor, BlockPos blockPos, BlockPos pos) {
        if (blockState.getValue(WATERLOGGED)) {
            accessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
        }

        return direction == blockState.getValue(FACING).getOpposite() && !blockState.canSurvive(accessor, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState1, accessor, blockPos, pos);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelaccessor = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER)).setValue(FACING, context.getClickedFace());
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(WATERLOGGED, FACING);
    }


    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

}
