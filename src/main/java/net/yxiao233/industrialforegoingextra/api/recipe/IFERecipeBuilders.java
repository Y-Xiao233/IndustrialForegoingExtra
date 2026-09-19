package net.yxiao233.industrialforegoingextra.api.recipe;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class IFERecipeBuilders {
    public static DissolutionChamberRecipeBuilder dissolutionChamberRecipe(ItemStack output, String id){
        return new DissolutionChamberRecipeBuilder(output,id);
    }

    public static DissolutionChamberRecipeBuilder dissolutionChamberRecipe(ItemStack output){
        return new DissolutionChamberRecipeBuilder(output);
    }

    public static InfuserRecipeBuilder infuserRecipe(ItemStack output, String id){
        return new InfuserRecipeBuilder(output,id);
    }

    public static InfuserRecipeBuilder infuserRecipe(ItemStack output){
        return new InfuserRecipeBuilder(output);
    }

    public static LaserDrillFluidRecipeBuilder laserDrillFluidRecipe(SizedFluidIngredient output, String id){
        return new LaserDrillFluidRecipeBuilder(output,id);
    }

    public static FluidExtractorRecipeBuilder fluidExtractorRecipe(FluidStack output, String id){
        return new FluidExtractorRecipeBuilder(output,id);
    }

    public static ArcaneDragonEggForgingRecipeBuilder arcaneDragonEggForgingRecipe(ItemStack output){
        return new ArcaneDragonEggForgingRecipeBuilder(output);
    }

    public static ArcaneDragonEggForgingRecipeBuilder arcaneDragonEggForgingRecipe(ItemStack output, String id){
        return new ArcaneDragonEggForgingRecipeBuilder(output,id);
    }

    public static ShapedRecipeBuilder shapedRecipe(ItemStack output, String id){
        return new ShapedRecipeBuilder(output,id);
    }

    public static ShapedRecipeBuilder shapedRecipe(ItemStack output){
        return new ShapedRecipeBuilder(output);
    }

    public static ShapelessRecipeBuilder shapelessRecipe(ItemStack output){
        return new ShapelessRecipeBuilder(output);
    }

    public static ShapelessRecipeBuilder shapelessRecipe(ItemStack output, String id){
        return new ShapelessRecipeBuilder(output,id);
    }
}
