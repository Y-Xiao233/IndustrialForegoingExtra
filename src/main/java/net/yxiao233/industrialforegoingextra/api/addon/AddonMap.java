package net.yxiao233.industrialforegoingextra.api.addon;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

public class AddonMap {
    private final HashMap<String,DeferredHolder<Item, Item>> map = new HashMap<>();
    private final IAddonType type;
    public AddonMap(IAddonType type){
        this.type = type;
    }
    public void put(String key, DeferredHolder<Item, Item> entry){
        map.put(key,entry);
    }
    public DeferredHolder<Item,Item> getAddonItem(int tier){
        return map.getOrDefault(type.getTypeId() + "_addon" + (tier > 0 ? "_" + tier : ""),null);
    }
    public void forEach(Consumer<Item> action){
        Objects.requireNonNull(action);
        for (DeferredHolder<Item, Item> item : map.values()) {
            action.accept(item.get());
        }
    }
}
