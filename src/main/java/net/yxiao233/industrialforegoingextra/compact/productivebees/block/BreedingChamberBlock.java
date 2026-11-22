package net.yxiao233.industrialforegoingextra.compact.productivebees.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.compact.productivebees.tile.BreedingChamberTile;
import org.jetbrains.annotations.NotNull;

public class BreedingChamberBlock extends IFEBlock<BreedingChamberTile> {
    public BreedingChamberBlock() {
        super("breeding_chamber", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), BreedingChamberTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return BreedingChamberTile::new;
    }

    @Override
    public @NotNull RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }
}
