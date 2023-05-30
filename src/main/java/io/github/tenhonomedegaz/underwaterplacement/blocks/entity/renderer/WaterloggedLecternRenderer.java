package io.github.tenhonomedegaz.underwaterplacement.blocks.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import io.github.tenhonomedegaz.underwaterplacement.blocks.WaterloggedLectern;
import io.github.tenhonomedegaz.underwaterplacement.blocks.entity.WaterloggedLecternBlockEntity;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static io.github.tenhonomedegaz.underwaterplacement.blocks.entity.renderer.WaterloggedEnchantingTableBlockEntityRenderer.BOOK_ENTITY_TEXTURE;

@OnlyIn(Dist.CLIENT)
public class WaterloggedLecternRenderer implements BlockEntityRenderer<WaterloggedLecternBlockEntity> {
    private final BookModel bookModel;

    public WaterloggedLecternRenderer(BlockEntityRendererProvider.Context context) {
        this.bookModel = new BookModel(context.bakeLayer(ModelLayers.BOOK));
    }

    public void render(WaterloggedLecternBlockEntity entity, float v, PoseStack poseStack, MultiBufferSource bufferSource, int i, int i1) {
        BlockState blockstate = entity.getBlockState();
        if (blockstate.getValue(WaterloggedLectern.HAS_BOOK)) {
            poseStack.pushPose();
            poseStack.translate(0.5D, 1.0625D, 0.5D);
            float f = blockstate.getValue(WaterloggedLectern.FACING).getClockWise().toYRot();
            poseStack.mulPose(Vector3f.YP.rotationDegrees(-f));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(67.5F));
            poseStack.translate(0.0D, -0.125D, 0.0D);
            this.bookModel.setupAnim(0.0F, 0.1F, 0.9F, 1.2F);
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entitySolid(BOOK_ENTITY_TEXTURE));
            this.bookModel.render(poseStack, vertexconsumer, i, i1, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}