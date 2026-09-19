package net.yxiao233.industrialforegoingextra.mixin;

import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.buuz135.industrial.block.tile.IndustrialProcessingTile;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.hrznstudio.titanium.module.BlockWithTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;
import net.yxiao233.industrialforegoingextra.api.addon.IFEAddonType;
import net.yxiao233.industrialforegoingextra.util.AugmentInventoryHelper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(IndustrialProcessingTile.class)
public abstract class MixinIndustrialProcessingTile<T extends IndustrialProcessingTile<T>> extends IndustrialMachineTile<T> {
    @Shadow
    public abstract ProgressBarComponent<T> getProgressBar();

    @Shadow
    public abstract int getMaxProgress();

    public MixinIndustrialProcessingTile(BlockWithTile basicTileBlock, BlockPos blockPos, BlockState blockState) {
        super(basicTileBlock, blockPos, blockState);
    }

    @Override
    public boolean canAcceptAugment(ItemStack augment){
        if(augment.getItem() instanceof IFEAddonItem addonItem && addonItem.getType().equals(IFEAddonType.CREATIVE)){
            return AugmentInventoryHelper.canAccept(this,augment);
        }
        return super.canAcceptAugment(augment);
    }

    @Override
    public void serverTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull T blockEntity) {
        super.serverTick(level, pos, state, blockEntity);
        if(AugmentInventoryHelper.contains(this, IFEAddonType.CREATIVE)){
            EnergyStorageComponent<T> energyStorage = this.getEnergyStorage();
            energyStorage.setEnergyStored(Integer.MAX_VALUE);
            this.getProgressBar().setMaxProgress(0);
        }else{
            this.getProgressBar().setMaxProgress(this.getMaxProgress());
        }
    }
}
