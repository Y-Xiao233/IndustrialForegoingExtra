package net.yxiao233.industrialforegoingextra.api.recipe;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.industrialforegoingextra.common.recipes.InfuserRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;

public class InfuserRecipeBuilder extends IFERecipeBuilder {
    public InfuserRecipeBuilder(ItemStack output) {
        super(output);
    }

    public InfuserRecipeBuilder(ItemStack output, String id) {
        super(output, id);
    }

    @Override
    public void save(RecipeOutput output) {
        InfuserRecipe.createRecipe(output,this.getLocation(IFERecipes.INFUSER),new InfuserRecipe(this.getInput(),this.getInputFluid(),this.getTime(),this.getOutput()));
    }
}
