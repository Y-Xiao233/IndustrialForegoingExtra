package net.yxiao233.industrialforegoingextra.compact.jei.category;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.util.AssetUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.yxiao233.industrialforegoingextra.api.jei.AbstractJEICategory;
import net.yxiao233.industrialforegoingextra.api.jei.JeiCategory;
import net.yxiao233.industrialforegoingextra.common.gui.AllGuiTextures;
import net.yxiao233.industrialforegoingextra.common.registry.IFEBlocks;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.compact.jei.IFERecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiCategory
public class BlockRightClickCategory extends AbstractJEICategory<BlockRightClickCategory.BlockRightClickRecipeWrapper> {
    public static final Component TITLE = Component.translatable("jei.industrialforegoingextra.block_right_click");
    public BlockRightClickCategory(IGuiHelper helper) {
        super(helper, IFERecipeType.BLOCK_RIGHT_CLICK, TITLE, IFEBlocks.DEAD_DRAGON_EGG.get().asItem(), 140, 62);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull BlockRightClickRecipeWrapper recipe, @NotNull IFocusGroup focusGroup) {
        //Block
        builder.addSlot(RecipeIngredientRole.INPUT,50,39)
                .addIngredient(VanillaTypes.ITEM_STACK,recipe.block.asItem().getDefaultInstance())
                .addRichTooltipCallback(addText("jei.industrialforegoingextra.world",ChatFormatting.GOLD));
        //handItem
        builder.addSlot(RecipeIngredientRole.INPUT,22,17)
                .addIngredient(VanillaTypes.ITEM_STACK,recipe.handItem)
                .addRichTooltipCallback(addText("jei.industrialforegoingextra.hand",ChatFormatting.AQUA));
        //result
        builder.addSlot(RecipeIngredientRole.OUTPUT,110,27)
                .addIngredient(VanillaTypes.ITEM_STACK,recipe.result.asItem().getDefaultInstance());
    }

    @Override
    public void draw(@NotNull BlockRightClickRecipeWrapper recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        //handItem
        AllGuiTextures.BASIC_SLOT.render(guiGraphics,21,16);
        //Click
        drawTextureWithTooltip(guiGraphics, AllGuiTextures.RIGHT_CLICK,Component.translatable("jei.industrialforegoingextra.block_right_click"),53,18,mouseX,mouseY);
        //Block
        AllGuiTextures.BASIC_SLOT.render(guiGraphics,49,38);
        //result
        AllGuiTextures.BASIC_SLOT.render(guiGraphics,109,26);
        //ProgressBar
        AssetUtil.drawAsset(guiGraphics, Minecraft.getInstance().screen, IAssetProvider.getAsset(DefaultAssetProvider.DEFAULT_PROVIDER, AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL), 80, 30);
    }

    @Override
    public void addRecipes(IRecipeRegistration registration) {
        List<BlockRightClickRecipeWrapper> blockRightClickRecipes = new ArrayList<>();
        blockRightClickRecipes.add(new BlockRightClickCategory.BlockRightClickRecipeWrapper(IFEItems.DRAGON_STAR.get().getDefaultInstance(), IFEBlocks.DEAD_DRAGON_EGG.get(), Blocks.DRAGON_EGG));
        registration.addRecipes(IFERecipeType.BLOCK_RIGHT_CLICK,blockRightClickRecipes);
    }

    public record BlockRightClickRecipeWrapper(ItemStack handItem, Block block, Block result) {
    }
}
