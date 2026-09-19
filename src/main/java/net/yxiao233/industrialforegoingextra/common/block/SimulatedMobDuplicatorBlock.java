package net.yxiao233.industrialforegoingextra.common.block;

import com.buuz135.industrial.block.IndustrialBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.common.tile.SimulatedMobDuplicatorTile;

public class SimulatedMobDuplicatorBlock extends IFEBlock<SimulatedMobDuplicatorTile> {
    public SimulatedMobDuplicatorBlock() {
        super("simulated_mob_duplicator", Properties.ofFullCopy(Blocks.IRON_BLOCK), SimulatedMobDuplicatorTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return SimulatedMobDuplicatorTile::new;
    }
}
