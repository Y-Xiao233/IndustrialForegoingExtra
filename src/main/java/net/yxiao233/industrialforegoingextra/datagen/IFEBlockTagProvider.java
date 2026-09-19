package net.yxiao233.industrialforegoingextra.datagen;

import com.buuz135.industrial.module.ModuleCore;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFETags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IFEBlockTagProvider extends BlockTagsProvider {
    private final List<ResourceLocation> generated = new ArrayList<>();
    public IFEBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, IndustrialForegoingExtra.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(IFEBlocks.INFUSER.getBlock())
                .add(IFEBlocks.ARCANE_DRAGON_EGG_FORGING.getBlock())
                .add(IFEBlocks.CREATIVE_CAPACITOR.getBlock())
                .add(IFEBlocks.SAUCEPAN.getBlock())
                .add(IFEBlocks.FERMENTER.getBlock())
                .add(IFEBlocks.ULTIMATE_MACHINE_FRAME.get())
                .add(IFEBlocks.DRAGON_STAR_BLOCK.get())
                .add(ModuleCore.PITY.get())
                .add(ModuleCore.SIMPLE.get())
                .add(ModuleCore.ADVANCED.get())
                .add(ModuleCore.SUPREME.get())
                .add(IFEBlocks.BIG_DISSOLUTION_CHAMBER.getBlock())
                .add(IFEBlocks.BOUNDING.getBlock())
                .add(IFEBlocks.FLUID_CRAFTING_TABLE.getBlock())
                .add(IFEBlocks.DRAGON_GENERATOR.getBlock())
                .add(IFEBlocks.SIMULATED_MOB_CRUSHER.getBlock())
                .add(IFEBlocks.SIMULATED_MOB_DUPLICATOR.getBlock())
                .add(IFEBlocks.SIMULATED_ORE_LASER_BASE.getBlock())
                .add(IFEBlocks.SIMULATED_FLUID_LASER_BASE.getBlock());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(IFEBlocks.INFUSER.getBlock())
                .add(IFEBlocks.ARCANE_DRAGON_EGG_FORGING.getBlock())
                .add(IFEBlocks.CREATIVE_CAPACITOR.getBlock())
                .add(IFEBlocks.SAUCEPAN.getBlock())
                .add(IFEBlocks.FERMENTER.getBlock())
                .add(IFEBlocks.ULTIMATE_MACHINE_FRAME.get())
                .add(IFEBlocks.DRAGON_STAR_BLOCK.get())
                .add(IFEBlocks.BIG_DISSOLUTION_CHAMBER.getBlock())
                .add(IFEBlocks.BOUNDING.getBlock())
                .add(IFEBlocks.FLUID_CRAFTING_TABLE.getBlock())
                .add(IFEBlocks.DRAGON_GENERATOR.getBlock())
                .add(IFEBlocks.SIMULATED_MOB_CRUSHER.getBlock())
                .add(IFEBlocks.SIMULATED_MOB_DUPLICATOR.getBlock())
                .add(IFEBlocks.SIMULATED_ORE_LASER_BASE.getBlock())
                .add(IFEBlocks.SIMULATED_FLUID_LASER_BASE.getBlock());


        this.tag(IFETags.Blocks.MACHINE_FRAME_ULTIMATE)
                .add(IFEBlocks.ULTIMATE_MACHINE_FRAME.get());

    }

    private boolean shouldGenerated(Block block){
        ResourceLocation location = BuiltInRegistries.BLOCK.getKey(block);
        return !generated.contains(location);
    }

    private void generated(Block block){
        generated.add(BuiltInRegistries.BLOCK.getKey(block));
    }
}
