package net.yxiao233.industrialforegoingextra.compact.kubejs.schemas;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidStackComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.SizedFluidIngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.TimeComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TickDuration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.yxiao233.industrialforegoingextra.compact.kubejs.component.IngredientsComponent;

import java.util.List;

public interface DissolutionChamberSchema {
    RecipeKey<List<Ingredient>> INPUTS = IngredientsComponent.OPTIONAL_INGREDIENTS.inputKey("input");
    RecipeKey<SizedFluidIngredient> INPUT_FLUID = SizedFluidIngredientComponent.FLAT.inputKey("inputFluid");
    RecipeKey<ItemStack> OUTPUT = ItemStackComponent.ITEM_STACK.inputKey("output");
    RecipeKey<FluidStack> OUTPUT_FLUID = FluidStackComponent.OPTIONAL_FLUID_STACK.outputKey("outputFluid").optional(FluidStack.EMPTY);
    RecipeKey<TickDuration> TIME = TimeComponent.TICKS.otherKey("processingTime");
    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT,INPUTS,INPUT_FLUID,TIME,OUTPUT_FLUID);
}
