package io.github.tenhonomedegaz.underwaterplacement.menus;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.MenuType;

import static io.github.tenhonomedegaz.underwaterplacement.init.BlockInit.MARINE_GRINDSTONE;

public class MarineGrindstoneMenu extends GrindstoneMenu {

    private final ContainerLevelAccess access;
    public MarineGrindstoneMenu(int i, Inventory inventory, ContainerLevelAccess levelAccess) {
        super(i, inventory, levelAccess);
        this.access = levelAccess;
    }


    @Override
    public MenuType<?> getType(){
        return MenuType.GRINDSTONE;
    }

    @Override
    public boolean stillValid(Player player){
        return stillValid(this.access, player, MARINE_GRINDSTONE.get());
    }
}
