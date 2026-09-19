package net.yxiao233.industrialforegoingextra.util;

import com.buuz135.industrial.utils.CustomRarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class RarityHelper {
    public static Item.Properties epic(Item.Properties properties){
        return properties.rarity(Rarity.EPIC);
    }

    public static Item.Properties supreme(Item.Properties properties){
        return properties.rarity(CustomRarity.SUPREME.getValue());
    }
}
