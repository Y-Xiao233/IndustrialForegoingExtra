package net.yxiao233.industrialforegoingextra.api.tile;

import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.BasicTile;
import com.hrznstudio.titanium.module.BlockWithTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class MultiBlockTile<T extends BasicTile<T>> extends BasicTile<T> implements IBoundingTile {
    private int boundingPower;
    @SuppressWarnings("unchecked")
    public MultiBlockTile(BlockWithTile tile, BlockPos pos, BlockState state) {
        super((BasicTileBlock<T>) tile.block().get(), tile.type().get(), pos, state);
    }

    @Override
    public void onBoundingBlockPowerChange(BlockPos boundingPos, int oldLevel, int newLevel) {
        //The bounding block above forwards redstone changes to this machine
        boundingPower = newLevel;
        setChanged();
    }

    @Override
    public int getBoundingComparatorSignal(BlockPos offset) {
        //Expose the same signal a comparator reading the main block would get
        return level == null ? 0 : level.getBestNeighborSignal(worldPosition);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("bounding_power", boundingPower);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        boundingPower = tag.getInt("bounding_power");
    }
}
