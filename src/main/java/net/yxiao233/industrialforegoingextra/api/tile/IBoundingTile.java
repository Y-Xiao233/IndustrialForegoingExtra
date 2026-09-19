package net.yxiao233.industrialforegoingextra.api.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.yxiao233.industrialforegoingextra.api.block.BoundingBlock;
import org.jetbrains.annotations.Nullable;

/**
 * Implemented by the block entity of the main block of a big block. The {@link BoundingBlock} filler blocks use this
 * interface to redirect redstone changes and comparator queries back to the main block entity.
 *
 * <p>It also controls which capabilities (item handler, fluid handler, energy, ...) are exposed at the bounding block
 * positions. By default <strong>no</strong> capabilities are proxied to the bounding blocks, so automation (pipes,
 * hoppers, auto-eject) can only interact with the main block position. This prevents a machine from ejecting into (or
 * pulling from) its own bounding blocks. Machines that want specific parts of themselves to be automation accessible
 * can opt in by overriding {@link #isOffsetCapabilityDisabled(BlockCapability, Direction, BlockPos)} — the same way
 * Mekanism's DigitalMiner does.</p>
 */
public interface IBoundingTile {

    /**
     * Gets the position of the main block of this big block.
     */
    BlockPos getMainPos();

    /**
     * Called when the redstone level one of our bounding blocks receives changes.
     *
     * @param boundingPos The position of the bounding block that changed
     * @param oldLevel    The previous redstone level
     * @param newLevel    The new redstone level
     */
    default void onBoundingBlockPowerChange(BlockPos boundingPos, int oldLevel, int newLevel) {
    }

    /**
     * Gets the comparator signal to expose at a bounding block.
     *
     * @param offset The offset of the bounding block relative to the main position
     */
    default int getBoundingComparatorSignal(BlockPos offset) {
        return 0;
    }

    /**
     * Checks whether the given capability should <strong>not</strong> be exposed at a bounding block position. By
     * default all capabilities are disabled. Override this to precisely open up specific capabilities at specific
     * offsets and sides.
     *
     * @param capability The capability being queried
     * @param side       The side of the bounding block being interacted with, or null if not sided
     * @param offset     The offset of the bounding block relative to the main position
     */
    default boolean isOffsetCapabilityDisabled(BlockCapability<?, @Nullable Direction> capability, @Nullable Direction side, BlockPos offset) {
        //By default, don't proxy any capabilities to the bounding blocks, which prevents automation (pipes, hoppers,
        // auto-eject) from interacting with the machine through its own bounding blocks
        return true;
    }

    /**
     * Gets the capability as if it was queried at the main position. Only called when
     * {@link #isOffsetCapabilityDisabled(BlockCapability, Direction, BlockPos)} returns false.
     */
    @Nullable
    default <T> T getOffsetCapabilityIfEnabled(BlockCapability<T, @Nullable Direction> capability, @Nullable Direction side, BlockPos offset) {
        //Get the capability as if it was queried at the main position
        if (this instanceof BlockEntity be) {
            Level level = be.getLevel();
            if (level != null) {
                return level.getCapability(capability, be.getBlockPos(), be.getBlockState(), be, side);
            }
        }
        return null;
    }

    /**
     * Gets the capability to expose at the given bounding block offset, or null if it is disabled.
     */
    @Nullable
    default <T> T getOffsetCapability(BlockCapability<T, @Nullable Direction> capability, @Nullable Direction side, BlockPos offset) {
        return isOffsetCapabilityDisabled(capability, side, offset) ? null : getOffsetCapabilityIfEnabled(capability, side, offset);
    }
}
