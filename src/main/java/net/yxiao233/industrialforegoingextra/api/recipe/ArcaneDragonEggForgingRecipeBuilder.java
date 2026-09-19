package net.yxiao233.industrialforegoingextra.api.recipe;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.yxiao233.industrialforegoingextra.common.recipes.ArcaneDragonEggForgingRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;

import java.util.Optional;

public class ArcaneDragonEggForgingRecipeBuilder extends IFERecipeBuilder {
    public ArcaneDragonEggForgingRecipeBuilder(ItemStack output) {
        super(output);
    }

    public ArcaneDragonEggForgingRecipeBuilder(ItemStack output, String id) {
        super(output, id);
    }

    @Override
    public void save(RecipeOutput output) {
        FluidStack[] stacks = this.getInputFluids();
        Optional<FluidStack> fluidStack = this.getOutputFluid() == FluidStack.EMPTY ? Optional.empty() : Optional.of(this.getOutputFluid());
        Optional<ItemStack> itemStack = this.getOutput() == ItemStack.EMPTY ? Optional.empty() : Optional.of(this.getOutput());
        ArcaneDragonEggForgingRecipe.createRecipe(output,this.getLocation(IFERecipes.ARCANE_DRAGON_EGG_FORGING),new ArcaneDragonEggForgingRecipe(this.getInput(),stacks[0],stacks[1],this.getTime(),itemStack,fluidStack));
    }
}
