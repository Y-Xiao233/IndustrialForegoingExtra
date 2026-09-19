package net.yxiao233.industrialforegoingextra.common.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.common.tile.CreativeCapacitorTile;
import org.jetbrains.annotations.NotNull;

public class CreativeCapacitorBlock extends IFEBlock<CreativeCapacitorTile> {
    public CreativeCapacitorBlock() {
        super("creative_capacitor", Properties.ofFullCopy(Blocks.IRON_BLOCK), CreativeCapacitorTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return CreativeCapacitorTile::new;
    }

    @NotNull
    @Override
    public RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }
}
