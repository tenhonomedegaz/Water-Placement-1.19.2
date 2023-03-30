package io.github.tenhonomedegaz.underwaterplacement.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class WaterloggedLectern extends LecternBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty HAS_BOOK = BlockStateProperties.HAS_BOOK;
    public WaterloggedLectern(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)).setValue(HAS_BOOK, Boolean.valueOf(false)));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54543_) {
        p_54543_.add(FACING, POWERED, HAS_BOOK, WATERLOGGED);
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LecternBlockEntity(pos, state);
    }

    public static boolean tryPlaceBook(@Nullable Player p_153567_, Level p_153568_, BlockPos p_153569_, BlockState p_153570_, ItemStack p_153571_) {
        if (!p_153570_.getValue(HAS_BOOK)) {
            if (!p_153568_.isClientSide) {
                placeBook(p_153567_, p_153568_, p_153569_, p_153570_, p_153571_);
            }

            return true;
        } else {
            return false;
        }
    }

    private static void placeBook(@Nullable Player p_153576_, Level p_153577_, BlockPos p_153578_, BlockState p_153579_, ItemStack p_153580_) {
        BlockEntity blockentity = p_153577_.getBlockEntity(p_153578_);
        if (blockentity instanceof LecternBlockEntity lecternblockentity) {
            lecternblockentity.setBook(p_153580_.split(1));
            resetBookState(p_153577_, p_153578_, p_153579_, true);
            p_153577_.playSound((Player)null, p_153578_, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
            p_153577_.gameEvent(p_153576_, GameEvent.BLOCK_CHANGE, p_153578_);
        }

    }

    public static void resetBookState(Level p_54498_, BlockPos p_54499_, BlockState p_54500_, boolean p_54501_) {
        p_54498_.setBlock(p_54499_, p_54500_.setValue(POWERED, Boolean.valueOf(false)).setValue(HAS_BOOK, Boolean.valueOf(p_54501_)), 3);
        updateBelow(p_54498_, p_54499_, p_54500_);
    }
    private static void changePowered(Level p_54554_, BlockPos p_54555_, BlockState p_54556_, boolean p_54557_) {
        p_54554_.setBlock(p_54555_, p_54556_.setValue(POWERED, Boolean.valueOf(p_54557_)), 3);
        updateBelow(p_54554_, p_54555_, p_54556_);
    }

    private static void updateBelow(Level p_54545_, BlockPos p_54546_, BlockState p_54547_) {
        p_54545_.updateNeighborsAt(p_54546_.below(), p_54547_.getBlock());
    }




    public InteractionResult use(BlockState p_54524_, Level p_54525_, BlockPos p_54526_, Player p_54527_, InteractionHand p_54528_, BlockHitResult p_54529_) {
        if (p_54524_.getValue(HAS_BOOK)) {
            if (!p_54525_.isClientSide) {
                this.openScreen(p_54525_, p_54526_, p_54527_);
            }

            return InteractionResult.sidedSuccess(p_54525_.isClientSide);
        } else {
            ItemStack itemstack = p_54527_.getItemInHand(p_54528_);
            return !itemstack.isEmpty() && !itemstack.is(ItemTags.LECTERN_BOOKS) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
    }
    private void openScreen(Level p_54485_, BlockPos p_54486_, Player p_54487_) {
        BlockEntity blockentity = p_54485_.getBlockEntity(p_54486_);
        if (blockentity instanceof LecternBlockEntity) {
            p_54487_.openMenu((LecternBlockEntity)blockentity);
            p_54487_.awardStat(Stats.INTERACT_WITH_LECTERN);
        }

    }
    @Nullable
    public MenuProvider getMenuProvider(BlockState p_54571_, Level p_54572_, BlockPos p_54573_) {
        return !p_54571_.getValue(HAS_BOOK) ? null : super.getMenuProvider(p_54571_, p_54572_, p_54573_);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        Level level = context.getLevel();
        ItemStack itemstack = context.getItemInHand();
        Player player = context.getPlayer();
        boolean flag = false;
        if (!level.isClientSide && player != null && player.canUseGameMasterBlocks()) {
            CompoundTag compoundtag = BlockItem.getBlockEntityData(itemstack);
            if (compoundtag != null && compoundtag.contains("Book")) {
                flag = true;
            }
        }

        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)).setValue(HAS_BOOK, Boolean.valueOf(flag));
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


}
