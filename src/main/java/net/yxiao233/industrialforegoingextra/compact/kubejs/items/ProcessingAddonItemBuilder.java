package net.yxiao233.industrialforegoingextra.compact.kubejs.items;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.yxiao233.industrialforegoingextra.common.item.IFEProcessingAddonItem;

public class ProcessingAddonItemBuilder extends ItemBuilder {
    private int tier;
    public ProcessingAddonItemBuilder(ResourceLocation i) {
        super(i);
    }
    public ProcessingAddonItemBuilder setTier(int tier){
        this.tier = tier;
        return this;
    }

    @Override
    public Item createObject() {
        return new IFEProcessingAddonItem(tier);
    }
}