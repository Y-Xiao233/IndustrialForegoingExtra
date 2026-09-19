package net.yxiao233.industrialforegoingextra.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.block.BoundingBlock;
import net.yxiao233.industrialforegoingextra.api.block.IBoundingBlockProvider;
import net.yxiao233.industrialforegoingextra.api.tile.BoundingTile;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;

/**
 * Handles placing and removing the {@link BoundingBlock} filler blocks for any block implementing
 * {@link IBoundingBlockProvider}.
 */
public final class BoundingBlockHelper {

    private BoundingBlockHelper() {
    }

    /**
     * Places bounding blocks at every position declared by {@link IBoundingBlockProvider#getBoundingPositions(Level, BlockPos, BlockState)}.
     * Call this when the main block is placed. This is called on both the server and the client, so that the client's
     * bounding block entities immediately know the main position (no data syncing required).
     */
    public static void placeBoundingBlocks(Level level, BlockPos mainPos, BlockState state) {
        Block block = state.getBlock();
        if (block instanceof IBoundingBlockProvider provider) {
            for (BlockPos boundingPos : provider.getBoundingPositions(level, mainPos, state)) {
                if (!provider.canPlaceBounding(level, boundingPos, state)) {
                    IndustrialForegoingExtra.LOGGER.warn("Unable to place bounding block at {} in {}, position is not replaceable", boundingPos, level.dimension().location());
                    continue;
                }
                BlockState boundingState = IFEBlocks.BOUNDING.getBlock().defaultBlockState();
                if (level.setBlockAndUpdate(boundingPos, boundingState)) {
                    BlockEntity blockEntity = level.getBlockEntity(boundingPos);
                    if (blockEntity instanceof BoundingTile bounding) {
                        //The block update already syncs this position to the client, no extra sync needed here
                        bounding.setMainLocation(mainPos);
                    } else {
                        IndustrialForegoingExtra.LOGGER.warn("Unable to find bounding block entity at {} in {}", boundingPos, level.dimension().location());
                    }
                    //Notify capability caches (pipes, hoppers, ...) that a capability may now be available here.
                    //Without this, a cache that queried this position before the bounding block existed would keep
                    // its cached null forever (NeoForge does not invalidate caches on block placement by itself)
                    level.invalidateCapabilities(boundingPos);
                } else {
                    IndustrialForegoingExtra.LOGGER.warn("Unable to set bounding block at {} in {}", boundingPos, level.dimension().location());
                }
            }
        }
    }

    /**
     * Removes all bounding blocks belonging to the given main block. Call this when the main block is removed.
     * This is called on both the server and the client.
     */
    public static void removeBoundingBlocks(Level level, BlockPos mainPos, BlockState state) {
        Block block = state.getBlock();
        if (block instanceof IBoundingBlockProvider provider) {
            for (BlockPos boundingPos : provider.getBoundingPositions(level, mainPos, state)) {
                BlockState boundingState = level.getBlockState(boundingPos);
                if (boundingState.is(IFEBlocks.BOUNDING.block())) {
                    //Remove the block entity first so it does not try to proxy the removal back to the main position
                    level.removeBlockEntity(boundingPos);
                    level.removeBlock(boundingPos, false);
                    //Notify capability caches that the capability at this position is gone
                    level.invalidateCapabilities(boundingPos);
                }
            }
        }
    }

    /**
     * Notifies capability caches (pipes, hoppers, ...) that the capabilities at all of this machine's bounding
     * positions may have changed. Call this whenever the machine's capability availability changes, for example when
     * a side configuration change enables/disables a capability on certain sides.
     */
    public static void invalidateBoundingCapabilities(Level level, BlockPos mainPos, BlockState state) {
        Block block = state.getBlock();
        if (block instanceof IBoundingBlockProvider provider) {
            for (BlockPos boundingPos : provider.getBoundingPositions(level, mainPos, state)) {
                level.invalidateCapabilities(boundingPos);
            }
        }
    }

    /**
     * Checks whether all bounding positions of the given block can be placed (are replaceable/air). If any position
     * is blocked by another block, the main block should not be placed at all.
     */
    public static boolean canPlaceBoundingBlocks(Level level, BlockPos mainPos, BlockState state) {
        Block block = state.getBlock();
        if (block instanceof IBoundingBlockProvider provider) {
            for (BlockPos boundingPos : provider.getBoundingPositions(level, mainPos, state)) {
                if (!provider.canPlaceBounding(level, boundingPos, state)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Re-syncs the main position to all bounding blocks of the given machine. Call this from the main block entity's
     * {@link net.minecraft.world.level.block.entity.BlockEntity#onLoad()} (server side) so that the client always
     * learns the link between the bounding blocks and the main block, no matter in which order the chunks and their
     * block entity data were sent.
     */
    public static void syncMasterPosition(Level level, BlockPos mainPos, BlockState state) {
        if (level.isClientSide) {
            return;
        }
        Block block = state.getBlock();
        if (block instanceof IBoundingBlockProvider provider) {
            for (BlockPos boundingPos : provider.getBoundingPositions(level, mainPos, state)) {
                BlockEntity blockEntity = level.getBlockEntity(boundingPos);
                if (blockEntity instanceof BoundingTile bounding) {
                    bounding.setMainLocation(mainPos);
                }
            }
        }
    }
}
