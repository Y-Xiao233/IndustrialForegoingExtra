package net.yxiao233.industrialforegoingextra.compact.jei.category;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.client.screen.addon.SlotsScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.util.AssetUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.yxiao233.industrialforegoingextra.api.jei.AbstractJEICategory;
import net.yxiao233.industrialforegoingextra.api.jei.JeiCategory;
import net.yxiao233.industrialforegoingextra.common.config.machine.FluidCraftingTableConfig;
import net.yxiao233.industrialforegoingextra.common.recipes.ShapedRecipe;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.common.registry.IFERecipes;
import net.yxiao233.industrialforegoingextra.compact.jei.IFERecipeType;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

@JeiCategory
public class ShapedCategory extends AbstractJEICategory<RecipeHolder<ShapedRecipe>> {
    public static final Component TITLE = Component.translatable("jei.industrialforegoingextra.fluid_shaped");
    private final IDrawable bigTank_input1;
    public ShapedCategory(IGuiHelper helper) {
        super(helper, IFERecipeType.SHAPED, TITLE, IFEBlocks.FLUID_CRAFTING_TABLE.getBlock().asItem(),160, 82);
        this.bigTank_input1 = helper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 180, 4, 12, 50);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> getRecipe() {
        return (RecipeType<R>) IFERecipes.SHAPED.asType();
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull RecipeHolder<ShapedRecipe> recipe, @NotNull IFocusGroup iFocusGroup) {
        //Input
        int x = 31;
        int y = 15;
        for (int i = 0; i < recipe.value().inputs.size(); i++) {
            if(i % 3 == 0 && i != 0){
                x = 31;
                y += 18;
            }
            Iterator<ItemStack> iterator = Arrays.stream(recipe.value().inputs.get(i).getItems()).iterator();
            if(iterator.hasNext() && iterator.next().is(IFEItems.AIR.get())){
                builder.addSlot(RecipeIngredientRole.INPUT,x,y).addIngredient(VanillaTypes.ITEM_STACK,ItemStack.EMPTY);
            }else{
                builder.addSlot(RecipeIngredientRole.INPUT,x,y).addIngredients(VanillaTypes.ITEM_STACK, Arrays.asList(recipe.value().inputs.get(i).getItems()));
            }
            x += 18;
        }

        //InputFluid
        if(recipe.value().inputFluid != null && !recipe.value().inputFluid.isEmpty()){
            builder.addSlot(RecipeIngredientRole.CATALYST, 9 + 3, 12 + 3)
                    .setFluidRenderer(FluidCraftingTableConfig.maxInputTankSize >= 1000 ? FluidCraftingTableConfig.maxInputTankSize : 1000,false,12,50)
                    .setOverlay(bigTank_input1,0,0).addIngredient(NeoForgeTypes.FLUID_STACK, recipe.value().inputFluid);
        }
        //Output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 119, 33)
                .addIngredient(VanillaTypes.ITEM_STACK,recipe.value().output);
    }

    @Override
    public void draw(@NotNull RecipeHolder<ShapedRecipe> recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        //Input
        int x = 31;
        int y = 15;
        for (int i = 0; i < recipe.value().inputs.size(); i++) {
            if(i % 3 == 0 && i != 0){
                x = 31;
                y += 18;
            }
            SlotsScreenAddon.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER,x,y,0,0,1, integer -> Pair.of(0, 18 * (integer)), integer -> ItemStack.EMPTY, true, integer -> new Color(DyeColor.LIGHT_BLUE.getFireworkColor()), integer -> true, 1);
            x += 18;
        }
        //InputFluid
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, Objects.requireNonNull(DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_NORMAL)), 9, 12);
        //Output
        SlotsScreenAddon.drawAsset(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 119, 33, 0, 0, 1, integer -> Pair.of(0, 18 * (integer)), integer -> ItemStack.EMPTY, true, integer -> new Color(DyeColor.ORANGE.getFireworkColor()), integer -> true, 1);
        //ProgressBar
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, IAssetProvider.getAsset(DefaultAssetProvider.DEFAULT_PROVIDER, AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 92, 41 - 8);
    }
}
