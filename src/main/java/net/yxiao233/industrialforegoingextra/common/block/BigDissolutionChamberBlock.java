package net.yxiao233.industrialforegoingextra.common.block;

import com.hrznstudio.titanium.block.RotatableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.yxiao233.industrialforegoingextra.api.block.MultiBlock;
import net.yxiao233.industrialforegoingextra.api.shapes.BigBlockShapes;
import net.yxiao233.industrialforegoingextra.common.tile.BigDissolutionChamberTile;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BigDissolutionChamberBlock extends MultiBlock<BigDissolutionChamberTile> {
    public static final VoxelShape SHAPE = BigBlockShapes.box(-16, 0, -16, 32, 48, 32);
    public BigDissolutionChamberBlock() {
        super("big_dissolution_chamber", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), BigDissolutionChamberTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<BigDissolutionChamberTile> getTileEntityFactory() {
        return BigDissolutionChamberTile::new;
    }

    @Override
    @Nonnull
    public RotatableBlock.RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }

    @Override
    public Iterable<BlockPos> getBoundingPositions(Level level, BlockPos mainPos, BlockState state) {
        List<BlockPos> positions = new ArrayList<>(26);
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y <= 2; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x != 0 || y != 0 || z != 0) {
                        positions.add(mainPos.offset(x, y, z));
                    }
                }
            }
        }
        return positions;
    }

    @Override
    public VoxelShape getProxyShape() {
        return SHAPE;
    }
}
