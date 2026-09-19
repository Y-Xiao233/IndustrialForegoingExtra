package net.yxiao233.industrialforegoingextra.mixin;

import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.buuz135.industrial.block.tile.IndustrialWorkingTile;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IndustrialWorkingTile.class)
public abstract class MixinIndustrialWorkingTile<T extends IndustrialWorkingTile<T>> extends IndustrialMachineTile<T> {
    public MixinIndustrialWorkingTile(BlockWithTile basicTileBlock, BlockPos blockPos, BlockState blockState) {
        super(basicTileBlock, blockPos, blockState);
    }

    @Inject(method = "canAcceptAugment", at = @At("HEAD"),cancellable = true)
    public void industrialforegoingextra$canAcceptAugment(ItemStack augment, CallbackInfoReturnable<Boolean> cir){
        if(augment.getItem() instanceof IFEAddonItem addonItem && addonItem.getType().equals(IFEAddonType.CREATIVE)){
            cir.setReturnValue(AugmentInventoryHelper.canAccept(this,augment));
        }
    }

    @Override
    public void serverTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull T blockEntity) {
        super.serverTick(level, pos, state, blockEntity);
        if(AugmentInventoryHelper.contains(this, IFEAddonType.CREATIVE)){
            EnergyStorageComponent<T> energyStorage = this.getEnergyStorage();
            energyStorage.setEnergyStored(Integer.MAX_VALUE);
        }
    }
}
