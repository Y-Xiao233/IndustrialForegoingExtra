package net.yxiao233.industrialforegoingextra.compact.kubejs.items;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.yxiao233.industrialforegoingextra.common.item.IFEEfficiencyAddonItem;

public class EfficiencyAddonItemBuilder extends ItemBuilder {
    private int tier;
    public EfficiencyAddonItemBuilder(ResourceLocation i) {
        super(i);
    }

    public EfficiencyAddonItemBuilder setTier(int tier) {
        this.tier = tier;
        return this;
    }

    @Override
    public Item createObject() {
        return new IFEEfficiencyAddonItem(tier);
    }
}
