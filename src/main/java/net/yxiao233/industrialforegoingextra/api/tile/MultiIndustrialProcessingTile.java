package net.yxiao233.industrialforegoingextra.api.tile;

import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
import com.hrznstudio.titanium.module.BlockWithTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.industrialforegoingextra.util.BoundingBlockHelper;
import org.jetbrains.annotations.NotNull;

public abstract class MultiIndustrialProcessingTile<T extends MultiIndustrialProcessingTile<T>> extends IndustrialProcessingTile<T> implements IBoundingTile {
    private int boundingPower;
    public MultiIndustrialProcessingTile(BlockWithTile basicTileBlock, int x, int y, BlockPos blockPos, BlockState blockState) {
        super(basicTileBlock, x, y, blockPos, blockState);
    }

    @Override
    public void onBoundingBlockPowerChange(BlockPos boundingPos, int oldLevel, int newLevel) {
        //The bounding block above forwards redstone changes to this machine
        boundingPower = newLevel;
        this.getRedstoneManager().setLastRedstoneState(boundingPower > 0);
        setChanged();
    }

    @Override
    public void onNeighborChanged(Block blockIn, BlockPos fromPos) {
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

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide) {
            //Re-sync our position to all bounding blocks so the client always learns the link, no matter in which
            // order the chunks and their block entity data were sent when loading the world
            BoundingBlockHelper.syncMasterPosition(level, worldPosition, getBlockState());
        }
    }

    public abstract void checkForRecipe();

    @Override
    public void setChanged() {
        super.setChanged();
        this.checkForRecipe();
    }

    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);
        this.checkForRecipe();
    }

    @Override
    public BlockPos getMainPos() {
        return worldPosition;
    }
}
