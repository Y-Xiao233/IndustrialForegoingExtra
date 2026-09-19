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

public class IFESpeedAddonItem extends IFEAddonItem {
    public IFESpeedAddonItem(int tier) {
        super(IFEAddonType.SPEED, tier);
    }

    @Override
    public void addTooltipDetails(@Nullable BasicItem.Key key, @NotNull ItemStack stack, List<Component> tooltip, boolean advanced) {
        String var10001 = String.valueOf(ChatFormatting.GRAY);
        tooltip.add(Component.literal(var10001 + Component.translatable("text.industrialforegoing.tooltip.speed").getString() + "+" + this.getTier() * 100 + "%"));
    }

    @Override
    protected float getFormula() {
        return (float) (1 + this.getTier());
    }
}
