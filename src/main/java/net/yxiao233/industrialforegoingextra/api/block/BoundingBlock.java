package net.yxiao233.industrialforegoingextra.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.tile.BoundingTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

/**
 * The invisible filler block used to occupy the extra positions of a big block. It is not a real "bounding" block;
 * it just mimics the bounds of the main block and redirects everything (interactions, breaking, shapes, explosions,
 * comparator/redstone) back to the main block.
 *
 * <p>Equivalent of Mekanism's {@code BlockBounding}, implemented purely with vanilla NeoForge code.</p>
 */
public class BoundingBlock extends Block implements EntityBlock {
    @Nullable
    public static BlockPos getMainBlockPos(BlockGetter world, BlockPos thisPos) {
        BlockEntity blockEntity = world.getBlockEntity(thisPos);
        if (blockEntity instanceof BoundingTile bounding && bounding.hasMainPos()) {
            return bounding.getMainPos();
        }
        return null;
    }

    public BoundingBlock(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(3.5F, 4.8F).requiresCorrectToolForDrops().dynamicShape().noOcclusion().pushReaction(PushReaction.BLOCK).noLootTable());
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new BoundingTile(pos, state);
    }

    @Override
    protected boolean canBeReplaced(@NotNull BlockState state, @NotNull BlockPlaceContext context) {
        return getMainBlockPos(context.getLevel(), context.getClickedPos()) == null;
    }

    @Override
    protected boolean canBeReplaced(@NotNull BlockState state, @NotNull Fluid fluid) {
        return false;
    }

    @NotNull
    @Override
    protected InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return InteractionResult.FAIL;
        }
        return world.getBlockState(mainPos).useWithoutItem(world, player, withHitResultForMain(hit, mainPos));
    }

    @NotNull
    @Override
    protected ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return ItemInteractionResult.FAIL;
        }
        return world.getBlockState(mainPos).useItemOn(stack, world, player, hand, withHitResultForMain(hit, mainPos));
    }

    private BlockHitResult withHitResultForMain(BlockHitResult hit, BlockPos mainPos) {
        Vec3 location = mainPos.getCenter().relative(hit.getDirection(), 0.5);
        if (hit.getType() == Type.MISS) {
            return BlockHitResult.miss(location, hit.getDirection(), mainPos);
        }
        return new BlockHitResult(location, hit.getDirection(), mainPos, hit.isInside());
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockPos mainPos = getMainBlockPos(world, pos);
            if (mainPos != null) {
                BlockState mainState = world.getBlockState(mainPos);
                if (!mainState.isAir()) {
                    world.removeBlock(mainPos, false);
                }
            }
            super.onRemove(state, world, pos, newState, movedByPiston);
        }
    }

    @NotNull
    @Override
    public ItemStack getCloneItemStack(@NotNull BlockState state, @NotNull HitResult target, @NotNull LevelReader world, @NotNull BlockPos pos, @NotNull Player player) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return ItemStack.EMPTY;
        }
        BlockState mainState = world.getBlockState(mainPos);
        return mainState.getBlock().getCloneItemStack(mainState, target, world, mainPos, player);
    }

    @Override
    public boolean onDestroyedByPlayer(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, boolean willHarvest,
          @NotNull FluidState fluidState) {
        if (willHarvest) {
            return true;
        }
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos != null) {
            BlockState mainState = world.getBlockState(mainPos);
            if (!mainState.isAir()) {
                return mainState.onDestroyedByPlayer(world, mainPos, player, false, mainState.getFluidState());
            }
        }
        return super.onDestroyedByPlayer(state, world, pos, player, false, fluidState);
    }

    @NotNull
    @Override
    public BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        BlockPos mainPos = getMainBlockPos(level, pos);
        if (mainPos != null) {
            BlockState mainState = level.getBlockState(mainPos);
            if (!mainState.isAir()) {
                mainState.getBlock().playerWillDestroy(level, mainPos, mainState, player);
                return state;
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected void onExplosionHit(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Explosion explosion,
          @NotNull BiConsumer<ItemStack, BlockPos> dropConsumer) {
        BlockPos mainPos = getMainBlockPos(level, pos);
        if (mainPos == null) {
            super.onExplosionHit(state, level, pos, explosion, dropConsumer);
        } else {
            level.getBlockState(mainPos).onExplosionHit(level, mainPos, explosion, dropConsumer);
        }
    }

    @Override
    protected void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        BlockPos mainPos = getMainBlockPos(level, pos);
        if (mainPos != null) {
            BlockState mainState = level.getBlockState(mainPos);
            if (!mainState.isAir()) {
                mainState.spawnAfterBreak(level, mainPos, stack, dropExperience);
            }
        }
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
    }

    @Override
    public void playerDestroy(@NotNull Level world, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity,
          @NotNull ItemStack stack) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos != null) {
            BlockState mainState = world.getBlockState(mainPos);
            mainState.getBlock().playerDestroy(world, player, mainPos, mainState, world.getBlockEntity(mainPos), stack);
        } else {
            super.playerDestroy(world, player, pos, state, blockEntity, stack);
        }
        world.removeBlock(pos, false);
    }

    @Override
    protected void neighborChanged(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
        if (world.getBlockEntity(pos) instanceof BoundingTile tile) {
            tile.onNeighborChange(world);
        }
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos != null) {
            world.getBlockState(mainPos).handleNeighborChanged(world, mainPos, neighborBlock, neighborPos, movedByPiston);
        }
    }

    @Override
    protected boolean hasAnalogOutputSignal(@NotNull BlockState blockState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos pos) {
        if (!world.isClientSide) {
            if (world.getBlockEntity(pos) instanceof BoundingTile tile) {
                return tile.getComparatorSignal();
            }
        }
        return 0;
    }

    @Override
    protected float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return super.getDestroyProgress(state, player, world, pos);
        }
        return world.getBlockState(mainPos).getDestroyProgress(player, world, mainPos);
    }

    @Override
    public float getExplosionResistance(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Explosion explosion) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return super.getExplosionResistance(state, world, pos, explosion);
        }
        return world.getBlockState(mainPos).getExplosionResistance(world, mainPos, explosion);
    }

    @NotNull
    @Override
    protected RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @NotNull
    @Override
    protected VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return proxyShape(world, pos, context, (s, level, p, ctx) -> s.getShape(level, p, ctx));
    }

    @NotNull
    @Override
    protected VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return proxyShape(world, pos, context, (s, level, p, ctx) -> s.getCollisionShape(level, p, ctx));
    }

    @NotNull
    @Override
    protected VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return proxyShape(world, pos, context, (s, level, p, ctx) -> s.getVisualShape(level, p, ctx));
    }

    @NotNull
    @Override
    protected VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return proxyShape(world, pos, null, (s, level, p, ctx) -> s.getOcclusionShape(level, p));
    }

    @NotNull
    @Override
    protected VoxelShape getBlockSupportShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return proxyShape(world, pos, null, (s, level, p, ctx) -> s.getBlockSupportShape(level, p));
    }

    @NotNull
    @Override
    protected VoxelShape getInteractionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return proxyShape(world, pos, null, (s, level, p, ctx) -> s.getInteractionShape(level, p));
    }

    private VoxelShape proxyShape(BlockGetter world, BlockPos pos, @Nullable CollisionContext context, ShapeProxy proxy) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return Shapes.empty();
        }
        BlockState mainState = world.getBlockState(mainPos);
        VoxelShape shape = proxy.getShape(mainState, world, mainPos, context);
        BlockPos offset = pos.subtract(mainPos);
        return shape.move(-offset.getX(), -offset.getY(), -offset.getZ());
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
        return false;
    }

    private interface ShapeProxy {
        VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, @Nullable CollisionContext context);
    }
}
