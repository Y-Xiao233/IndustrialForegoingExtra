package net.yxiao233.industrialforegoingextra.api.recipe;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ItemExistsCondition;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.registry.DeferredRecipe;
import net.yxiao233.industrialforegoingextra.common.recipes.ArcaneDragonEggForgingRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRecipe<T extends Recipe<?>> implements Recipe<CraftingInput> {
    @Override
    public boolean matches(@NotNull CraftingInput craftingInput, @NotNull Level level) {
        return false;
    }

    @Override
    @SuppressWarnings("all")
    public @NotNull ItemStack assemble(@NotNull CraftingInput craftingInput, HolderLookup.@NotNull Provider provider) {
        return null;
    }

    public ResourceLocation generateRL(String key) {
        return getDeferredRecipe().rl().withPath(path -> path + "/" + key);
    }

    public static <T extends BaseRecipe<?>> void createRecipe(RecipeOutput recipeOutput, ResourceLocation rl, T recipe) {
        AdvancementHolder advancementHolder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl)).rewards(AdvancementRewards.Builder.recipe(rl)).requirements(AdvancementRequirements.Strategy.OR).build(rl);
        List<ICondition> conditions = new ArrayList<>();
        recipe.getOutput().ifPresent(stack -> conditions.add(new ItemExistsCondition(BuiltInRegistries.ITEM.getKey(stack.getItem()))));

        recipeOutput.accept(rl, recipe, advancementHolder, conditions.toArray(new ICondition[0]));
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    public abstract @NotNull Optional<ItemStack> getOutput();

    @Override
    @SuppressWarnings("all")
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider){
        return null;
    }

    public abstract DeferredRecipe<T> getDeferredRecipe();

    @Override
    public @NotNull RecipeType<?> getType() {
        return getDeferredRecipe().type().get();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return getDeferredRecipe().asUnknownSerializer();
    }
}
