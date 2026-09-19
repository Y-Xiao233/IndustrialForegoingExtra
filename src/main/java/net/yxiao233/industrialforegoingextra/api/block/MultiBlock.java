package net.yxiao233.industrialforegoingextra.api.block;

import com.hrznstudio.titanium.block.tile.BasicTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.yxiao233.industrialforegoingextra.util.BoundingBlockHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base block class for blocks that occupy more than a single block position. Subclasses declare the extra positions
 * via {@link #getBoundingPositions(Level, BlockPos, BlockState)}, and this class automatically places/removes the
 * {@link BoundingBlock} filler blocks when the block is placed or destroyed.
 *
 * <p>Equivalent of Mekanism's {@code BigBlock} but implemented purely with vanilla NeoForge code.</p>
 *
 * @param <T> the block entity type of this block
 */
public abstract class MultiBlock<T extends BasicTile<T>> extends IFEBlock<T> implements EntityBlock, IBoundingBlockProvider {

    public MultiBlock(String name, Properties properties, Class<T> tileClass) {
        super(name,properties,tileClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state){
        return (T) getTileEntityFactory().create(pos,state);
    };

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BoundingBlockHelper.placeBoundingBlocks(level, pos, state);
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BoundingBlockHelper.removeBoundingBlocks(level, pos, state);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public abstract Iterable<BlockPos> getBoundingPositions(Level level, BlockPos mainPos, BlockState state);

    public abstract VoxelShape getProxyShape();

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return getProxyShape();
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return getProxyShape();
    }
}
