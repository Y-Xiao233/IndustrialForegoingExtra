package net.yxiao233.industrialforegoingextra.datagen;

import com.buuz135.industrial.module.*;
import com.buuz135.industrial.recipe.LaserDrillRarity;
import com.buuz135.industrial.recipe.data.EntityData;
import com.buuz135.industrial.utils.IndustrialTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.yxiao233.industrialforegoingextra.api.recipe.AddonRecipeGenerateHelper;
import net.yxiao233.industrialforegoingextra.api.recipe.IFERecipeBuilders;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFEFluids;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.common.registry.IFETags;

import java.util.ArrayList;
import java.util.Optional;

public class IFESerializableProvider{
    public static void init(RecipeOutput recipeOutput) {
        //dissolution chamber
        IFERecipeBuilders.dissolutionChamberRecipe(new ItemStack(Items.GLASS_BOTTLE,8),"liquid_dragon_breath")
                .inputs(
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance())
                )
                .inputFluid(new FluidStack(IFEFluids.LIQUID_SCULK_MATTER.getSourceFluid().get(),500))
                .processingTime(200)
                .outputFluid(new FluidStack(IFEFluids.LIQUID_DRAGON_BREATH.getSourceFluid().get(),100))
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(IFEItems.LASER_LENS_SCULK.get().getDefaultInstance())
                .inputs(
                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                        itemValue(Items.SCULK.getDefaultInstance()),
                        itemValue(Items.SCULK.getDefaultInstance()),
                        itemValue(Items.SCULK.getDefaultInstance()),
                        itemValue(Items.SCULK.getDefaultInstance())
                )
                .inputFluid(new FluidStack(ModuleCore.ETHER.getSourceFluid().get(), 1000))
                .processingTime(200)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(IFEItems.LASER_LENS_DRAGON.get().getDefaultInstance())
                .inputs(
                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                        tagValue(Tags.Items.GLASS_PANES_COLORLESS),
                        itemValue(IFEItems.DRAGON_STAR.get().getDefaultInstance()),
                        itemValue(IFEItems.DRAGON_STAR.get().getDefaultInstance()),
                        itemValue(IFEItems.DRAGON_STAR.get().getDefaultInstance()),
                        itemValue(IFEItems.DRAGON_STAR.get().getDefaultInstance())
                )
                .inputFluid(new FluidStack(ModuleCore.ETHER.getSourceFluid().get(), 1000))
                .processingTime(200)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(IFEBlocks.ULTIMATE_MACHINE_FRAME.get().asItem().getDefaultInstance())
                .inputs(
                        tagValue(IndustrialTags.Items.PLASTIC),
                        tagValue(IndustrialTags.Items.MACHINE_FRAME_SUPREME),
                        tagValue(IndustrialTags.Items.PLASTIC),
                        itemValue(IFEBlocks.DRAGON_STAR_BLOCK.get().asItem().getDefaultInstance()),
                        itemValue(IFEBlocks.DRAGON_STAR_BLOCK.get().asItem().getDefaultInstance()),
                        itemValue(Items.DRAGON_EGG.getDefaultInstance()),
                        tagValue(IFETags.Items.GEARS_SCULK),
                        itemValue(Items.DRAGON_EGG.getDefaultInstance())
                )
                .inputFluid(new FluidStack(IFEFluids.LIQUID_SCULK_MATTER.getSourceFluid().get(),8000))
                .processingTime(200)
                .outputFluid(new FluidStack(Fluids.WATER,8000))
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(new ItemStack(IFEItems.EMPTY_NETHER_STAR.get(),4))
                .inputs(
                        itemValue(Items.NETHERITE_INGOT.getDefaultInstance()),
                        tagValue(IndustrialTags.Items.PLASTIC),
                        itemValue(Items.NETHERITE_INGOT.getDefaultInstance()),
                        tagValue(IndustrialTags.Items.PLASTIC),
                        tagValue(IndustrialTags.Items.PLASTIC),
                        itemValue(Items.NETHERITE_INGOT.getDefaultInstance()),
                        tagValue(IndustrialTags.Items.PLASTIC),
                        itemValue(Items.NETHERITE_INGOT.getDefaultInstance())
                )
                .inputFluid(new FluidStack(IFEFluids.LIQUID_MALIC_ACID.getSourceFluid().get(), 4000))
                .processingTime(200)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(new ItemStack(IFEItems.ROUGH_DRAGON_STAR.get(),2))
                .inputs(
                        itemValue(IFEItems.EMPTY_NETHER_STAR.get().getDefaultInstance()),
                        itemValue(Items.WITHER_SKELETON_SKULL.getDefaultInstance()),
                        itemValue(IFEItems.EMPTY_NETHER_STAR.get().getDefaultInstance()),
                        tagValue(Tags.Items.RODS_BLAZE),
                        tagValue(Tags.Items.RODS_BLAZE),
                        tagValue(Tags.Items.STORAGE_BLOCKS_DIAMOND),
                        tagValue(Tags.Items.RODS_BLAZE),
                        tagValue(Tags.Items.STORAGE_BLOCKS_DIAMOND)
                )
                .inputFluid(new FluidStack(IFEFluids.DRAGON_STAR_ESSENCE.getSourceFluid().get(), 200))
                .processingTime(200)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(new ItemStack(IFEItems.APPLE_CORE.get(),8),"liquid_malic_acid")
                .inputs(
                        itemValue(Items.APPLE.getDefaultInstance()),
                        itemValue(Items.APPLE.getDefaultInstance()),
                        itemValue(Items.APPLE.getDefaultInstance()),
                        itemValue(Items.APPLE.getDefaultInstance()),
                        itemValue(Items.APPLE.getDefaultInstance()),
                        itemValue(Items.APPLE.getDefaultInstance()),
                        itemValue(Items.APPLE.getDefaultInstance()),
                        itemValue(Items.APPLE.getDefaultInstance())
                )
                .inputFluid(new FluidStack(ModuleCore.ETHER.getSourceFluid().get(), 100))
                .processingTime(200)
                .outputFluid(new FluidStack(IFEFluids.LIQUID_MALIC_ACID.getSourceFluid().get(), 10))
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(Items.NETHER_STAR.getDefaultInstance())
                .inputs(
                        itemValue(IFEItems.ROUGH_DRAGON_STAR.get().getDefaultInstance())
                )
                .inputFluid(new FluidStack(IFEFluids.LIQUID_MALIC_ACID.getSourceFluid().get(), 1000))
                .processingTime(200)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(IFEBlocks.SIMULATED_MOB_DUPLICATOR.asItem().getDefaultInstance())
                .inputs(
                        itemValue(ModuleAgricultureHusbandry.MOB_DUPLICATOR.asItem().getDefaultInstance()),
                        itemValue(Items.CONDUIT.getDefaultInstance()),
                        tagValue(IFETags.Items.MACHINE_FRAME_ULTIMATE),
                        itemValue(Items.SCULK.getDefaultInstance()),
                        itemValue(Items.HEAVY_CORE.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        tagValue(Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        itemValue(Items.NETHER_STAR.getDefaultInstance())
                )
                .inputFluid(IFEFluids.DRAGON_STAR_ESSENCE.getSourceFluid(),100)
                .processingTime(400)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(IFEBlocks.SIMULATED_ORE_LASER_BASE.asItem().getDefaultInstance())
                .inputs(
                        itemValue(ModuleResourceProduction.ORE_LASER_BASE.asItem().getDefaultInstance()),
                        itemValue(Items.CONDUIT.getDefaultInstance()),
                        tagValue(IFETags.Items.MACHINE_FRAME_ULTIMATE),
                        itemValue(Items.SCULK.getDefaultInstance()),
                        itemValue(Items.HEAVY_CORE.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        tagValue(Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        itemValue(Items.NETHER_STAR.getDefaultInstance())
                )
                .inputFluid(IFEFluids.DRAGON_STAR_ESSENCE.getSourceFluid(),100)
                .processingTime(400)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(IFEBlocks.SIMULATED_FLUID_LASER_BASE.asItem().getDefaultInstance())
                .inputs(
                        itemValue(ModuleResourceProduction.FLUID_LASER_BASE.asItem().getDefaultInstance()),
                        itemValue(Items.CONDUIT.getDefaultInstance()),
                        tagValue(IFETags.Items.MACHINE_FRAME_ULTIMATE),
                        itemValue(Items.SCULK.getDefaultInstance()),
                        itemValue(Items.HEAVY_CORE.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        tagValue(Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        itemValue(Items.NETHER_STAR.getDefaultInstance())
                )
                .inputFluid(IFEFluids.DRAGON_STAR_ESSENCE.getSourceFluid(),100)
                .processingTime(400)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(IFEItems.ADVANCED_MOB_IMPRISONMENT.get().getDefaultInstance())
                .inputs(
                        itemValue(ModuleTool.MOB_IMPRISONMENT_TOOL.get().getDefaultInstance()),
                        itemValue(Items.CONDUIT.getDefaultInstance()),
                        tagValue(IFETags.Items.MACHINE_FRAME_ULTIMATE),
                        itemValue(Items.SCULK.getDefaultInstance()),
                        itemValue(Items.HEAVY_CORE.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        tagValue(Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        itemValue(Items.NETHER_STAR.getDefaultInstance())
                )
                .inputFluid(IFEFluids.DRAGON_STAR_ESSENCE.getSourceFluid(),100)
                .processingTime(400)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);

        IFERecipeBuilders.dissolutionChamberRecipe(IFEItems.ADVANCED_SIMULATED_CARD.get().getDefaultInstance())
                .inputs(
                        itemValue(IFEItems.SIMULATED_CARD.get().getDefaultInstance()),
                        itemValue(Items.CONDUIT.getDefaultInstance()),
                        tagValue(IFETags.Items.MACHINE_FRAME_ULTIMATE),
                        itemValue(Items.SCULK.getDefaultInstance()),
                        itemValue(Items.HEAVY_CORE.getDefaultInstance()),
                        itemValue(Items.DRAGON_BREATH.getDefaultInstance()),
                        tagValue(Tags.Items.STORAGE_BLOCKS_NETHERITE),
                        itemValue(Items.NETHER_STAR.getDefaultInstance())
                )
                .inputFluid(IFEFluids.DRAGON_STAR_ESSENCE.getSourceFluid(),100)
                .processingTime(400)
                .outputFluid(FluidStack.EMPTY)
                .save(recipeOutput);


        //infuser
        IFERecipeBuilders.infuserRecipe(IFEItems.DRAGON_STAR.get().getDefaultInstance())
                .input(Items.NETHER_STAR.getDefaultInstance())
                .inputFluid(new FluidStack(IFEFluids.LIQUID_DRAGON_BREATH.getSourceFluid().get(), 1000))
                .processingTime(200)
                .save(recipeOutput);

        IFERecipeBuilders.infuserRecipe(Items.MUD.getDefaultInstance(),"dirt_mud")
                .input(Items.DIRT.getDefaultInstance())
                .inputFluid(new FluidStack(Fluids.WATER.getSource(),1000))
                .processingTime(200)
                .save(recipeOutput);

        IFERecipeBuilders.infuserRecipe(Items.CLAY.getDefaultInstance(),"mud_clay")
                .input(Items.MUD.getDefaultInstance())
                .inputFluid(new FluidStack(Fluids.WATER.getSource(),2000))
                .processingTime(500)
                .save(recipeOutput);


        IFERecipeBuilders.infuserRecipe(Items.BLACKSTONE.getDefaultInstance(),"cobblestone_blackstone")
                .input(Items.COBBLESTONE.getDefaultInstance())
                .inputFluid(new FluidStack(Fluids.LAVA.getSource(),200))
                .processingTime(200)
                .save(recipeOutput);

        IFERecipeBuilders.infuserRecipe(Items.ECHO_SHARD.getDefaultInstance(),"sculk")
                .input(Items.AMETHYST_SHARD.getDefaultInstance())
                .inputFluid(new FluidStack(IFEFluids.LIQUID_SCULK_MATTER.getSourceFluid().get(),200))
                .processingTime(200)
                .save(recipeOutput);

        IFERecipeBuilders.infuserRecipe(Items.CRYING_OBSIDIAN.getDefaultInstance())
                .input(Items.OBSIDIAN.getDefaultInstance())
                .inputFluid(new FluidStack(IFEFluids.LIQUID_SCULK_MATTER.getSourceFluid().get(),2000))
                .processingTime(200)
                .save(recipeOutput);

        IFERecipeBuilders.infuserRecipe(Items.DRAGON_BREATH.getDefaultInstance())
                .input(Items.GLASS_BOTTLE.getDefaultInstance())
                .inputFluid(new FluidStack(IFEFluids.LIQUID_DRAGON_BREATH.getSourceFluid().get(),10))
                .processingTime(200)
                .save(recipeOutput);



        //laser drill fluid
        IFERecipeBuilders.laserDrillFluidRecipe(new SizedFluidIngredient(FluidIngredient.of(IFEFluids.LIQUID_SCULK_MATTER.getSourceFluid().get()),10),"liquid_sculk_matter")
                .catalyst(Ingredient.of(IFEItems.LASER_LENS_SCULK.get()))
                .entity(Optional.of(EntityData.of(EntityType.WARDEN)))
                .rarity(new LaserDrillRarity(new LaserDrillRarity.BiomeRarity(new ArrayList<>(),new ArrayList<>()),new LaserDrillRarity.DimensionRarity(new ArrayList<>(),new ArrayList<>()),-64,256,8))
                .save(recipeOutput);

        IFERecipeBuilders.laserDrillFluidRecipe(new SizedFluidIngredient(FluidIngredient.of(IFEFluids.LIQUID_DRAGON_BREATH.getSourceFluid().get()),10),"liquid_dragon_breath")
                .catalyst(Ingredient.of(IFEItems.LASER_LENS_DRAGON.get()))
                .entity(Optional.of(EntityData.of(EntityType.ENDER_DRAGON)))
                .rarity(new LaserDrillRarity(new LaserDrillRarity.BiomeRarity(LaserDrillRarity.BiomeRarity.END,new ArrayList<>()),new LaserDrillRarity.DimensionRarity(new ArrayList<>(),new ArrayList<>()),-64,256,8))
                .save(recipeOutput);

        //fluid extractor
        IFERecipeBuilders.fluidExtractorRecipe(new FluidStack(IFEFluids.DRAGON_STAR_ESSENCE.getSourceFluid().get(),2),"dragon_star_essence")
                .inputBlock(itemValue(new ItemStack(IFEBlocks.DRAGON_STAR_BLOCK.get())))
                .resultBlockState(Blocks.AIR.defaultBlockState())
                .breakChance(0.01f)
                .isDefault(false)
                .save(recipeOutput);


        //arcane dragon egg forging
        IFERecipeBuilders.arcaneDragonEggForgingRecipe(IFEBlocks.DEAD_DRAGON_EGG.get().asItem().getDefaultInstance(),"dead_dragon_egg")
                .input(new ItemStack(Items.EGG,4))
                .inputFluids(new FluidStack(ModuleCore.ESSENCE.getSourceFluid().get(),250),new FluidStack(IFEFluids.LIQUID_DRAGON_BREATH.getSourceFluid().get(),4000))
                .outputFluid(FluidStack.EMPTY)
                .processingTime(200)
                .save(recipeOutput);

        IFERecipeBuilders.arcaneDragonEggForgingRecipe(new ItemStack(Items.EGG,16),"egg_1")
                .input(Items.DRAGON_EGG.getDefaultInstance())
                .inputFluids(new FluidStack(ModuleCore.PINK_SLIME.getSourceFluid().get(),100),new FluidStack(Fluids.WATER,1000))
                .outputFluid(new FluidStack(ModuleCore.ESSENCE.getSourceFluid().get(), 100))
                .processingTime(200)
                .save(recipeOutput);

        IFERecipeBuilders.arcaneDragonEggForgingRecipe(new ItemStack(Items.EGG,16),"egg_2")
                .input(IFEBlocks.DEAD_DRAGON_EGG.get().asItem().getDefaultInstance())
                .inputFluids(new FluidStack(ModuleCore.PINK_SLIME.getSourceFluid().get(),100),new FluidStack(Fluids.WATER,1000))
                .outputFluid(FluidStack.EMPTY)
                .processingTime(200)
                .save(recipeOutput);

        //fluid crafting table
        //如果配方内某个格子为空,请使用IFEItems.AIR.get().getDefaultInstance(),而不是ItemStack.EMPTY
        IFERecipeBuilders.shapedRecipe(IFEBlocks.DRAGON_STAR_BLOCK.get().asItem().getDefaultInstance())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', IFEItems.DRAGON_STAR.get().getDefaultInstance())
                .inputFluid(new FluidStack(IFEFluids.LIQUID_SCULK_MATTER.getSourceFluid().get(),8000))
                .save(recipeOutput);

        IFERecipeBuilders.shapedRecipe(IFEBlocks.BIG_DISSOLUTION_CHAMBER.asItem().getDefaultInstance())
                .pattern("ABA")
                .pattern("CDC")
                .pattern("AEA")
                .define('A', IndustrialTags.Items.PLASTIC)
                .define('B', Tags.Items.BUCKETS)
                .define('C', ModuleCore.DISSOLUTION_CHAMBER.asItem().getDefaultInstance())
                .define('D', IFETags.Items.MACHINE_FRAME_ULTIMATE)
                .define('E', Tags.Items.CHESTS)
                .inputFluid(new FluidStack(IFEFluids.LIQUID_SCULK_MATTER.getSourceFluid().get(),1000))
                .save(recipeOutput);

        IFERecipeBuilders.shapedRecipe(Items.ENCHANTED_GOLDEN_APPLE.getDefaultInstance())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', IFEItems.DRAGON_STAR.get().getDefaultInstance())
                .define('B', Tags.Items.STORAGE_BLOCKS_GOLD)
                .define('C', Items.APPLE.getDefaultInstance())
                .inputFluid(new FluidStack(IFEFluids.LIQUID_SCULK_MATTER.getSourceFluid().get(),100))
                .save(recipeOutput);

        IFERecipeBuilders.shapedRecipe(IFEItems.NETHERITE_GEAR.get().getDefaultInstance())
                .pattern("ASA")
                .pattern("SAS")
                .pattern("ASA")
                .define('A', IFEItems.AIR.get().getDefaultInstance())
                .define('S', Items.NETHERITE_INGOT.getDefaultInstance())
                .inputFluid(new FluidStack(ModuleCore.MEAT.getSourceFluid(),100))
                .save(recipeOutput);

        IFERecipeBuilders.shapedRecipe(IFEItems.SCULK_GEAR.get().getDefaultInstance())
                .pattern("ASA")
                .pattern("SAS")
                .pattern("ASA")
                .define('A', IFEItems.AIR.get().getDefaultInstance())
                .define('S', Items.NETHERITE_INGOT.getDefaultInstance())
                .inputFluid(new FluidStack(ModuleCore.SLUDGE.getSourceFluid(),100))
                .save(recipeOutput);

        //shapeless
        //有多少输入写多少
        IFERecipeBuilders.shapelessRecipe(IFEItems.DRAGON_STAR.get().getDefaultInstance().copyWithCount(9),"dragon_star_from_block")
                .inputs(itemValue(IFEBlocks.DRAGON_STAR_BLOCK.get().asItem().getDefaultInstance()))
                .inputFluid(new FluidStack(IFEFluids.LIQUID_DRAGON_BREATH.getSourceFluid().get(),100))
                .save(recipeOutput);


        //addon item
        AddonRecipeGenerateHelper.INSTANCE
                .defineTierMaterial(1,tagValue(IndustrialTags.Items.GEAR_GOLD),tagValue(IndustrialTags.Items.GEAR_GOLD),new FluidStack(ModuleCore.LATEX.getSourceFluid(),1000))
                .defineTierMaterial(2,tagValue(IndustrialTags.Items.GEAR_DIAMOND),tagValue(IndustrialTags.Items.GEAR_DIAMOND),new FluidStack(ModuleCore.LATEX.getSourceFluid(),1000))
                .defineTierMaterial(3,tagValue(IFETags.Items.GEARS_NETHERITE),tagValue(IFETags.Items.GEARS_NETHERITE),new FluidStack(ModuleCore.PINK_SLIME.getSourceFluid().get(),1000))
                .defineTierMaterial(4,tagValue(IFETags.Items.GEARS_SCULK),tagValue(IFETags.Items.GEARS_SCULK),new FluidStack(ModuleCore.ETHER.getSourceFluid().get(), 1000))
                .defineTierMaterial(5,itemValue(Items.NETHER_STAR.getDefaultInstance()),itemValue(Items.NETHER_STAR.getDefaultInstance()),new FluidStack(IFEFluids.LIQUID_SCULK_MATTER.getSourceFluid().get(),1000))
                .defineTierMaterial(6,itemValue(IFEItems.DRAGON_STAR.get().getDefaultInstance()),itemValue(IFEItems.DRAGON_STAR.get().getDefaultInstance()),new FluidStack(IFEFluids.LIQUID_DRAGON_BREATH.getSourceFluid().get(),1000))
                .defineTyped(IFEItems.SPEED_ADDONS,itemValue(Items.SUGAR.getDefaultInstance()),itemValue(Items.SUGAR.getDefaultInstance()))
                .defineTyped(IFEItems.EFFICIENCY_ADDONS,itemValue(Items.BLAZE_ROD.getDefaultInstance()),itemValue(Items.BLAZE_ROD.getDefaultInstance()))
                .defineTyped(IFEItems.PROCESSING_ADDONS,itemValue(Items.FURNACE.getDefaultInstance()),itemValue(Items.CRAFTING_TABLE.getDefaultInstance()))
                .defineTyped(IFEItems.THREAD_ADDONS,itemValue(Items.ECHO_SHARD.getDefaultInstance()),tagValue(Tags.Items.INGOTS_NETHERITE))
                .defineTyped(IFEItems.APPLE_ADDONS,itemValue(IFEItems.APPLE_CORE.get().getDefaultInstance()),itemValue(IFEItems.APPLE_CORE.get().getDefaultInstance()))
                .defineTyped(IFEItems.HEAL_ADDONS,itemValue(Items.GOLDEN_APPLE.getDefaultInstance()),itemValue(Items.GOLDEN_CARROT.getDefaultInstance()))
                .defineTyped(IFEItems.ENERGY_ADDONS,itemValue(ModuleGenerator.MYCELIAL_GENERATORS.get(9).asItem().getDefaultInstance()),itemValue(ModuleMisc.INFINITY_CHARGER.asItem().getDefaultInstance()))
                .defineTyped(IFEItems.LOOTING_ADDONS,tagValue(Tags.Items.STORAGE_BLOCKS_EMERALD),itemValue(IFEItems.DRAGON_STAR.get().getDefaultInstance()))
                .defineTyped(IFEItems.FORTUNE_ADDONS,itemValue(Items.QUARTZ_BLOCK.getDefaultInstance()),itemValue(IFEItems.DRAGON_STAR.get().getDefaultInstance()))
                .save(recipeOutput);
    }

    public static Ingredient tagValue(TagKey<Item> tagKey){
        return Ingredient.of(tagKey);
    }
    public static Ingredient itemValue(ItemStack itemStack){
        return Ingredient.of(itemStack);
    }
    public static ResourceLocation MC(String path){
        return ResourceLocation.fromNamespaceAndPath("minecraft",path);
    }
}