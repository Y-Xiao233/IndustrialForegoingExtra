package net.yxiao233.industrialforegoingextra.compact.productivebees;

import com.hrznstudio.titanium.module.BlockWithTile;
import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import cy.jdkdigital.productivebees.compat.jei.ProductiveBeesJeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.yxiao233.industrialforegoingextra.api.registry.RegistryHelper;
import net.yxiao233.industrialforegoingextra.compact.productivebees.block.BeeConverterBlock;
import net.yxiao233.industrialforegoingextra.compact.productivebees.block.BeeFishingDeviceBlock;
import net.yxiao233.industrialforegoingextra.compact.productivebees.block.BeeSpawnerBlock;
import net.yxiao233.industrialforegoingextra.compact.productivebees.block.BreedingChamberBlock;

public class ModuleBeeRegistries {
    public static BlockWithTile BREEDING_CHAMBER;
    public static BlockWithTile BEE_SPAWNER;
    public static BlockWithTile BEE_FISHING_DEVICE;
    public static BlockWithTile BEE_CONVERTER;
    public static void registryBlock(DeferredRegistryHelper helper){
        BREEDING_CHAMBER = RegistryHelper.blockWithTileItem(helper,"breeding_chamber", BreedingChamberBlock::new);
        BEE_SPAWNER = RegistryHelper.blockWithTileItem(helper,"bee_spawner", BeeSpawnerBlock::new);
        BEE_FISHING_DEVICE = RegistryHelper.blockWithTileItem(helper,"bee_fishing_device", BeeFishingDeviceBlock::new);
        BEE_CONVERTER = RegistryHelper.blockWithTileItem(helper,"bee_converter", BeeConverterBlock::new);
    }

    public static void registryJeiCatalyst(IRecipeCatalystRegistration registration){
        registration.addRecipeCatalyst(BEE_CONVERTER, ProductiveBeesJeiPlugin.BEE_CONVERSION_TYPE);
        registration.addRecipeCatalyst(BEE_SPAWNER, ProductiveBeesJeiPlugin.BEE_SPAWNING_TYPE);
        registration.addRecipeCatalyst(BEE_FISHING_DEVICE, ProductiveBeesJeiPlugin.BEE_FISHING_TYPE);
        registration.addRecipeCatalyst(BREEDING_CHAMBER, ProductiveBeesJeiPlugin.BEE_BREEDING_TYPE);
    }
}
