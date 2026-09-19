package net.yxiao233.industrialforegoingextra.util;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.yxiao233.industrialforegoingextra.api.jei.AbstractJEICategory;
import net.yxiao233.industrialforegoingextra.api.jei.JeiCategory;
import net.yxiao233.industrialforegoingextra.api.registry.DeferredRecipe;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class JeiHelper {
    public static void loadCategoriesFromAnnotation(IRecipeCategoryRegistration registration, List<AbstractJEICategory<?>> categories){
        categories.clear();
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        AnnotationUtil.getAllClasses(JeiCategory.class).forEach(clazz ->{
            if(AbstractJEICategory.class.isAssignableFrom(clazz)){
                try {
                    AbstractJEICategory<?> category = (AbstractJEICategory<?>) clazz.getConstructor(IGuiHelper.class).newInstance(guiHelper);
                    categories.add(category);
                    registration.addRecipeCategories(category);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration, List<AbstractJEICategory<?>> categories) {
        categories.forEach(category ->{
            registration.addRecipeCatalyst(category.iconItem,category.type);
        });
    }

    public static void registerRecipes(@NotNull IRecipeRegistration registration, List<AbstractJEICategory<?>> categories) {
        Level level = Minecraft.getInstance().level;
        if(level == null){
            return;
        }

        holderRecipesFromAllCategories(level,registration,categories);
        recipesFromAllCategories(registration,categories);
    }

    public static void hideItems(IJeiRuntime jeiRuntime, List<ItemStack> stacks){
        jeiRuntime.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, stacks);
    }

    public static void hideItem(IJeiRuntime jeiRuntime, ItemStack stack){
        hideItems(jeiRuntime,List.of(stack));
    }

    public static void hideItem(IJeiRuntime jeiRuntime, ItemLike item){
        hideItems(jeiRuntime,List.of(item.asItem().getDefaultInstance()));
    }

    public static <R extends T, T extends Item> void hideItem(IJeiRuntime jeiRuntime, DeferredHolder<T, R> item){
        hideItems(jeiRuntime,List.of(item.get().getDefaultInstance()));
    }


    private static void recipesFromAllCategories(IRecipeRegistration registration, List<AbstractJEICategory<?>> categories){
        categories.forEach(category -> {
            category.addRecipes(registration);
        });
    }

    @SuppressWarnings("unchecked")
    private static <I extends RecipeInput, T extends Recipe<I>> void holderRecipesFromAllCategories(Level level, IRecipeRegistration registration, List<AbstractJEICategory<?>> categories){
        categories.forEach(category -> {
            if(RecipeHolder.class.isAssignableFrom(category.getRecipeType().getRecipeClass())){
                net.minecraft.world.item.crafting.RecipeType<T> recipeType = category.getRecipe();
                if(recipeType != null){
                    addHolderRecipes(level,registration,recipeType,(RecipeType<RecipeHolder<T>>) category.type);
                }
            }
        });
    }

    public static  <I extends RecipeInput, T extends Recipe<I>> void addHolderRecipes(Level level, IRecipeRegistration registration, DeferredRecipe<T> deferredRecipe, RecipeType<RecipeHolder<T>> type){
        addHolderRecipes(level,registration,deferredRecipe.asType(),type);
    }

    public static  <I extends RecipeInput, T extends Recipe<I>> void addHolderRecipes(Level level, IRecipeRegistration registration, net.minecraft.world.item.crafting.RecipeType<T> recipeType, RecipeType<RecipeHolder<T>> type){
        List<RecipeHolder<T>> recipes = level.getRecipeManager().getAllRecipesFor(recipeType).stream().toList();
        registration.addRecipes(type,recipes);
    }
}
