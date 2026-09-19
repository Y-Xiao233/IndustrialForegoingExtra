package net.yxiao233.industrialforegoingextra.api.recipe;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.yxiao233.industrialforegoingextra.common.recipes.ShapedRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;

import java.util.List;

public class ShapedRecipeBuilder extends IFERecipeBuilder{
    public ShapedRecipeBuilder(ItemStack output) {
        super(output);
    }
    public ShapedRecipeBuilder(ItemStack output, String id){
        super(output,id);
    }

    @Override
    public void save(RecipeOutput recipeOutput) {
        List<Ingredient> allItems = this.getStructure().stream()
                .flatMap(str ->
                        str.chars()
                                .mapToObj(c -> (char) c)
                                .map(c ->{
                                    if(this.getDefineMap().containsKey(c)){
                                        return Ingredient.of(this.getDefineMap().get(c));
                                    }else if(this.getDefineTagMap().containsKey(c)){
                                        return Ingredient.of(this.getDefineTagMap().get(c));
                                    }else{
                                        return Ingredient.of(IFEItems.AIR.get());
                                    }
                                })
                )
                .toList();

        ShapedRecipe.createRecipe(recipeOutput,this.getLocation(IFERecipes.SHAPED),new ShapedRecipe(
                allItems,this.getInputFluid(),this.getOutput()
        ));
    }
}
