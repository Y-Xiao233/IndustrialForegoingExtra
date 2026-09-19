package net.yxiao233.industrialforegoingextra.compact.kubejs.items;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.yxiao233.industrialforegoingextra.common.item.IFEEnergyAddonItem;
import net.yxiao233.industrialforegoingextra.common.item.IFELootingAddonItem;

public class LootingAddonItemBuilder extends ItemBuilder {
    private int tier;
    public LootingAddonItemBuilder(ResourceLocation i) {
        super(i);
    }
    public LootingAddonItemBuilder setTier(int tier){
        this.tier = tier;
        return this;
    }

    @Override
    public Item createObject() {
        return new IFELootingAddonItem(tier);
    }
}