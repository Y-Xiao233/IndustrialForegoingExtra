package net.yxiao233.industrialforegoingextra.api.block;

import com.buuz135.industrial.block.IndustrialBlock;
import com.hrznstudio.titanium.block.tile.BasicTile;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;

public abstract class IFEBlock<T extends BasicTile<T>> extends IndustrialBlock<T> {
    public IFEBlock(String name, Properties properties, Class<T> tileClass) {
        super(name, properties, tileClass, IndustrialForegoingExtra.TAB);
    }
}
