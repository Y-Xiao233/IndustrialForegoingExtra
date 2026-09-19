package net.yxiao233.industrialforegoingextra.common.item;

import com.hrznstudio.titanium.item.BasicItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;
import net.yxiao233.industrialforegoingextra.api.addon.IFEAddonType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class IFEEfficiencyAddonItem extends IFEAddonItem {
    public IFEEfficiencyAddonItem(int tier) {
        super(IFEAddonType.EFFICIENCY, tier);
    }

    public void addTooltipDetails(@Nullable BasicItem.Key key, @NotNull ItemStack stack, List<Component> tooltip, boolean advanced) {
        String var10001 = String.valueOf(ChatFormatting.GRAY);
        tooltip.add(Component.literal(var10001 + Component.translatable("text.industrialforegoing.tooltip.efficiency").getString() + "-" + this.getTier() * 10 + "%"));
    }

    @Override
    protected float getFormula() {
        return 1.0F - (float) this.getTier() * 0.1F;
    }
}
