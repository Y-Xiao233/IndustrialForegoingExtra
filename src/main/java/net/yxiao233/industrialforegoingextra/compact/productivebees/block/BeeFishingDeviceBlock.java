package net.yxiao233.industrialforegoingextra.compact.productivebees.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.compact.productivebees.tile.BeeFishingDeviceTile;
import org.jetbrains.annotations.NotNull;

public class BeeFishingDeviceBlock extends IFEBlock<BeeFishingDeviceTile> {
    public BeeFishingDeviceBlock() {
        super("bee_fishing_device", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), BeeFishingDeviceTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return BeeFishingDeviceTile::new;
    }

    @Override
    public @NotNull RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }
}