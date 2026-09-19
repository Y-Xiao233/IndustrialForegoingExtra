package net.yxiao233.industrialforegoingextra.mixin;

import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.MachineTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.industrialforegoingextra.api.addon.EnergyAddonMixinEntry;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;
import net.yxiao233.industrialforegoingextra.api.addon.IFEAddonType;
import net.yxiao233.industrialforegoingextra.api.tile.MultiIndustrialProcessingTile;
import net.yxiao233.industrialforegoingextra.util.AugmentInventoryHelper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(IndustrialMachineTile.class)
public abstract class MixinIndustrialMachineTile<T extends IndustrialMachineTile<T>> extends MachineTile<T> {
    @Unique
    private final AtomicInteger industrialforegoingextra$baseCapacity = industrialforegoingextra$getBaseCapacity();
    public MixinIndustrialMachineTile(BasicTileBlock<T> basicTileBlock, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(basicTileBlock, blockEntityType, pos, state);
    }
    @Unique
    public final AtomicInteger industrialforegoingextra$getBaseCapacity(){
        return new AtomicInteger(this.getEnergyStorage().getMaxEnergyStored());
    }

    @Inject(method = "canAcceptAugment", at = @At("HEAD"), cancellable = true)
    private void industrialforegoingextra$canAcceptAugment(ItemStack augment, CallbackInfoReturnable<Boolean> cir){
        if(augment.getItem() instanceof IFEAddonItem addonItem && addonItem.getType().equals(IFEAddonType.ENERGY)){
            cir.setReturnValue(AugmentInventoryHelper.canAccept(this,augment));
        }
    }

    @Inject(method = "onNeighborChanged", at = @At("HEAD"), cancellable = true)
    private void industrialforegoingextra$onNeighborChanged(Block blockIn, BlockPos fromPos, CallbackInfo ci){
        if(this.getSelf() instanceof MultiIndustrialProcessingTile<?>){
            super.onNeighborChanged(blockIn,fromPos);
            ci.cancel();
        }
    }

    @Override
    public void serverTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull T blockEntity) {
        super.serverTick(level, pos, state, blockEntity);
        EnergyAddonMixinEntry.create(this, this.industrialforegoingextra$baseCapacity.get()).updateEnergyCapacity();
    }

    @Inject(
            method = "clientTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/buuz135/industrial/block/tile/IndustrialMachineTile;)V",
            at = @At("RETURN")
    )
    private void onClientTick(Level level, BlockPos pos, BlockState state, T blockEntity, CallbackInfo ci){
        EnergyAddonMixinEntry.create(this, this.industrialforegoingextra$baseCapacity.get()).updateEnergyCapacity();
    }
}
