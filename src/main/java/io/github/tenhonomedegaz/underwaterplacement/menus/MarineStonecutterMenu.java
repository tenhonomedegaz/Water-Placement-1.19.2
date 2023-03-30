package io.github.tenhonomedegaz.underwaterplacement.menus;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.StonecutterMenu;

import static io.github.tenhonomedegaz.underwaterplacement.init.BlockInit.MARINE_STONECUTTER;


public class MarineStonecutterMenu extends StonecutterMenu {

    private final ContainerLevelAccess access;
    public MarineStonecutterMenu(int p_40294_, Inventory p_40295_) {
        this(p_40294_, p_40295_, ContainerLevelAccess.NULL);
    }
    public MarineStonecutterMenu(int i, Inventory inventory, ContainerLevelAccess levelAccess) {
        super(i, inventory, levelAccess);
        this.access = levelAccess;
    }


    @Override
    public MenuType<?> getType(){
        return MenuType.STONECUTTER;
    }

    @Override
    public boolean stillValid(Player player){
        return stillValid(this.access, player, MARINE_STONECUTTER.get());
    }
}
