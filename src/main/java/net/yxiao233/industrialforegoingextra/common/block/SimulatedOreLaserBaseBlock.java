package net.yxiao233.industrialforegoingextra.common.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.yxiao233.industrialforegoingextra.api.block.SimulatedBlock;
import net.yxiao233.industrialforegoingextra.common.tile.SimulatedOreLaserBaseTile;

public class SimulatedOreLaserBaseBlock extends SimulatedBlock<SimulatedOreLaserBaseTile> {
    public SimulatedOreLaserBaseBlock() {
        super("simulated_ore_laser_base", SimulatedOreLaserBaseTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return SimulatedOreLaserBaseTile::new;
    }
}
