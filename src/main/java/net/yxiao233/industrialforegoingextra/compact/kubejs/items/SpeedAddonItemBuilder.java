package net.yxiao233.industrialforegoingextra.compact.kubejs.items;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.yxiao233.industrialforegoingextra.common.item.IFESpeedAddonItem;

public class SpeedAddonItemBuilder extends ItemBuilder {
    private int tier;
    public SpeedAddonItemBuilder(ResourceLocation i) {
        super(i);
    }
    public SpeedAddonItemBuilder setTier(int tier){
        this.tier = tier;
        return this;
    }

    @Override
    public Item createObject() {
        return new IFESpeedAddonItem(tier);
    }
}
