package net.yxiao233.industrialforegoingextra.api.item;

import com.buuz135.industrial.block.IndustrialBlockItem;
import com.hrznstudio.titanium.tab.TitaniumTab;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.industrialforegoingextra.util.BoundingBlockHelper;
import org.jetbrains.annotations.NotNull;

public class BigIndustrialBlockItem extends IndustrialBlockItem {
    public BigIndustrialBlockItem(Block blockIn, TitaniumTab group) {
        super(blockIn, group);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, @NotNull BlockState state) {
        //Refuse to place the machine if any of its bounding positions is occupied by another block
        if (!BoundingBlockHelper.canPlaceBoundingBlocks(context.getLevel(), context.getClickedPos(), state)) {
            return false;
        }
        return super.placeBlock(context, state);
    }
}
