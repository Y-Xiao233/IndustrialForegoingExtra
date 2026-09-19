package net.yxiao233.industrialforegoingextra.api.recipe;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.industrialforegoingextra.common.recipes.ShapelessRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;

public class ShapelessRecipeBuilder extends IFERecipeBuilder{
    public ShapelessRecipeBuilder(ItemStack output) {
        super(output);
    }

    public ShapelessRecipeBuilder(ItemStack output, String id) {
        super(output, id);
    }

    @Override
    public void save(RecipeOutput output) {
        ShapelessRecipe.createRecipe(output,this.getLocation(IFERecipes.SHAPELESS),new ShapelessRecipe(this.getInputs(),this.getInputFluid(),this.getOutput()));
    }
}