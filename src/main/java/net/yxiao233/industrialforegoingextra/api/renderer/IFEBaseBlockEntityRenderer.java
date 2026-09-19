package net.yxiao233.industrialforegoingextra.api.renderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class IFEBaseBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public IFEBaseBlockEntityRenderer(BlockEntityRendererProvider.Context context){
    }
}
