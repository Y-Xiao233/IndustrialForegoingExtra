package net.yxiao233.industrialforegoingextra.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;
import net.yxiao233.industrialforegoingextra.api.addon.IFEAddonType;
import net.yxiao233.industrialforegoingextra.util.TooltipHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class IFEEnergyAddonItem extends IFEAddonItem {
    public IFEEnergyAddonItem(int tier) {
        super(IFEAddonType.ENERGY, tier);
    }

    @Override
    public void addTooltipDetails(@Nullable Key key, @NotNull ItemStack stack, @NotNull List<Component> tooltip, boolean advanced) {
        TooltipHelper.addTooltip(tooltip,"energy_addon_item_0",ChatFormatting.GREEN,new Object[]{Math.min((int) (Math.pow(10,(getTier() + 2) / 2D)),Integer.MAX_VALUE)});
    }

    @Override
    protected float getFormula() {
        return Math.min((int) (Math.pow(10,(getTier() + 2) / 2D)),Integer.MAX_VALUE);
    }
}
