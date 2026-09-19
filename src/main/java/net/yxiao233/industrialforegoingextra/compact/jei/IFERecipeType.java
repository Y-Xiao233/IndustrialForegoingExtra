package net.yxiao233.industrialforegoingextra.compact.jei;

import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.common.recipes.*;
import net.yxiao233.industrialforegoingextra.compact.jei.category.BlockRightClickCategory;
import net.yxiao233.industrialforegoingextra.compact.jei.category.DragonGeneratorCategory;
import net.yxiao233.industrialforegoingextra.compact.jei.category.FermenterCategory;
import net.yxiao233.industrialforegoingextra.compact.jei.category.SaucepanCategory;

public class IFERecipeType {
    public static RecipeType<RecipeHolder<InfuserRecipe>> INFUSER = creatHolderType("infuser");
    public static RecipeType<RecipeHolder<ArcaneDragonEggForgingRecipe>> ARCANE_DRAGON_EGG_FORGING = creatHolderType("arcane_dragon_egg_forging");
    public static RecipeType<BlockRightClickCategory.BlockRightClickRecipeWrapper> BLOCK_RIGHT_CLICK = createType("block_right_click", BlockRightClickCategory.BlockRightClickRecipeWrapper.class);
    public static RecipeType<SaucepanCategory.SaucepanRecipeWrapper> SAUCEPAN = createType("saucepan", SaucepanCategory.SaucepanRecipeWrapper.class);
    public static RecipeType<FermenterCategory.FermenterRecipeWrapper> FERMENTER = createType("fermenter", FermenterCategory.FermenterRecipeWrapper.class);
    public static RecipeType<DragonGeneratorCategory.DragonGeneratorRecipeWrapper> DRAGON_GENERATOR = createType("dragon_generator", DragonGeneratorCategory.DragonGeneratorRecipeWrapper.class);
    public static RecipeType<RecipeHolder<ShapedRecipe>> SHAPED = creatHolderType("shaped");
    public static RecipeType<RecipeHolder<ShapelessRecipe>> SHAPELESS = creatHolderType("shapeless");
    private static <T> RecipeType<T> createType(String name, Class<? extends T> clazz){
        return RecipeType.create(IndustrialForegoingExtra.MODID,name,clazz);
    }
    private static <T extends Recipe<?>> RecipeType<RecipeHolder<T>> creatHolderType(String name){
        return RecipeType.createRecipeHolderType(IndustrialForegoingExtra.makeId(name));
    }
}
