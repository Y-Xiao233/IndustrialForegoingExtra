package net.yxiao233.industrialforegoingextra.client.event;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.client.renderer.DragonGeneratorRenderer;
import net.yxiao233.industrialforegoingextra.client.renderer.FluidCraftingTableRenderer;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.tile.DragonGeneratorTile;
import net.yxiao233.industrialforegoingextra.common.tile.FluidCraftingTableTile;

@SuppressWarnings({"unchecked","removal"})
@EventBusSubscriber(modid = IndustrialForegoingExtra.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockRendererEvent {
    @SubscribeEvent
    public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer((BlockEntityType<DragonGeneratorTile>) IFEBlocks.DRAGON_GENERATOR.type().get(), DragonGeneratorRenderer::new);
        event.registerBlockEntityRenderer((BlockEntityType<FluidCraftingTableTile>) IFEBlocks.FLUID_CRAFTING_TABLE.type().get(), FluidCraftingTableRenderer::new);
    }
}
