package net.yxiao233.industrialforegoingextra.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.neoforged.neoforge.fluids.FluidStack;
import net.yxiao233.industrialforegoingextra.api.renderer.IFEBaseBlockEntityRenderer;
import net.yxiao233.industrialforegoingextra.common.config.machine.FluidCraftingTableConfig;
import net.yxiao233.industrialforegoingextra.common.tile.FluidCraftingTableTile;
import net.yxiao233.industrialforegoingextra.util.RendererHelper;
import org.jetbrains.annotations.NotNull;

public class FluidCraftingTableRenderer extends IFEBaseBlockEntityRenderer<FluidCraftingTableTile> {
    public FluidCraftingTableRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FluidCraftingTableTile entity, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        if(!entity.isFluidRender){
            return;
        }
        FluidStack fluid = entity.inputFluid.getFluid();
        RendererHelper.renderFullFluid(poseStack,multiBufferSource,entity,fluid, FluidCraftingTableConfig.maxInputTankSize,combinedLight);
    }
}
