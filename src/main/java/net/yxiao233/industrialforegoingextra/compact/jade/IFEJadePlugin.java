package net.yxiao233.industrialforegoingextra.compact.jade;

import com.buuz135.industrial.block.IndustrialBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.yxiao233.industrialforegoingextra.api.block.BoundingBlock;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.compact.jade.provider.MachineTileProvider;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class IFEJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new MachineTileProvider(), IndustrialBlock.class);

        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            //Redirect bounding blocks to the main tile for purposes of capability lookups and the like
            if (accessor instanceof BlockAccessor target && target.getBlockState().is(IFEBlocks.BOUNDING.getBlock())) {
                Level level = target.getLevel();
                BlockHitResult blockHitResult = target.getHitResult();
                BlockPos mainPos = BoundingBlock.getMainBlockPos(level, blockHitResult.getBlockPos());
                if (mainPos != null) {
                    return registration.blockAccessor()
                            .from(target)
                            .hit(blockHitResult.withPosition(mainPos))
                            .blockState(level.getBlockState(mainPos))
                            .blockEntity(level.getBlockEntity(mainPos))
                            .build();
                }
            }
            return accessor;
        });
    }
}
