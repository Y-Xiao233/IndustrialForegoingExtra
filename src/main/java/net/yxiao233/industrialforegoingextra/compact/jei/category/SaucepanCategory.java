package net.yxiao233.industrialforegoingextra.compact.jei.category;

import com.buuz135.industrial.module.ModuleCore;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.yxiao233.industrialforegoingextra.api.jei.AbstractJEICategory;
import net.yxiao233.industrialforegoingextra.api.jei.JeiCategory;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.tile.SaucepanTile;
import net.yxiao233.industrialforegoingextra.compact.jei.IFERecipeType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@JeiCategory
public class SaucepanCategory extends AbstractJEICategory<SaucepanCategory.SaucepanRecipeWrapper> {
    private final IDrawable tankOverlay;
    private static final Component TITLE = Component.translatable("jei.industrialforegoingextra.recipe.title.saucepan");

    public SaucepanCategory(IGuiHelper guiHelper) {
        super(guiHelper,IFERecipeType.SAUCEPAN,TITLE, IFEBlocks.SAUCEPAN.asItem(),0,0);
        this.background = guiHelper.createDrawable(ResourceLocation.fromNamespaceAndPath("industrialforegoingextra", "textures/gui/jei.png"), 0, 51, 70, 50);
        this.tankOverlay = guiHelper.createDrawable(ResourceLocation.fromNamespaceAndPath("industrialforegoingextra", "textures/gui/jei.png"), 1, 207, 12, 48);
    }

    @Nullable
    public IDrawable getIcon() {
        return null;
    }

    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull SaucepanRecipeWrapper recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 17).addIngredients(Ingredient.of(recipe.stack));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 57, 1).setFluidRenderer(1000L, false, 12, 48).setOverlay(this.tankOverlay, 0, 0).addIngredient(NeoForgeTypes.FLUID_STACK, recipe.fluid());
    }

    @Override
    public void addRecipes(IRecipeRegistration registration) {
        List<SaucepanRecipeWrapper> recipes = new ArrayList<>();
        List<TagKey<Item>> tags = List.copyOf(SaucepanTile.VALID);

        tags.forEach(tag ->{
            recipes.add(new SaucepanCategory.SaucepanRecipeWrapper(tag,new FluidStack(ModuleCore.MEAT.getSourceFluid().get(),80)));
        });

        registration.addRecipes(IFERecipeType.SAUCEPAN,recipes);
    }

    public record SaucepanRecipeWrapper(TagKey<Item> stack, FluidStack fluid) {
    }
}
