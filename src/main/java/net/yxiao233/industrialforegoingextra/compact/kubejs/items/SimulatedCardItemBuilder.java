package net.yxiao233.industrialforegoingextra.compact.kubejs.items;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.yxiao233.industrialforegoingextra.api.data.KubeSimulatedCard;
import net.yxiao233.industrialforegoingextra.common.item.IFEEnergyAddonItem;

public class SimulatedCardItemBuilder extends ItemBuilder {
    private int maxFluidDataStorage = 1;
    private int maxMobDataStorage = 1;
    private int maxOreDataStorage = 4;
    public SimulatedCardItemBuilder(ResourceLocation i) {
        super(i);
    }
    public SimulatedCardItemBuilder setMaxFluidDataStorage(int amount){
        this.maxFluidDataStorage = amount;
        return this;
    }

    public SimulatedCardItemBuilder setMaxMobDataStorage(int amount){
        this.maxMobDataStorage = amount;
        return this;
    }

    public SimulatedCardItemBuilder setMaxOreDataStorage(int amount){
        this.maxOreDataStorage = amount;
        return this;
    }

    @Override
    public Item createObject() {
        return new KubeSimulatedCard(maxFluidDataStorage,maxMobDataStorage,maxOreDataStorage);
    }
}