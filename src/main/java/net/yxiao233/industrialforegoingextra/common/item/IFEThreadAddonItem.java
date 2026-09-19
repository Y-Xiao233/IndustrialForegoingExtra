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

public class IFEThreadAddonItem extends IFEAddonItem {
    public IFEThreadAddonItem(int tier) {
        super(IFEAddonType.THREAD, tier);
    }

    @Override
    public void addTooltipDetails(@Nullable Key key, @NotNull ItemStack stack, @NotNull List<Component> tooltip, boolean advanced) {
        TooltipHelper.addTooltip(tooltip,"thread_addon_item_0", ChatFormatting.GRAY);
        TooltipHelper.addTooltip(tooltip,"thread_addon_item_1",ChatFormatting.GREEN,new Object[]{getTier() * 4});
    }

    @Override
    protected float getFormula() {
        return (float)(this.getTier() * 4);
    }
}
