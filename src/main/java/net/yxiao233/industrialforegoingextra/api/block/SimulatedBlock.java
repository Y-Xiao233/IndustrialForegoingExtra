package net.yxiao233.industrialforegoingextra.api.block;

import com.buuz135.industrial.block.tile.IndustrialWorkingTile;
import net.minecraft.world.level.block.Blocks;

public abstract class SimulatedBlock<T extends IndustrialWorkingTile<T>> extends IFEBlock<T> {
    public SimulatedBlock(String name, Class<T> tileClass) {
        super(name, Properties.ofFullCopy(Blocks.IRON_BLOCK), tileClass);
    }
}
