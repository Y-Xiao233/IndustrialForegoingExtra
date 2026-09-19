package net.yxiao233.industrialforegoingextra.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Blocks;
import net.yxiao233.industrialforegoingextra.api.renderer.IFEBaseBlockEntityRenderer;
import net.yxiao233.industrialforegoingextra.common.tile.DragonGeneratorTile;
import net.yxiao233.industrialforegoingextra.util.RendererHelper;
import org.jetbrains.annotations.NotNull;

public class DragonGeneratorRenderer extends IFEBaseBlockEntityRenderer<DragonGeneratorTile> {
    public DragonGeneratorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(DragonGeneratorTile entity, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, int i1) {
        if(entity.isCurrentBlockAbove()){
            return;
        }

        RendererHelper.renderSingleBatchedGhostBlock(poseStack,multiBufferSource,entity.getBlockPos(),entity.getBlockPos().above(), Blocks.DRAGON_EGG.defaultBlockState());
    }
}
