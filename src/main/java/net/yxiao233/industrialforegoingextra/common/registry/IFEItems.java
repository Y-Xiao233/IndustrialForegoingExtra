package net.yxiao233.industrialforegoingextra.common.registry;

import com.buuz135.industrial.module.IModule;
import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.addon.AddonMap;
import net.yxiao233.industrialforegoingextra.api.addon.IFEAddonType;
import net.yxiao233.industrialforegoingextra.api.data.AdvancedSimulatedCard;
import net.yxiao233.industrialforegoingextra.api.data.SimulatedCard;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryHelper;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryModule;
import net.yxiao233.industrialforegoingextra.common.item.*;
import net.yxiao233.industrialforegoingextra.util.RarityHelper;
import org.apache.commons.lang3.IntegerRange;

@RegistryModule
public class IFEItems implements IModule {
    public static AddonMap SPEED_ADDONS;
    public static AddonMap PROCESSING_ADDONS;
    public static AddonMap EFFICIENCY_ADDONS;
    public static AddonMap APPLE_ADDONS;
    public static AddonMap CREATIVE_ADDONS;
    public static AddonMap ENERGY_ADDONS;
    public static AddonMap HEAL_ADDONS;
    public static AddonMap THREAD_ADDONS;
    public static AddonMap FORTUNE_ADDONS;
    public static AddonMap LOOTING_ADDONS;
    public static DeferredHolder<Item, Item> DRAGON_STAR;
    public static DeferredHolder<Item, Item> LASER_LENS_SCULK;
    public static DeferredHolder<Item, Item> NETHERITE_GEAR;
    public static DeferredHolder<Item, Item> SCULK_GEAR;
    public static DeferredHolder<Item, Item> LASER_LENS_DRAGON;
    public static DeferredHolder<Item, Item> ROUGH_DRAGON_STAR;
    public static DeferredHolder<Item, Item> EMPTY_NETHER_STAR;
    public static DeferredHolder<Item, Item> APPLE_CORE;
    public static DeferredHolder<Item, Item> AIR;
    public static DeferredHolder<Item, Item> SIMULATED_CARD;
    public static DeferredHolder<Item, Item> ADVANCED_SIMULATED_CARD;
    public static DeferredHolder<Item, Item> ADVANCED_MOB_IMPRISONMENT;

    @Override
    public void generateFeatures(DeferredRegistryHelper helper) {
        SPEED_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.SPEED, IntegerRange.of(3,6), IFESpeedAddonItem::new);
        PROCESSING_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.PROCESSING, IntegerRange.of(3,6), IFEProcessingAddonItem::new);
        EFFICIENCY_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.EFFICIENCY, IntegerRange.of(3,6), IFEEfficiencyAddonItem::new);
        APPLE_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.APPLE,6, IFEAppleAddonItem::new);
        CREATIVE_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.CREATIVE, -1, IFECreativeAddonItem::new);
        ENERGY_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.ENERGY, 6, IFEEnergyAddonItem::new);
        HEAL_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.HEAL, 6, IFEHealAddonItem::new);
        THREAD_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.THREAD, 6, IFEThreadAddonItem::new);
        FORTUNE_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.FORTUNE, 2, IFEFortuneAddonItem::new);
        LOOTING_ADDONS = RegistryHelper.registryAddons(helper, IFEAddonType.LOOTING, 2, IFELootingAddonItem::new);

        DRAGON_STAR = RegistryHelper.simpleItem(helper,"dragon_star", RarityHelper::supreme);
        LASER_LENS_SCULK = RegistryHelper.simpleItem(helper,"laser_lens_sculk");
        NETHERITE_GEAR = RegistryHelper.simpleItem(helper,"netherite_gear");
        SCULK_GEAR = RegistryHelper.simpleItem(helper,"sculk_gear");
        LASER_LENS_DRAGON = RegistryHelper.simpleItem(helper,"laser_lens_dragon");
        ROUGH_DRAGON_STAR = RegistryHelper.simpleItem(helper,"rough_dragon_star", RarityHelper::epic);
        EMPTY_NETHER_STAR = RegistryHelper.simpleItem(helper,"empty_nether_star");
        APPLE_CORE = RegistryHelper.simpleItem(helper,"apple_core",properties -> properties.food(IFEFoodProperties.APPLE_CORE));
        AIR = RegistryHelper.simpleItem(helper,"air",false);
        SIMULATED_CARD = RegistryHelper.item(helper,"simulated_card", SimulatedCard::new);
        ADVANCED_SIMULATED_CARD = RegistryHelper.item(helper,"advanced_simulated_card", AdvancedSimulatedCard::new);
        ADVANCED_MOB_IMPRISONMENT = RegistryHelper.item(helper,"advanced_mob_imprisonment_tool", AdvancedMobImprisonmentToolItem::new,false);
    }
}
