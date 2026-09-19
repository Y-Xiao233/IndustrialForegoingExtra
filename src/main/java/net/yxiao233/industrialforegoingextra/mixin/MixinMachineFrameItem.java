package net.yxiao233.industrialforegoingextra.mixin;

import com.buuz135.industrial.block.MachineFrameBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MachineFrameBlock.MachineFrameItem.class)
public abstract class MixinMachineFrameItem extends BlockItem {
    public MixinMachineFrameItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    protected boolean canPlace(@NotNull BlockPlaceContext context, @NotNull BlockState state) {
        return true;
    }
}

