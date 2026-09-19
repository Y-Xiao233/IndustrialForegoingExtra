package net.yxiao233.industrialforegoingextra.compact.ars_nouveau;

import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ArsNouveauCompact {
    public static LivingEntity getMobJarEntity(Level level, BlockPos pos){
        BlockEntity blockEntity = level.getBlockEntity(pos.below());
        if(blockEntity instanceof MobJarTile mobJarTile){
            Entity entity = mobJarTile.getEntity();
            if(entity instanceof LivingEntity livingEntity){
                return livingEntity;
            }
            return null;
        }
        return null;
    }
}
