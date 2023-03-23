package io.github.tenhonomedegaz.underwaterplacement.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class WaterLoggedGrindstone extends GrindstoneBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final Component CONTAINER_TITLE = Component.translatable("container.grindstone_title");

    public WaterLoggedGrindstone(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(WATERLOGGED, FACING, FACE);
    }

    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        for (Direction direction : placeContext.getNearestLookingDirections()) {
            FluidState fluidstate = placeContext.getLevel().getFluidState(placeContext.getClickedPos());
            BlockState blockstate;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockstate = this.defaultBlockState()
                        .setValue(FACING, placeContext.getHorizontalDirection())
                        .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER))
                        .setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR);
            } else {
                blockstate = this.defaultBlockState()
                        .setValue(FACE, AttachFace.WALL)
                        .setValue(FACING, direction.getOpposite())
                        .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
            }
            if (blockstate.canSurvive(placeContext.getLevel(), placeContext.getClickedPos())) {
                return blockstate;
            }
        }
        return null;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor accessor, BlockPos pos, BlockPos pos1) {
        if (state.getValue(WATERLOGGED)) {
            accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
        }
        return super.updateShape(state, direction, state1, accessor, pos, pos1);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            MenuProvider menuProvider = this.getMenuProvider(blockState, level, pos);
            if (menuProvider != null) {
                player.openMenu(menuProvider);
                player.awardStat(Stats.INTERACT_WITH_GRINDSTONE);

            }
            return InteractionResult.CONSUME;
        }
    }
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos){
        return new SimpleMenuProvider((p1, p2, p3) ->{
            return new GrindstoneMenu(p1, p2, ContainerLevelAccess.create(level, pos));
        }, CONTAINER_TITLE);
    }
}


