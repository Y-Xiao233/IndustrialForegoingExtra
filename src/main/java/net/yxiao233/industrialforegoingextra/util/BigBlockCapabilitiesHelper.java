package net.yxiao233.industrialforegoingextra.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.yxiao233.industrialforegoingextra.api.block.BoundingBlock;
import net.yxiao233.industrialforegoingextra.api.tile.MultiIndustrialProcessingTile;
import net.yxiao233.industrialforegoingextra.api.tile.BoundingTile;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;

public class BigBlockCapabilitiesHelper {
    public static void registryCapabilities(RegisterCapabilitiesEvent event, Holder<BlockEntityType<?>> holder){
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,holder.value(),(object, direction) -> {
            if (object instanceof MultiIndustrialProcessingTile<?> machine) {
                return machine.getItemHandler(direction);
            }
            return null;
        });
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,holder.value(),(object, direction) -> {
            if (object instanceof MultiIndustrialProcessingTile<?> machine) {
                return machine.getEnergyStorage();
            }
            return null;
        });
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,holder.value(),(object, direction) -> {
            if (object instanceof MultiIndustrialProcessingTile<?> machine) {
                return machine.getFluidHandler(direction);
            }
            return null;
        });
    }

    public static void registryBoundingCapabilities(RegisterCapabilitiesEvent event){
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, side) -> {
            if (blockEntity instanceof BoundingTile bounding) {
                BlockPos mainPos = BoundingBlock.getMainBlockPos(level, pos);
                if (mainPos != null) {
                    return bounding.getOffsetCapability(Capabilities.ItemHandler.BLOCK, side, pos.subtract(mainPos));
                }
            }
            return null;
        }, IFEBlocks.BOUNDING.getBlock());
        event.registerBlock(Capabilities.FluidHandler.BLOCK, (level, pos, state, blockEntity, side) -> {
            if (blockEntity instanceof BoundingTile bounding) {
                BlockPos mainPos = BoundingBlock.getMainBlockPos(level, pos);
                if (mainPos != null) {
                    return bounding.getOffsetCapability(Capabilities.FluidHandler.BLOCK, side, pos.subtract(mainPos));
                }
            }
            return null;
        },IFEBlocks.BOUNDING.getBlock());
        event.registerBlock(Capabilities.EnergyStorage.BLOCK, (level, pos, state, blockEntity, side) -> {
            if (blockEntity instanceof BoundingTile bounding) {
                BlockPos mainPos = BoundingBlock.getMainBlockPos(level, pos);
                if (mainPos != null) {
                    return bounding.getOffsetCapability(Capabilities.EnergyStorage.BLOCK, side, pos.subtract(mainPos));
                }
            }
            return null;
        },IFEBlocks.BOUNDING.getBlock());
    }
}
