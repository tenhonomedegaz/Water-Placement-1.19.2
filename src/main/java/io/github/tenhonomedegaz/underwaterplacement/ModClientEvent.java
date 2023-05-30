package io.github.tenhonomedegaz.underwaterplacement;

import io.github.tenhonomedegaz.underwaterplacement.blocks.entity.ModBlockEntities;
import io.github.tenhonomedegaz.underwaterplacement.blocks.entity.renderer.WaterloggedEnchantingTableBlockEntityRenderer;
import io.github.tenhonomedegaz.underwaterplacement.blocks.entity.renderer.WaterloggedLecternRenderer;
import io.github.tenhonomedegaz.underwaterplacement.init.BlockInit;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = UnderwaterPlacement.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvent {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(BlockInit.MARINE_HYDRANGEA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.POTTED_MARINE_HYDRANGEA.get(), RenderType.cutout());

    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntities.MARINE_ENCHANTING_TABLE.get(),
                WaterloggedEnchantingTableBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MARINE_LECTERN.get(),
                WaterloggedLecternRenderer::new);
    }
}
