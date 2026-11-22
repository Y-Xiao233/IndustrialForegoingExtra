package net.yxiao233.industrialforegoingextra.compact.productivebees.block;

import com.hrznstudio.titanium.block.RotatableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.yxiao233.industrialforegoingextra.api.block.IFEBlock;
import net.yxiao233.industrialforegoingextra.compact.productivebees.tile.BeeConverterTile;
import org.jetbrains.annotations.NotNull;

public class BeeConverterBlock extends IFEBlock<BeeConverterTile> {
    public BeeConverterBlock() {
        super("bee_converter", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), BeeConverterTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return BeeConverterTile::new;
    }

    @Override
    public @NotNull RotatableBlock.RotationType getRotationType() {
        return RotatableBlock.RotationType.FOUR_WAY;
    }
}
