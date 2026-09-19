package net.yxiao233.industrialforegoingextra.datagen;

import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.utils.IndustrialTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.common.registry.IFETags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IFEItemTagProvider extends ItemTagsProvider {
    private final List<ResourceLocation> generated = new ArrayList<>();
    public IFEItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags) {
        super(pOutput, pLookupProvider, pBlockTags);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(IFETags.Items.GEARS_NETHERITE).add(IFEItems.NETHERITE_GEAR.get());
        tag(IFETags.Items.GEARS_SCULK).add(IFEItems.SCULK_GEAR.get());
        tag(IndustrialTags.Items.GEAR_DIAMOND).add(ModuleCore.DIAMOND_GEAR.get());
        tag(IndustrialTags.Items.GEAR_GOLD).add(ModuleCore.GOLD_GEAR.get());
        tag(IndustrialTags.Items.GEAR_IRON).add(ModuleCore.IRON_GEAR.get());
        tag(IFETags.Items.MACHINE_FRAME_ULTIMATE).add(IFEBlocks.ULTIMATE_MACHINE_FRAME.get().asItem());

        tag(IFETags.Items.GEARS)
                .addTag(IndustrialTags.Items.GEAR_IRON)
                .addTag(IndustrialTags.Items.GEAR_GOLD)
                .addTag(IndustrialTags.Items.GEAR_DIAMOND)
                .addTag(IFETags.Items.GEARS_NETHERITE)
                .addTag(IFETags.Items.GEARS_SCULK);


        tag(IFETags.Items.ROTTEN_CROPS)
                .add(Items.POISONOUS_POTATO);
    }

    private boolean shouldGenerated(Item item){
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
        return !generated.contains(location);
    }

    private void generated(Item item){
        generated.add(BuiltInRegistries.ITEM.getKey(item));
    }
}
