package net.yxiao233.industrialforegoingextra.datagen;

import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block.RotationHandler;
import com.hrznstudio.titanium.module.BlockWithTile;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;

import java.util.ArrayList;
import java.util.List;

public class IFEBlockStateProvider extends BlockStateProvider {
    private final List<ResourceLocation> generated = new ArrayList<>();
    public IFEBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialForegoingExtra.MODID,exFileHelper);
    }
    public static ResourceLocation getModel(Block block) {
        return ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(block).getNamespace(), "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath());
    }

    @Override
    protected void registerStatesAndModels() {
        blockItem(IFEBlocks.DEAD_DRAGON_EGG);
        blockItem(IFEBlocks.ULTIMATE_MACHINE_FRAME);
        blockItem(IFEBlocks.DRAGON_STAR_BLOCK);
        blockWithTileItem(IFEBlocks.INFUSER);
        blockWithTileItem(IFEBlocks.CREATIVE_CAPACITOR);
        blockWithTileItem(IFEBlocks.ARCANE_DRAGON_EGG_FORGING);
        blockWithTileItem(IFEBlocks.SAUCEPAN);
        blockWithTileItem(IFEBlocks.FERMENTER);
        blockWithTileItem(IFEBlocks.DRAGON_GENERATOR);
        fourWayBlockState(IFEBlocks.BIG_DISSOLUTION_CHAMBER.getBlock());
        blockWithTileItem(IFEBlocks.FLUID_CRAFTING_TABLE);
        blockWithTileItem(IFEBlocks.SIMULATED_MOB_CRUSHER);
        blockWithTileItem(IFEBlocks.SIMULATED_MOB_DUPLICATOR);
        blockWithTileItem(IFEBlocks.SIMULATED_ORE_LASER_BASE);
        blockWithTileItem(IFEBlocks.SIMULATED_FLUID_LASER_BASE);
    }

    private boolean shouldGenerated(Block block){
        ResourceLocation location = BuiltInRegistries.BLOCK.getKey(block);
        return !generated.contains(location);
    }

    private void generated(Block block){
        generated.add(BuiltInRegistries.BLOCK.getKey(block));
    }

    private void blockItem(DeferredHolder<Block,Block> registryObject){
        simpleBlockItem(registryObject.get(),new ModelFile.UncheckedModelFile(IndustrialForegoingExtra.MODID +
                ":block/" + BuiltInRegistries.BLOCK.getKey(registryObject.get()).getPath()));
    }

    private void blockWithTileItem(BlockWithTile blockWithTile){
        simpleBlockItem(blockWithTile.getBlock(),new ModelFile.UncheckedModelFile(IndustrialForegoingExtra.MODID +
                ":block/" + BuiltInRegistries.BLOCK.getKey(blockWithTile.getBlock()).getPath()));
    }

    private void blockWithItem(DeferredHolder<Block, Block> block){
        simpleBlockWithItem(block.get(),new ModelFile.UncheckedModelFile(IndustrialForegoingExtra.MODID +
                ":block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath()));
    }

    private <T extends Block> void fourWayBlockState(T block){
        fourWayBlockState(block,BuiltInRegistries.BLOCK.getKey(block).getPath());
    }
    private <T extends Block> void fourWayBlockState(T block, String modelPath){
        fourWayBlockState(block,ResourceLocation.fromNamespaceAndPath(IndustrialForegoingExtra.MODID, "block/" + modelPath));
    }

    private <T extends Block> void fourWayBlockState(T block, ResourceLocation modelPath){
        Property<Direction> property = RotatableBlock.FACING_HORIZONTAL;
        getVariantBuilder(block)
                .partialState().with(property, Direction.NORTH)
                .modelForState()
                .modelFile(models().getExistingFile(modelPath))
                .rotationY(0)
                .addModel()

                .partialState().with(property, Direction.EAST)
                .modelForState()
                .modelFile(models().getExistingFile(modelPath))
                .rotationY(90)
                .addModel()

                .partialState().with(property, Direction.SOUTH)
                .modelForState()
                .modelFile(models().getExistingFile(modelPath))
                .rotationY(180)
                .addModel()

                .partialState().with(property, Direction.WEST)
                .modelForState()
                .modelFile(models().getExistingFile(modelPath))
                .rotationY(270)
                .addModel();
    }

}
