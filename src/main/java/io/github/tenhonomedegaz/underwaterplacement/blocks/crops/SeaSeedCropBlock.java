package io.github.tenhonomedegaz.underwaterplacement.blocks.crops;

import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import io.github.tenhonomedegaz.underwaterplacement.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SeaSeedCropBlock extends SeaCropBlock{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 7);
    public SeaSeedCropBlock(Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), Integer.valueOf(0)).setValue(WATERLOGGED, Boolean.valueOf(true)));
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.is(BlockInit.SAND_FARMLAND.get());
    }
    @Override
    protected ItemLike getBaseSeedId(){
        return ItemInit.SEA_SEEDS.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(AGE,WATERLOGGED);
    }
}
