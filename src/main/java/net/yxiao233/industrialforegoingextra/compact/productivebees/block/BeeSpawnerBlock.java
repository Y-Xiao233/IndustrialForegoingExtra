package net.yxiao233.industrialforegoingextra.compact.productivebees.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.compact.productivebees.tile.BeeSpawnerTile;
import org.jetbrains.annotations.NotNull;

public class BeeSpawnerBlock extends IFEBlock<BeeSpawnerTile> {
    public BeeSpawnerBlock() {
        super("bee_spawner", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), BeeSpawnerTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return BeeSpawnerTile::new;
    }

    @Override
    public @NotNull RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }
}
