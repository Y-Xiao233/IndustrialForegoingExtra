package net.yxiao233.industrialforegoingextra.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Implemented by the "main" block of a big block (a block that occupies more than a single block position).
 * The main block declares which extra positions should be filled with {@link BoundingBlock}s.
 *
 * <p>This module depends only on vanilla Minecraft and NeoForge; it can be dropped into any mod without
 * needing Mekanism (or any other mod) as a dependency.</p>
 */
public interface IBoundingBlockProvider {

    /**
     * Gets the absolute world positions that should be occupied by {@link BoundingBlock} filler blocks for the given
     * main position and state. Return an empty iterable if this block only occupies a single position.
     *
     * @param level   The level the block was placed in
     * @param mainPos The position of the main block
     * @param state   The state of the main block (use this to support facing/rotation dependent bounding positions)
     */
    Iterable<BlockPos> getBoundingPositions(Level level, BlockPos mainPos, BlockState state);

    /**
     * Checks whether a bounding block may be placed at the given position. By default any replaceable block
     * (including air) is considered valid.
     */
    default boolean canPlaceBounding(Level level, BlockPos boundingPos, BlockState state) {
        return level.getBlockState(boundingPos).canBeReplaced();
    }
}
