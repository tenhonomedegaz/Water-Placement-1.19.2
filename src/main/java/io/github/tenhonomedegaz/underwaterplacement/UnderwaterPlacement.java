package io.github.tenhonomedegaz.underwaterplacement;

import io.github.tenhonomedegaz.underwaterplacement.blocks.entity.ModBlockEntities;
import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import io.github.tenhonomedegaz.underwaterplacement.init.ItemInit;
import io.github.tenhonomedegaz.underwaterplacement.world.feature.ModConfiguredFeatures;
import io.github.tenhonomedegaz.underwaterplacement.world.feature.ModPlacedFeatures;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UnderwaterPlacement.MODID)
public class UnderwaterPlacement {
    public static final String MODID = "underwaterplacement";

    public UnderwaterPlacement() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.BLOCKS.register(bus);
        ItemInit.ITEMS.register(bus);
        ModConfiguredFeatures.register(bus);
        ModPlacedFeatures.register(bus);
        ModBlockEntities.register(bus);
    }

    public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return ItemInit.AQUAMARINE.get().getDefaultInstance();
        }
    };
}
