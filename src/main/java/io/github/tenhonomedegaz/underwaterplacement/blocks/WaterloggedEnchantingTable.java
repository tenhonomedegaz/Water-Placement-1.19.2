package io.github.tenhonomedegaz.underwaterplacement.blocks;

import io.github.tenhonomedegaz.underwaterplacement.blocks.entity.ModBlockEntities;
import io.github.tenhonomedegaz.underwaterplacement.blocks.entity.WaterloggedEnchantingTableBlockEntity;
import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import io.github.tenhonomedegaz.underwaterplacement.menus.MarineEnchantmentMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class WaterloggedEnchantingTable extends EnchantmentTableBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public WaterloggedEnchantingTable(Properties properties) {
        super(properties);
    }


    public void animateTick( BlockState blockState, Level level, BlockPos blockPos, RandomSource source) {
        super.animateTick(blockState, level, blockPos, source);

        for (BlockPos blockpos : BOOKSHELF_OFFSETS) {
            if (source.nextInt(16) == 0 && isValidBookShelf(level, blockPos, blockpos)) {
                level.addParticle(ParticleTypes.ENCHANT, (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 2.0D, (double) blockPos.getZ() + 0.5D, (double) ((float) blockpos.getX() + source.nextFloat()) - 0.5D, (double) ((float) blockpos.getY() - source.nextFloat() - 1.0F), (double) ((float) blockpos.getZ() + source.nextFloat()) - 0.5D);
            }
        }
    }

    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack p_52967_) {
        if (p_52967_.hasCustomHoverName()) {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof EnchantmentTableBlockEntity) {
                ((EnchantmentTableBlockEntity)blockentity).setCustomName(p_52967_.getHoverName());
            }
        }

    }
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(WATERLOGGED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        FluidState fluidstate = placeContext.getLevel().getFluidState(placeContext.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
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

    // Thanks Serilum, i didn't know how to contact you but your mod helped me to do that part
    public static boolean isValidBookShelf(Level level, BlockPos blockPos, BlockPos blockPos1) {
        BlockPos offsetPos = blockPos.offset(blockPos1.getX() / 2, blockPos1.getY(), blockPos1.getZ() / 2);
        return (level.getBlockState(blockPos.offset(blockPos1)).is(BlockInit.PRISMARINE_BOOKSHELF.get()) && (level.isEmptyBlock(offsetPos) || level.getBlockState(offsetPos).getBlock().equals(Blocks.WATER)))
                || (level.getBlockState(blockPos.offset(blockPos1)).getEnchantPowerBonus(level, blockPos.offset(blockPos1)) != 0 && level.isEmptyBlock(blockPos.offset(blockPos1.getX() / 2, blockPos1.getY(), blockPos1.getZ() / 2)));
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(blockState.getMenuProvider(level, blockPos));
            return InteractionResult.CONSUME;
        }
    }
    @Nullable
    public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity blockentity = level.getBlockEntity(blockPos);
        if (blockentity instanceof WaterloggedEnchantingTableBlockEntity) {
            Component component = ((Nameable)blockentity).getDisplayName();
            return new SimpleMenuProvider((p_207906_, p_207907_, p_207908_) -> {
                return new MarineEnchantmentMenu(p_207906_, p_207907_, ContainerLevelAccess.create(level, blockPos));
            }, component);
        } else {
            return null;
        }
    }

@Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WaterloggedEnchantingTableBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return createTickerHelper(entityType, ModBlockEntities.MARINE_ENCHANTING_TABLE.get(),
                WaterloggedEnchantingTableBlockEntity::bookAnimationTick);
    }
}
