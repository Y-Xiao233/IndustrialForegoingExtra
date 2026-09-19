package net.yxiao233.industrialforegoingextra.compact.kubejs.component;

import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ListRecipeComponent;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientsComponent{
    public static final ListRecipeComponent<Ingredient> INGREDIENTS = ListRecipeComponent.create(new IngredientComponent(IngredientComponent.INGREDIENT, Ingredient.CODEC_NONEMPTY, false),true,true);
    public static final ListRecipeComponent<Ingredient> OPTIONAL_INGREDIENTS = ListRecipeComponent.create(new IngredientComponent(IngredientComponent.INGREDIENT, Ingredient.CODEC, true),true,true);
}

