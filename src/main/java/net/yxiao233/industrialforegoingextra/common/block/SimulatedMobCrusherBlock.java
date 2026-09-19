package net.yxiao233.industrialforegoingextra.common.block;

import com.hrznstudio.titanium.block.RotatableBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.yxiao233.industrialforegoingextra.api.block.SimulatedBlock;
import net.yxiao233.industrialforegoingextra.common.tile.SimulatedMobCrusherTile;
import org.jetbrains.annotations.NotNull;

public class SimulatedMobCrusherBlock extends SimulatedBlock<SimulatedMobCrusherTile> {
    public SimulatedMobCrusherBlock() {
        super("simulated", SimulatedMobCrusherTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return SimulatedMobCrusherTile::new;
    }

    @NotNull
    @Override
    public RotatableBlock.RotationType getRotationType() {
        return RotatableBlock.RotationType.FOUR_WAY;
    }
}
