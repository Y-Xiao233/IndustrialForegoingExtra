package net.yxiao233.industrialforegoingextra.compact.jei.category;

import com.buuz135.industrial.module.ModuleCore;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.yxiao233.industrialforegoingextra.api.jei.AbstractJEICategory;
import net.yxiao233.industrialforegoingextra.api.jei.JeiCategory;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFETags;
import net.yxiao233.industrialforegoingextra.common.tile.FermenterTile;
import net.yxiao233.industrialforegoingextra.compact.jei.IFERecipeType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@JeiCategory
public class FermenterCategory extends AbstractJEICategory<FermenterCategory.FermenterRecipeWrapper> {
    private final IDrawable tankOverlay;
    private static final Component TITLE = Component.translatable("jei.industrialforegoingextra.recipe.title.fermenter");

    public FermenterCategory(IGuiHelper guiHelper) {
        super(guiHelper,IFERecipeType.FERMENTER,TITLE, IFEBlocks.FERMENTER.asItem(),0,0);
        this.background = guiHelper.createDrawable(ResourceLocation.fromNamespaceAndPath("industrialforegoingextra", "textures/gui/jei.png"), 0, 0, 70, 50);
        this.tankOverlay = guiHelper.createDrawable(ResourceLocation.fromNamespaceAndPath("industrialforegoingextra", "textures/gui/jei.png"), 1, 207, 12, 48);
    }

    @Nullable
    public IDrawable getIcon() {
        return null;
    }

    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull FermenterRecipeWrapper recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 3).addIngredients(Ingredient.of(recipe.stack));
        builder.addSlot(RecipeIngredientRole.CATALYST, 1, 31).addIngredients(Ingredient.of(recipe.catalyst)).addRichTooltipCallback((recipeSlotView, tooltip) -> {
            tooltip.add(Component.translatable("jei.industrialforegoingextra.catalyst").withStyle(ChatFormatting.GOLD));
        });
        builder.addSlot(RecipeIngredientRole.OUTPUT, 57, 1).setFluidRenderer(1000L, false, 12, 48).setOverlay(this.tankOverlay, 0, 0).addIngredient(NeoForgeTypes.FLUID_STACK, recipe.fluid());
    }

    @Override
    public void addRecipes(IRecipeRegistration registration) {
        List<FermenterRecipeWrapper> recipes = new ArrayList<>();
        List<TagKey<Item>> catalysts = List.of(FermenterTile.CATALYST);

        recipes.add(new FermenterCategory.FermenterRecipeWrapper(Tags.Items.CROPS,catalysts.getFirst(),new FluidStack(ModuleCore.SLUDGE.getSourceFluid().get(),80)));
        recipes.add(new FermenterCategory.FermenterRecipeWrapper(IFETags.Items.ROTTEN_CROPS,catalysts.getFirst(),new FluidStack(ModuleCore.SLUDGE.getSourceFluid().get(),400)));

        registration.addRecipes(IFERecipeType.FERMENTER,recipes);
    }

    public record FermenterRecipeWrapper(TagKey<Item> stack, TagKey<Item> catalyst, FluidStack fluid) {
    }
}
