package net.yxiao233.industrialforegoingextra.util;

import com.hrznstudio.titanium.block.tile.MachineTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.industrialforegoingextra.api.addon.IAddonType;
import net.yxiao233.industrialforegoingextra.api.item.IFEAddonItem;

public class AugmentInventoryHelper {

    public static int getAugmentIndex(InventoryComponent<?> augmentInventory, IAddonType type){
        for (int i = 0; i < augmentInventory.getSlots(); i++) {
            if(augmentInventory.getStackInSlot(i).getItem() instanceof IFEAddonItem addonItem){
                if(addonItem.getType() == type){
                    return i;
                }
            }
        }
        return -1;
    }

    public static int getAugmentIndex(MachineTile<?> tile, IAddonType type){
        return getAugmentIndex(tile.getAugmentInventory(),type);
    }

    public static int getAugmentIndex(InventoryComponent<?> augmentInventory, ItemStack stack){
        if(stack.getItem() instanceof IFEAddonItem addonItem1){
            return getAugmentIndex(augmentInventory,addonItem1.getType());
        }
        return -1;
    }

    public static int getAugmentIndex(MachineTile<?> tile, ItemStack stack){
        return getAugmentIndex(tile.getAugmentInventory(),stack);
    }

    public static int getAugmentIndex(InventoryComponent<?> augmentInventory, Item item){
        return getAugmentIndex(augmentInventory,item.getDefaultInstance());
    }

    public static int getAugmentIndex(MachineTile<?> tile, Item item){
        return getAugmentIndex(tile.getAugmentInventory(),item);
    }
    public static boolean contains(InventoryComponent<?> augmentInventory, ItemStack stack){
        return getAugmentIndex(augmentInventory,stack) != -1;
    }

    public static boolean contains(MachineTile<?> tile, IAddonType type){
        return getAugmentIndex(tile,type) != -1;
    }

    public static boolean contains(InventoryComponent<?> augmentInventory, IAddonType type){
        return getAugmentIndex(augmentInventory,type) != -1;
    }

    public static boolean contains(MachineTile<?> tile, ItemStack stack){
        return contains(tile.getAugmentInventory(),stack);
    }

    public static boolean canAccept(InventoryComponent<?> augmentInventory, ItemStack stack){
        return !contains(augmentInventory,stack);
    }

    public static boolean canAccept(MachineTile<?> tile, ItemStack stack){
        return canAccept(tile.getAugmentInventory(),stack);
    }

    public static int getAugmentTier(InventoryComponent<?> augmentInventory, IAddonType type){
        int index = getAugmentIndex(augmentInventory,type);
        return index == -1 ? 0 :((IFEAddonItem) augmentInventory.getStackInSlot(index).getItem()).getTier();
    }

    public static int getAugmentTier(MachineTile<?> tile, IAddonType type){
        return getAugmentTier(tile.getAugmentInventory(),type);
    }
}
