package io.github.tenhonomedegaz.underwaterplacement.blocks.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import io.github.tenhonomedegaz.underwaterplacement.UnderwaterPlacement;
import io.github.tenhonomedegaz.underwaterplacement.blocks.entity.WaterloggedEnchantingTableBlockEntity;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WaterloggedEnchantingTableBlockEntityRenderer implements BlockEntityRenderer<WaterloggedEnchantingTableBlockEntity> {
    public static final ResourceLocation BOOK_ENTITY_TEXTURE  =
            new ResourceLocation(UnderwaterPlacement.MODID,"textures/entity/enchanting_table_book.png");
    private final BookModel bookModel;

    public WaterloggedEnchantingTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.bookModel = new BookModel(context.bakeLayer(ModelLayers.BOOK));
    }
    @Override
    public void render(WaterloggedEnchantingTableBlockEntity blockEntity, float v, PoseStack stack, MultiBufferSource bufferSource, int i, int i1) {
        stack.pushPose();
        stack.translate(0.5D, 0.75D, 0.5D);
        float f = (float)blockEntity.time + v;
        stack.translate(0.0D, (double)(0.1F + Mth.sin(f * 0.1F) * 0.01F), 0.0D);

        float f1;
        for(f1 = blockEntity.rot - blockEntity.oRot; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F)) {
        }

        while(f1 < -(float)Math.PI) {
            f1 += ((float)Math.PI * 2F);
        }

        float f2 = blockEntity.oRot + f1 * v;
        stack.mulPose(Vector3f.YP.rotation(-f2));
        stack.mulPose(Vector3f.ZP.rotationDegrees(80.0F));
        float f3 = Mth.lerp(v, blockEntity.oFlip, blockEntity.flip);
        float f4 = Mth.frac(f3 + 0.25F) * 1.6F - 0.3F;
        float f5 = Mth.frac(f3 + 0.75F) * 1.6F - 0.3F;
        float f6 = Mth.lerp(v, blockEntity.oOpen, blockEntity.open);
        this.bookModel.setupAnim(f, Mth.clamp(f4, 0.0F, 1.0F), Mth.clamp(f5, 0.0F, 1.0F), f6);
        //VertexConsumer vertexconsumer = BOOK_LOCATION.buffer(bufferSource, RenderType::entitySolid);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entitySolid(BOOK_ENTITY_TEXTURE));
        this.bookModel.render(stack, vertexconsumer, i, i1, 1.0F, 1.0F, 1.0F, 1.0F);
        stack.popPose();
    }
}
