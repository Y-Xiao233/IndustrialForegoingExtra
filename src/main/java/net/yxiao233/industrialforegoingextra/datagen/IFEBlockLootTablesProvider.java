package net.yxiao233.industrialforegoingextra.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IFEBlockLootTablesProvider {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> provider){
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK)
        ),provider);
    }

    public static class BlockLootTables extends BlockLootSubProvider {
        public BlockLootTables(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(),provider);
        }

        @Override
        protected void generate() {
            this.dropSelf(IFEBlocks.DEAD_DRAGON_EGG.get());
            this.dropSelf(IFEBlocks.ULTIMATE_MACHINE_FRAME.get());
            this.dropSelf(IFEBlocks.DRAGON_STAR_BLOCK.get());
            this.dropSelf(IFEBlocks.ARCANE_DRAGON_EGG_FORGING.getBlock());
            this.dropSelf(IFEBlocks.FERMENTER.getBlock());
            this.dropSelf(IFEBlocks.INFUSER.getBlock());
            this.dropSelf(IFEBlocks.CREATIVE_CAPACITOR.getBlock());
            this.dropSelf(IFEBlocks.SAUCEPAN.getBlock());
            this.dropSelf(IFEBlocks.BIG_DISSOLUTION_CHAMBER.getBlock());
            this.dropSelf(IFEBlocks.DRAGON_GENERATOR.getBlock());
            this.dropSelf(IFEBlocks.FLUID_CRAFTING_TABLE.getBlock());
            this.dropSelf(IFEBlocks.SIMULATED_MOB_CRUSHER.getBlock());
            this.dropSelf(IFEBlocks.SIMULATED_MOB_DUPLICATOR.getBlock());
            this.dropSelf(IFEBlocks.SIMULATED_ORE_LASER_BASE.getBlock());
            this.dropSelf(IFEBlocks.SIMULATED_FLUID_LASER_BASE.getBlock());
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            ArrayList<Block> list = new ArrayList<>();
            BuiltInRegistries.BLOCK.forEach(block ->{
                if (!block.getLootTable().equals(BuiltInLootTables.EMPTY) && BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(IndustrialForegoingExtra.MODID)){
                    list.add(block);
                }
            });

            return list;
        }
    }
}
