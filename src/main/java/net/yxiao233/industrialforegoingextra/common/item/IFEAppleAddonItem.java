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

public class IFEAppleAddonItem extends IFEAddonItem {
    public IFEAppleAddonItem(int tier) {
        super(IFEAddonType.APPLE, tier);
    }

    @Override
    public void addTooltipDetails(@Nullable Key key, @NotNull ItemStack stack, @NotNull List<Component> tooltip, boolean advanced) {
        TooltipHelper.addTooltip(tooltip,"apple_addon_item_0", ChatFormatting.GRAY);
        TooltipHelper.addTooltip(tooltip,"apple_addon_item_1",ChatFormatting.GREEN,new Object[]{getTier()});
    }

    @Override
    protected float getFormula() {
        return (float)(this.getTier());
    }
}
