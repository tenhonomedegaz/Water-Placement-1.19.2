package io.github.tenhonomedegaz.underwaterplacement;


import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = UnderwaterPlacement.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {
    @SubscribeEvent
    public static void commomSetup(FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            ((FlowerPotBlock)(Blocks.FLOWER_POT)).addPlant(BlockInit.EXAMPLE_FLOWER.getId(), BlockInit.EXAMPLE_FLOWER_POT);
        });
    }
}
