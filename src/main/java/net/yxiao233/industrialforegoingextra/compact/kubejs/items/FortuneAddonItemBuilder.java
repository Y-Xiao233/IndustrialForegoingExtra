package net.yxiao233.industrialforegoingextra.compact.kubejs.items;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.yxiao233.industrialforegoingextra.common.item.IFEEnergyAddonItem;
import net.yxiao233.industrialforegoingextra.common.item.IFEFortuneAddonItem;

public class FortuneAddonItemBuilder extends ItemBuilder {
    private int tier;
    public FortuneAddonItemBuilder(ResourceLocation i) {
        super(i);
    }
    public FortuneAddonItemBuilder setTier(int tier){
        this.tier = tier;
        return this;
    }

    @Override
    public Item createObject() {
        return new IFEFortuneAddonItem(tier);
    }
}