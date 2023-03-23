package io.github.tenhonomedegaz.underwaterplacement.menus;

import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.blocks.WaterloggedAnvil;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;

import static io.github.tenhonomedegaz.underwaterplacement.init.BlockInit.PRISMARSTEEL_ANVIL;


public class PrismarsteelAnvilMenu extends AnvilMenu {

    private final DataSlot cost = DataSlot.standalone();
    private final ContainerLevelAccess access;
    public PrismarsteelAnvilMenu(int i, Inventory inventory, ContainerLevelAccess levelAccess) {
        super(i, inventory, levelAccess);
        this.access = levelAccess;
    }

    protected void onTake(Player p_150474_, ItemStack p_150475_) {
        if (!p_150474_.getAbilities().instabuild) {
            p_150474_.giveExperienceLevels(-this.cost.get());
        }

        float breakChance = net.minecraftforge.common.ForgeHooks.onAnvilRepair(p_150474_, p_150475_, PrismarsteelAnvilMenu.this.inputSlots.getItem(0), PrismarsteelAnvilMenu.this.inputSlots.getItem(1));

        this.inputSlots.setItem(0, ItemStack.EMPTY);
        if (this.repairItemCountCost > 0) {
            ItemStack itemstack = this.inputSlots.getItem(1);
            if (!itemstack.isEmpty() && itemstack.getCount() > this.repairItemCountCost) {
                itemstack.shrink(this.repairItemCountCost);
                this.inputSlots.setItem(1, itemstack);
            } else {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
        } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
        }

        this.cost.set(0);
        this.access.execute((p_150479_, p_150480_) -> {
            BlockState blockstate = p_150479_.getBlockState(p_150480_);
            if (!p_150474_.getAbilities().instabuild && blockstate.is(BlockTags.ANVIL) && p_150474_.getRandom().nextFloat() < breakChance) {
                BlockState blockstate1 = WaterloggedAnvil.damage(blockstate);
                if (blockstate1 == null) {
                    p_150479_.removeBlock(p_150480_, false);
                    p_150479_.levelEvent(1029, p_150480_, 0);
                } else {
                    p_150479_.setBlock(p_150480_, blockstate1, 2);
                    p_150479_.levelEvent(1030, p_150480_, 0);
                }
            } else {
                p_150479_.levelEvent(1030, p_150480_, 0);
            }

        });
    }

    public PrismarsteelAnvilMenu(int i, Inventory inventory){
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    @Override
    public MenuType<?> getType(){
        return MenuType.ANVIL;
    }

    @Override
    protected boolean isValidBlock(BlockState p_39019_) {
        return p_39019_.is(BlockTags.ANVIL);
    }
}

