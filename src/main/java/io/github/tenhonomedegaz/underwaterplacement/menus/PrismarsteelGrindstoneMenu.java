package io.github.tenhonomedegaz.underwaterplacement.menus;

import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.MenuType;

import static io.github.tenhonomedegaz.underwaterplacement.init.BlockInit.PRISMARSTEEL_GRINDSTONE;

public class PrismarsteelGrindstoneMenu extends GrindstoneMenu {

    private final ContainerLevelAccess access;
    public PrismarsteelGrindstoneMenu(int i, Inventory inventory, ContainerLevelAccess levelAccess) {
        super(i, inventory, levelAccess);
        this.access = levelAccess;
    }

    public PrismarsteelGrindstoneMenu(int i, Inventory inventory){
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    @Override
    public MenuType<?> getType(){
        return MenuType.GRINDSTONE;
    }

    @Override
    public boolean stillValid(Player player){
        return stillValid(this.access, player, PRISMARSTEEL_GRINDSTONE.get());
    }
}
