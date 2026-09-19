package net.yxiao233.industrialforegoingextra.mixin;

import com.buuz135.industrial.block.misc.tile.StasisChamberTile;
import com.buuz135.industrial.block.tile.IndustrialAreaWorkingTile;
import com.buuz135.industrial.block.tile.RangeManager;
import com.hrznstudio.titanium.module.BlockWithTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;
import net.yxiao233.industrialforegoingextra.api.addon.IFEAddonType;
import net.yxiao233.industrialforegoingextra.util.AugmentInventoryHelper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StasisChamberTile.class)
public abstract class MixinStasisChamberTile extends IndustrialAreaWorkingTile<StasisChamberTile> {
    @Unique
    private int industrialforegoingextra$healAddonTier = -1;

    public MixinStasisChamberTile(BlockWithTile basicTileBlock, RangeManager.RangeType type, boolean acceptsRangeUpgrades, int estimatedPower, BlockPos blockPos, BlockState blockState) {
        super(basicTileBlock, type, acceptsRangeUpgrades, estimatedPower, blockPos, blockState);
    }


    @Redirect(
            method = "work",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Mob;heal(F)V",
                    ordinal = 0
            )
    )
    private void redirectHeal(Mob entity, float amount) {
        entity.heal(1 + industrialforegoingextra$healAddonTier * 3);
    }

    @Override
    public void serverTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull StasisChamberTile blockEntity) {
        super.serverTick(level, pos, state, blockEntity);
        industrialforegoingextra$healAddonTier = AugmentInventoryHelper.getAugmentTier(this, IFEAddonType.HEAL);
    }

    @Override
    public boolean canAcceptAugment(ItemStack augment) {
        if(augment.getItem() instanceof IFEAddonItem item && item.getType().equals(IFEAddonType.HEAL)){
            return AugmentInventoryHelper.canAccept(this,augment);
        }
        return super.canAcceptAugment(augment);
    }
}
