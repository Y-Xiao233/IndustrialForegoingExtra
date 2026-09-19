package net.yxiao233.industrialforegoingextra.compact.kubejs.items;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.yxiao233.industrialforegoingextra.common.item.IFEHealAddonItem;

public class HealAddonItemBuilder extends ItemBuilder {
    private int tier;
    public HealAddonItemBuilder(ResourceLocation i) {
        super(i);
    }
    public HealAddonItemBuilder setTier(int tier){
        this.tier = tier;
        return this;
    }

    @Override
    public Item createObject() {
        return new IFEHealAddonItem(tier);
    }
}