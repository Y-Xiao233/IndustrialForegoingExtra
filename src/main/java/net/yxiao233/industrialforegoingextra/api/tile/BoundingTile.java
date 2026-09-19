package net.yxiao233.industrialforegoingextra.api.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.block.BoundingBlock;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Block entity of the {@link BoundingBlock} filler block. Stores the position of the main block and redirects
 * redstone/comparator queries back to it.
 */
public class BoundingTile extends BlockEntity {

    @Nullable
    private BlockPos mainPos;
    private int currentRedstoneLevel;
    public BoundingTile(BlockPos pos, BlockState state) {
        super(IFEBlocks.BOUNDING.type().get(), pos, state);
    }

    public void setMainLocation(@Nullable BlockPos pos) {
        mainPos = pos == null ? null : pos.immutable();
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            //Belt-and-braces: also send our data to all players tracking this chunk right away, so the client always
            // learns the main position even if the initial chunk packet was built before it was set
            if (level instanceof ServerLevel serverLevel) {
                Packet<?> packet = ClientboundBlockEntityDataPacket.create(this);
                for (ServerPlayer player : serverLevel.getChunkSource().chunkMap.getPlayers(new ChunkPos(worldPosition), false)) {
                    player.connection.send(packet);
                }
            }
        }
    }

    public boolean hasMainPos() {
        return mainPos != null;
    }

    public BlockPos getMainPos() {
        return mainPos == null ? worldPosition : mainPos;
    }

    @Nullable
    public IBoundingTile getMain() {
        if (level == null || mainPos == null) {
            return null;
        }
        BlockEntity tile = level.getBlockEntity(mainPos);
        return tile instanceof IBoundingTile main ? main : null;
    }

    /**
     * Gets the capability to expose at this bounding block position by proxying to the main block entity, or null if
     * the main block entity has not enabled it for this offset (see
     * {@link IBoundingTile#isOffsetCapabilityDisabled(BlockCapability, Direction, BlockPos)}).
     */
    @Nullable
    public <T> T getOffsetCapability(BlockCapability<T, @Nullable Direction> capability, @Nullable Direction side, BlockPos offset) {
        IBoundingTile main = getMain();
        return main == null ? null : main.getOffsetCapability(capability, side, offset);
    }

    public void onNeighborChange(Level level) {
        int power = level.getBestNeighborSignal(worldPosition);
        if (currentRedstoneLevel != power) {
            IBoundingTile main = getMain();
            if (main != null) {
                main.onBoundingBlockPowerChange(worldPosition, currentRedstoneLevel, power);
            }
            currentRedstoneLevel = power;
        }
    }

    public int getComparatorSignal() {
        IBoundingTile main = getMain();
        if (main != null) {
            return main.getBoundingComparatorSignal(worldPosition.subtract(getMainPos()));
        }
        return 0;
    }

    /**
     * Required so the client learns the main position when the world is loaded from disk. The client only receives
     * block entity data when {@link BlockEntity#getUpdatePacket()} returns non-null, and by default it returns null.
     */
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("main_pos", Tag.TAG_INT_ARRAY)) {
            mainPos = NbtUtils.readBlockPos(tag, "main_pos").orElse(null);
        }
        currentRedstoneLevel = tag.getInt("redstone");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (mainPos != null) {
            tag.put("main_pos", NbtUtils.writeBlockPos(mainPos));
        }
        tag.putInt("redstone", currentRedstoneLevel);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag updateTag = super.getUpdateTag(registries);
        if (mainPos != null) {
            updateTag.put("main_pos", NbtUtils.writeBlockPos(mainPos));
        }
        updateTag.putInt("redstone", currentRedstoneLevel);
        return updateTag;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.handleUpdateTag(tag, registries);
        if (tag.contains("main_pos", Tag.TAG_INT_ARRAY)) {
            mainPos = NbtUtils.readBlockPos(tag, "main_pos").orElse(null);
        }
        currentRedstoneLevel = tag.getInt("redstone");
    }
}
