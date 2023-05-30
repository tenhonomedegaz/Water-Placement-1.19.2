package io.github.tenhonomedegaz.underwaterplacement.blocks;

import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import io.github.tenhonomedegaz.underwaterplacement.init.ItemInit;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

public class SoilSand extends Block {
    public SoilSand(Properties p_55926_) {
        super(p_55926_);
    }


    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (context.getItemInHand().is(ItemInit.EXAMPLE_HOE.get())) {
            if(state.is(BlockInit.SOIL_SAND.get())){
                return BlockInit.SAND_FARMLAND.get().defaultBlockState();
            }
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
