package net.yxiao233.industrialforegoingextra.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFEFluids;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import org.jetbrains.annotations.NotNull;

public class IFEItemModelProvider extends ItemModelProvider {
    public IFEItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IndustrialForegoingExtra.MODID, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return IndustrialForegoingExtra.MODID + " - ItemModel";
    }

    @Override
    protected void registerModels() {
        basicItem(IFEItems.NETHERITE_GEAR.get());
        basicItem(IFEItems.SCULK_GEAR.get());
        basicItem(IFEFluids.LIQUID_SCULK_MATTER.getBucketFluid().get());
        basicItem(IFEFluids.LIQUID_DRAGON_BREATH.getBucketFluid().get());
        basicItem(IFEFluids.LIQUID_MALIC_ACID.getBucketFluid());
        basicItem(IFEFluids.DRAGON_STAR_ESSENCE.getBucketFluid());
        basicItem(IFEItems.DRAGON_STAR.get());
        basicItem(IFEItems.LASER_LENS_SCULK.get());
        basicItem(IFEItems.LASER_LENS_DRAGON.get());
        basicItem(IFEItems.ROUGH_DRAGON_STAR.get());
        basicItem(IFEItems.EMPTY_NETHER_STAR.get());
        basicItem(IFEItems.APPLE_CORE.get());
        basicItem(IFEItems.SIMULATED_CARD.get());
        basicItem(IFEItems.ADVANCED_SIMULATED_CARD.get());
        IFEItems.HEAL_ADDONS.forEach(this::basicItem);
        IFEItems.SPEED_ADDONS.forEach(this::basicItem);
        IFEItems.PROCESSING_ADDONS.forEach(this::basicItem);
        IFEItems.EFFICIENCY_ADDONS.forEach(this::basicItem);
        IFEItems.APPLE_ADDONS.forEach(this::basicItem);
        IFEItems.CREATIVE_ADDONS.forEach(this::basicItem);
        IFEItems.ENERGY_ADDONS.forEach(this::basicItem);
        IFEItems.HEAL_ADDONS.forEach(this::basicItem);
        IFEItems.THREAD_ADDONS.forEach(this::basicItem);
        IFEItems.FORTUNE_ADDONS.forEach(this::basicItem);
        IFEItems.LOOTING_ADDONS.forEach(this::basicItem);
    }
}
